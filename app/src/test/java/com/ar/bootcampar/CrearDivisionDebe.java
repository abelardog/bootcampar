package com.ar.bootcampar;

import static org.junit.Assert.*;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearGrupoDePrueba;

import com.ar.bootcampar.model.Division;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;

public class CrearDivisionDebe {
    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearDivisionConUsuarioInvalido() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy dummy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(dummy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearDivision(null, grupo));
        assertEquals("El usuario es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearDivisionConGrupoInvalido() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy dummy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(dummy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearDivision(usuario, null));
        assertEquals("El grupo es nulo", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElUsuarioEsInvalido() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearDivision(null, grupo));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElGrupoEsInvalido() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearDivision(usuario, null));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeLaDivision() {
        Usuario usuario = crearUsuarioDePrueba();
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearDivision(usuario, grupo);

        assertEquals(ID_USUARIO, spy.getInsertedValues().get("UsuarioId"));
        assertEquals(ID_GRUPO, spy.getInsertedValues().get("GrupoId"));
    }

    @Test
    public void insertarDatosEnTablaDivision() {
        Usuario usuario = crearUsuarioDePrueba();
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearDivision(usuario, grupo);

        assertEquals("Divisiones", spy.getTableName());
    }

    @Test
    public void retornarDivision_cuandoSeInsertaDivisionEnBaseDeDatos() {
        Usuario usuario = crearUsuarioDePrueba();
        Grupo grupo = crearGrupoDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(14)
                .build();
        Database database = new TestableDatabase(spy);
        Division sut = database.crearDivision(usuario, grupo);

        assertNotNull(sut);
        assertEquals(14, sut.getId());
        assertSame(usuario, sut.getUsuario());
        assertSame(grupo, sut.getGrupo());
    }

    @Test
    public void lanzarExcepcion_cuandoInsertRetornaError() {
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearDivision(crearUsuarioDePrueba(), crearGrupoDePrueba()));
        assertEquals("Error creando división", exception.getMessage());
    }

    @Test
    public void hacerRollback_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearDivision(crearUsuarioDePrueba(), crearGrupoDePrueba()));
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
        assertThrows(RuntimeException.class, () -> database.crearDivision(crearUsuarioDePrueba(), crearGrupoDePrueba()));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeInsertaDivisionEnBaseDeDatos() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        database.crearDivision(crearUsuarioDePrueba(), crearGrupoDePrueba());
        assertTrue(spy.getCloseCalled());
    }
}