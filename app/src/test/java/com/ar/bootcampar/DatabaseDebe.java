package com.ar.bootcampar;

import static org.junit.Assert.*;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.support.DatabaseConInsertRetornandoId;
import com.ar.bootcampar.support.DatabaseEspiandoTransacciones;
import com.ar.bootcampar.support.DatabaseSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;

public class DatabaseDebe {
    @Test
    public void recibirTodosLosDatosDeUsuario_alCrearUnUsuario() {
        DatabaseSpy spy = new DatabaseSpy();
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
    public void insertarEnTablaUsuario_alCrearUnUsuario() {
        DatabaseSpy spy = new DatabaseSpy();
        Database sut = new TestableDatabase(spy);
        sut.crearUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertEquals("Usuarios", spy.getTableName());
    }

    @Test
    public void retornarUsuario_cuandoSeInsertaUsuarioEnBaseDeDatos() {
        ISQLiteDatabaseWrapper spy = new DatabaseConInsertRetornandoId(14);
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

}