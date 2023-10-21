package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearCurriculaDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearGrupoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtroCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtroGrupoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Curricula;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ModificarCurriculaDebe {
    @Test
    public void lanzarExcepcion_cuandoSeQuiereModificarObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarCurricula(null, crearCursoDePrueba(), crearGrupoDePrueba()));
        assertEquals("La currícula es nula", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnCursoInvalido() {
        Curricula curricula = crearCurriculaDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarCurricula(curricula, null, crearGrupoDePrueba()));
        assertEquals("El curso es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnGrupoInvalido() {
        Curricula curricula = crearCurriculaDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarCurricula(curricula, crearCursoDePrueba(), null));
        assertEquals("El grupo es nulo", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElCursoEsInvalido() {
        Curricula curricula = crearCurriculaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarCurricula(curricula, null, crearGrupoDePrueba()));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElGrupoEsInvalido() {
        Curricula curricula = crearCurriculaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarCurricula(curricula, crearCursoDePrueba(), null));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeLaCurricula() {
        Curricula curricula = crearCurriculaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarCurricula(curricula, crearCursoDePrueba(), crearGrupoDePrueba());

        assertEquals(ID_CURSO, spy.getInsertedValues().get("CursoId"));
        assertEquals(ID_GRUPO, spy.getInsertedValues().get("GrupoId"));
    }

    @Test
    public void insertarDatosEnTablaCurricula() {
        Curricula curricula = crearCurriculaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarCurricula(curricula, crearCursoDePrueba(), crearGrupoDePrueba());

        assertEquals("Curriculas", spy.getTableName());
    }

    @Test
    public void retornarCurriculaModificado_cuandoSeModificaCurriculaEnBaseDeDatos() {
        Curso curso = crearOtroCursoDePrueba();
        Grupo grupo = crearOtroGrupoDePrueba();
        Curricula curricula = crearCurriculaDePrueba();

        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        Curricula sut = database.modificarCurricula(curricula, curso, grupo);

        assertNotNull(sut);
        assertEquals(ID_CURRICULA, sut.getId());
        assertEquals(curso, sut.getCurso());
        assertEquals(grupo, sut.getGrupo());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[]{0, -1, 2};
    }

    @Theory
    public void lanzarExcepcion_cuandoUpdateRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Curricula curricula = crearCurriculaDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(affectedRowsInvalido)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.modificarCurricula(curricula, crearCursoDePrueba(), crearGrupoDePrueba()));
        assertTrue(exception.getMessage().startsWith("Se esperaba modificar una única currícula pero se modificaron "));
        assertTrue(exception.getMessage().contains(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaError() {
        Curricula curricula = crearCurriculaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(0)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.modificarCurricula(curricula, crearCursoDePrueba(), crearGrupoDePrueba()));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaExito() {
        Curricula curricula = crearCurriculaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.modificarCurricula(curricula, crearCursoDePrueba(), crearGrupoDePrueba());
        assertTrue(spy.getCloseCalled());
    }
}