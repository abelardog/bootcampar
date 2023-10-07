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
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class CrearUsuarioDebe {
    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearUsuarioConNombreInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearUsuario(NOMBRE_INVALIDO, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearUsuarioConApellidoInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearUsuario(NOMBRE, APELLIDO_INVALIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertEquals("El apellido es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearUsuarioConEmailInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearUsuario(NOMBRE, APELLIDO, EMAIL_INVALIDO, CLAVE, ROL, TELEFONO));
        assertEquals("El email es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearUsuarioConClaveInvalida() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE_INVALIDA, ROL, TELEFONO));
        assertEquals("La clave es inválida", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElNombreEsInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearUsuario(NOMBRE_INVALIDO, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElApellidoEsInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearUsuario(NOMBRE, APELLIDO_INVALIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElEmailEsInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearUsuario(NOMBRE, APELLIDO, EMAIL_INVALIDO, CLAVE, ROL, TELEFONO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaClaveEsInvalida() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE_INVALIDA, ROL, TELEFONO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeUsuario() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
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
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertEquals("Usuarios", spy.getTableName());
    }

    @Test
    public void retornarUsuario_cuandoSeInsertaUsuarioEnBaseDeDatos() {
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(14)
                .build();
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
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertEquals("Error creando usuario", exception.getMessage());
    }

    @Test
    public void hacerRollback_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertTrue(spy.getBeginTransactionCalled());
        assertFalse(spy.getTransactionSuccessfulCalled());
        assertTrue(spy.getEndTransactionCalled());
    }

    @Test
    public void cerrarConexion_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarConexion_cuandoSeInsertaUsuarioEnBaseDeDatos() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        database.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);
        assertTrue(spy.getCloseCalled());
    }
}