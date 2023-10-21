package com.ar.bootcampar;

import static org.junit.Assert.*;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearGrupoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;

import com.ar.bootcampar.model.Curricula;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;

public class CrearCurriculaDebe {
    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearCurriculaConCursoInvalido() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy dummy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(dummy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearCurricula(null, grupo));
        assertEquals("El curso es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearCurriculaConGrupoInvalido() {
        Curso curso = crearCursoDePrueba();
        SqliteDatabaseWrapperSpy dummy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(dummy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearCurricula(curso, null));
        assertEquals("El grupo es nulo", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElCursoEsInvalido() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearCurricula(null, grupo));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElGrupoEsInvalido() {
        Curso curso = crearCursoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearCurricula(curso, null));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeLaCurricula() {
        Curso curso = crearCursoDePrueba();
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearCurricula(curso, grupo);

        assertEquals(ID_CURSO, spy.getInsertedValues().get("CursoId"));
        assertEquals(ID_GRUPO, spy.getInsertedValues().get("GrupoId"));
    }

    @Test
    public void insertarDatosEnTablaCurricula() {
        Curso curso = crearCursoDePrueba();
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearCurricula(curso, grupo);

        assertEquals("Curriculas", spy.getTableName());
    }

    @Test
    public void retornarCurricula_cuandoSeInsertaCurriculaEnBaseDeDatos() {
        Curso curso = crearCursoDePrueba();
        Grupo grupo = crearGrupoDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(14)
                .build();
        Database database = new TestableDatabase(spy);
        Curricula sut = database.crearCurricula(curso, grupo);

        assertNotNull(sut);
        assertEquals(14, sut.getId());
        assertSame(curso, sut.getCurso());
        assertSame(grupo, sut.getGrupo());
    }

    @Test
    public void lanzarExcepcion_cuandoInsertRetornaError() {
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearCurricula(crearCursoDePrueba(), crearGrupoDePrueba()));
        assertEquals("Error creando currícula", exception.getMessage());
    }

    @Test
    public void hacerRollback_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearCurricula(crearCursoDePrueba(), crearGrupoDePrueba()));
        assertTrue(spy.getBeginTransactionCalled());
        assertFalse(spy.getTransactionSuccessfulCalled());
        assertTrue(spy.getEndTransactionCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearCurricula(crearCursoDePrueba(), crearGrupoDePrueba()));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeInsertaCurriculaEnBaseDeDatos() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        database.crearCurricula(crearCursoDePrueba(), crearGrupoDePrueba());
        assertTrue(spy.getCloseCalled());
    }
}