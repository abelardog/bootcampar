package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static org.junit.Assert.assertEquals;
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
    public void recibirTodosLosDatosDeUsuario() {
        Usuario usuario = crearUsuario();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertEquals(NOMBRE, spy.getInsertedValues().get("Nombre"));
        assertEquals(APELLIDO, spy.getInsertedValues().get("Apellido"));
        assertEquals(EMAIL, spy.getInsertedValues().get("Email"));
        assertEquals(CLAVE, spy.getInsertedValues().get("Clave"));
        assertEquals(Rol.asInt(ROL), spy.getInsertedValues().get("Rol"));
        assertEquals(TELEFONO, spy.getInsertedValues().get("Telefono"));
    }

    @NonNull
    private static Usuario crearUsuario() {
        return new Usuario(ID, "Juan", "Perez", "juan.perez@gmail.com", "112233", Rol.Estudiante, "1111-2222");
    }

    @Test
    public void insertarDatosEnTablaUsuario() {
        Usuario usuario = crearUsuario();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertEquals("Usuarios", spy.getTableName());
    }

    @Test
    public void retornarUsuarioModificado_cuandoSeModificaUsuarioEnBaseDeDatos() {
        Usuario usuario = crearUsuario();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        Usuario sut = database.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertNotNull(sut);
        assertEquals(ID, sut.getId());
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
        Usuario usuario = crearUsuario();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(affectedRowsInvalido)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertTrue(exception.getMessage().startsWith("Se esperaba modificar un único usuario pero se modificaron "));
        assertTrue(exception.getMessage().contains(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoBorrarRetornaError() {
        Usuario usuario = crearUsuario();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(0)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoBorrarRetornaExito() {
        Usuario usuario = crearUsuario();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.modificarUsuario(usuario, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);
        assertTrue(spy.getCloseCalled());
    }
}