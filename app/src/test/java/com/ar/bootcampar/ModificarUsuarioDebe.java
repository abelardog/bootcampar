package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import androidx.annotation.NonNull;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Rol;
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
public class ModificarUsuarioDebe {
    @Test
    public void lanzarExcepcion_cuandoSeQuiereModificarObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarUsuario(null, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertEquals("El usuario es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnNombreInvalido() {
        Usuario usuario = crearUsuarioDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarUsuario(usuario, "", APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnApellidoInvalido() {
        Usuario usuario = crearUsuarioDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarUsuario(usuario, NOMBRE, "", EMAIL, CLAVE, ROL, TELEFONO));
        assertEquals("El apellido es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnEmailInvalido() {
        Usuario usuario = crearUsuarioDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarUsuario(usuario, NOMBRE, APELLIDO, "", CLAVE, ROL, TELEFONO));
        assertEquals("El email es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnClaveInvalida() {
        Usuario usuario = crearUsuarioDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, "", ROL, TELEFONO));
        assertEquals("La clave es inválida", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElNombreEsInvalido() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarUsuario(usuario, NOMBRE_INVALIDO, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElApellidoEsInvalido() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarUsuario(usuario, NOMBRE, APELLIDO_INVALIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElEmailEsInvalido() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL_INVALIDO, CLAVE, ROL, TELEFONO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaClaveEsInvalida() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE_INVALIDA, ROL, TELEFONO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeUsuario() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarUsuario(usuario, OTRO_NOMBRE, OTRO_APELLIDO, OTRO_EMAIL, OTRA_CLAVE, OTRO_ROL, OTRO_TELEFONO);

        assertEquals(OTRO_NOMBRE, spy.getInsertedValues().get("Nombre"));
        assertEquals(OTRO_APELLIDO, spy.getInsertedValues().get("Apellido"));
        assertEquals(OTRO_EMAIL, spy.getInsertedValues().get("Email"));
        assertEquals(OTRA_CLAVE, spy.getInsertedValues().get("Clave"));
        assertEquals(Rol.asInt(OTRO_ROL), spy.getInsertedValues().get("Rol"));
        assertEquals(OTRO_TELEFONO, spy.getInsertedValues().get("Telefono"));
    }

    @Test
    public void insertarDatosEnTablaUsuarioDePru    () {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertEquals("Usuarios", spy.getTableName());
    }

    @Test
    public void retornarUsuarioModificado_cuandoSeModificaUsuarioEnBaseDeDatos() {
        Usuario usuario = crearUsuarioDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        Usuario sut = database.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertNotNull(sut);
        assertEquals(ID_USUARIO, sut.getId());
        assertEquals(NOMBRE, sut.getNombre());
        assertEquals(APELLIDO, sut.getApellido());
        assertEquals(EMAIL, sut.getEmail());
        assertEquals(CLAVE, sut.getClave());
        assertEquals(ROL, sut.getRol());
        assertEquals(TELEFONO, sut.getTelefono());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[]{0, -1, 2};
    }

    @Theory
    public void lanzarExcepcion_cuandoUpdateRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Usuario usuario = crearUsuarioDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(affectedRowsInvalido)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertTrue(exception.getMessage().startsWith("Se esperaba modificar un único usuario pero se modificaron "));
        assertTrue(exception.getMessage().contains(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaError() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(0)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaExito() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);
        assertTrue(spy.getCloseCalled());
    }
}