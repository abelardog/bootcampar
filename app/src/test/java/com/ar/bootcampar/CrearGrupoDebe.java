package com.ar.bootcampar;

import static org.junit.Assert.*;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class CrearGrupoDebe {
    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearGrupoConNombreInvalido() {
        SqliteDatabaseWrapperSpy stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearGrupo("", INVITACION_GRUPO));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearGrupoConInvitacionInvalida() {
        SqliteDatabaseWrapperSpy stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearGrupo(NOMBRE_GRUPO, ""));
        assertEquals("La invitación es inválida", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElNombreEsInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearGrupo(NOMBRE_GRUPO_INVALIDO, INVITACION_GRUPO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaInvitacionEsInvalida() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearGrupo(NOMBRE_GRUPO, INVITACION_GRUPO_INVALIDA));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeGrupo() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearGrupo(NOMBRE_GRUPO, INVITACION_GRUPO);

        assertEquals(NOMBRE_GRUPO, spy.getInsertedValues().get("Nombre"));
        assertEquals(INVITACION_GRUPO, spy.getInsertedValues().get("Invitacion"));
    }

    @Test
    public void insertarDatosEnTablaGrupo() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearGrupo(NOMBRE_GRUPO, INVITACION_GRUPO);

        assertEquals("Grupos", spy.getTableName());
    }

    @Test
    public void retornarGrupo_cuandoSeInsertaGrupoEnBaseDeDatos() {
        ISQLiteDatabaseWrapper stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(14)
                .build();
        Database database = new TestableDatabase(stub);
        Grupo sut = database.crearGrupo(NOMBRE_GRUPO, INVITACION_GRUPO);

        assertNotNull(sut);
        assertEquals(14, sut.getId());
        assertEquals(NOMBRE_GRUPO, sut.getNombre());
        assertEquals(INVITACION_GRUPO, sut.getInvitacion());
    }

    @Test
    public void lanzarExcepcion_cuandoInsertRetornaError() {
        ISQLiteDatabaseWrapper stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearGrupo(NOMBRE_GRUPO, INVITACION_GRUPO));
        assertEquals("Error creando grupo", exception.getMessage());
    }

    @Test
    public void hacerRollback_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearGrupo(NOMBRE_GRUPO, INVITACION_GRUPO));
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
        assertThrows(RuntimeException.class, () -> database.crearGrupo(NOMBRE_GRUPO, INVITACION_GRUPO));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeInsertaGrupoioEnBaseDeDatos() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        database.crearGrupo(NOMBRE_GRUPO, INVITACION_GRUPO);
        assertTrue(spy.getCloseCalled());
    }
}