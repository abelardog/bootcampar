package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.annotation.NonNull;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;

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
}