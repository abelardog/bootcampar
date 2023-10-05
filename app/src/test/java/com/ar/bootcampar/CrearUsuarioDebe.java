package com.ar.bootcampar;

import static org.junit.Assert.*;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;

public class CrearUsuarioDebe {
    @Test
    public void recibirTodosLosDatosDeUsuario() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy();
        Database sut = new TestableDatabase(spy);
        sut.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertEquals(NOMBRE, spy.getInsertedValues().get("Nombre"));
        assertEquals(APELLIDO, spy.getInsertedValues().get("Apellido"));
        assertEquals(EMAIL, spy.getInsertedValues().get("Email"));
        assertEquals(CLAVE, spy.getInsertedValues().get("Clave"));
        assertEquals(Rol.asInt(ROL), spy.getInsertedValues().get("Rol"));
        assertEquals(TELEFONO, spy.getInsertedValues().get("Telefono"));
    }

    @Test
    public void insertarDatosEnTablaUsuario() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy();
        Database sut = new TestableDatabase(spy);
        sut.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertEquals("Usuarios", spy.getTableName());
    }

    @Test
    public void retornarUsuario_cuandoSeInsertaUsuarioEnBaseDeDatos() {
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy(14);
        Database database = new TestableDatabase(spy);
        Usuario sut = database.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertNotNull(sut);
        assertEquals(14, sut.getId());
        assertEquals(NOMBRE, sut.getNombre());
        assertEquals(APELLIDO, sut.getApellido());
        assertEquals(EMAIL, sut.getEmail());
        assertEquals(CLAVE, sut.getClave());
        assertEquals(ROL, sut.getRol());
        assertEquals(TELEFONO, sut.getTelefono());
    }

    @Test
    public void lanzarExcepcion_cuandoInsertRetornaError() {
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy(-1);
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertEquals("Error creando usuario", exception.getMessage());
    }

    @Test
    public void hacerRollback_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy(-1);
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertTrue(spy.getBeginTransactionCalled());
        assertFalse(spy.getTransactionSuccessfulCalled());
        assertTrue(spy.getEndTransactionCalled());
    }

    @Test
    public void cerrarConexion_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy(-1);
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarConexion_cuandoSeInsertaUsuarioEnBaseDeDatos() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy(1);
        Database database = new TestableDatabase(spy);
        database.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);
        assertTrue(spy.getCloseCalled());
    }
}