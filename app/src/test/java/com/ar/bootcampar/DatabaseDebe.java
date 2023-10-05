package com.ar.bootcampar;

import static org.junit.Assert.*;

import static com.ar.bootcampar.Constants.*;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Rol;

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
}