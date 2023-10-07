package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
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
public class BorrarUsuarioDebe {
    @Test
    public void recibirElIdParaBorrar() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.borrarUsuario(usuario);

        assertEquals(String.valueOf(ID), spy.getWhereArgs()[0]);
    }

    @Test
    public void borrarDatosDeLaTablaUsuario() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertEquals("Usuarios", spy.getTableName());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[] { 0, -1, 2 };
    }

    @Theory
    public void lanzarExcepcion_cuandoDeleteRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Usuario usuario = new Usuario(ID, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(affectedRowsInvalido)
                .build();

        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.borrarUsuario(usuario));
        assertTrue(exception.getMessage().startsWith(exception.getMessage()));
        assertTrue(exception.getMessage().endsWith(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarConexion_cuandoBorrarRetornaError() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(-1)
                .build();

        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.borrarUsuario(usuario));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarConexion_cuandoBorrarRetornaExito() {
        Usuario usuario = crearUsuarioDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.borrarUsuario(usuario);
        assertTrue(spy.getCloseCalled());
    }

    private static Usuario crearUsuarioDePrueba() {
        return new Usuario(ID, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);
    }
}