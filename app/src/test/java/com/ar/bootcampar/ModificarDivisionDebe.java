package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearGrupoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearDivisionDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtraDivisionDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtroGrupoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtroUsuarioDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Division;
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
public class ModificarDivisionDebe {
    @Test
    public void lanzarExcepcion_cuandoSeQuiereModificarObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarDivision(null, crearUsuarioDePrueba(), crearGrupoDePrueba()));
        assertEquals("La división es nula", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnUsuarioInvalido() {
        Division division = crearDivisionDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarDivision(division, null, crearGrupoDePrueba()));
        assertEquals("El usuario es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnGrupoInvalido() {
        Division division = crearDivisionDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarDivision(division, crearUsuarioDePrueba(), null));
        assertEquals("El grupo es nulo", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElUsuarioEsInvalido() {
        Division division = crearDivisionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarDivision(division, null, crearGrupoDePrueba()));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElGrupoEsInvalido() {
        Division division = crearDivisionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarDivision(division, crearUsuarioDePrueba(), null));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeLaDivision() {
        Division division = crearOtraDivisionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarDivision(division, crearUsuarioDePrueba(), crearGrupoDePrueba());

        assertEquals(ID_USUARIO, spy.getInsertedValues().get("UsuarioId"));
        assertEquals(ID_GRUPO, spy.getInsertedValues().get("GrupoId"));
    }

    @Test
    public void insertarDatosEnTablaDivision() {
        Division division = crearDivisionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarDivision(division, crearUsuarioDePrueba(), crearGrupoDePrueba());

        assertEquals("Divisiones", spy.getTableName());
    }

    @Test
    public void retornarDivisionModificado_cuandoSeModificaDivisionEnBaseDeDatos() {
        Usuario usuario = crearOtroUsuarioDePrueba();
        Grupo grupo = crearOtroGrupoDePrueba();
        Division division = crearDivisionDePrueba();

        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        Division sut = database.modificarDivision(division, usuario, grupo);

        assertNotNull(sut);
        assertEquals(ID_DIVISION, sut.getId());
        assertEquals(usuario, sut.getUsuario());
        assertEquals(grupo, sut.getGrupo());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[]{0, -1, 2};
    }

    @Theory
    public void lanzarExcepcion_cuandoUpdateRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Division division = crearDivisionDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(affectedRowsInvalido)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.modificarDivision(division, crearUsuarioDePrueba(), crearGrupoDePrueba()));
        assertTrue(exception.getMessage().startsWith("Se esperaba modificar una única división pero se modificaron "));
        assertTrue(exception.getMessage().contains(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaError() {
        Division division = crearDivisionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(0)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.modificarDivision(division, crearUsuarioDePrueba(), crearGrupoDePrueba()));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaExito() {
        Division division = crearDivisionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.modificarDivision(division, crearUsuarioDePrueba(), crearGrupoDePrueba());
        assertTrue(spy.getCloseCalled());
    }
}