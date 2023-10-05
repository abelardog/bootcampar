package com.ar.bootcampar;

import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Theories.class)
public class UsuarioDebe {
    public static final int ID = 1;
    public static final String NOMBRE = "Luis";
    public static final String APELLIDO = "Agote";
    public static final String EMAIL = "luis.agote@gmail.com";
    public static final String CLAVE = "123456";
    public static final Rol ROL = Rol.Estudiante;
    public static final String TELEFONO = "1234-5678";

    @Test
    public void serCreado_cuandoLosDatosSonCorrectos() {
        Usuario sut = new Usuario(ID, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);

        assertEquals(ID, sut.getId());
        assertEquals(NOMBRE, sut.getNombre());
        assertEquals(APELLIDO, sut.getApellido());
        assertEquals(EMAIL, sut.getEmail());
        assertEquals(CLAVE, sut.getClave());
        assertEquals(ROL, sut.getRol());
        assertEquals(TELEFONO, sut.getTelefono());
    }

    @DataPoints("ids invalidos")
    public static int[] idInvalidos() {
        return new int[] { 0, -1 };
    }

    @DataPoints("cadenas invalidas")
    public static String[] nombresInvalidos() {
        return new String[] { "", null, "   " };
    }

    @DataPoints("emails invalidos")
    public static String[] emailsInvalidos() {
        return new String[] { "", null, "   "/*, "hola", "hola@", "@chat", "hola@gmail", "@gmail.com", "test @gmail.com"*/ };
    }

    @Theory
    public void lanzarExcepcion_cuandoIdEsInvalido(@FromDataPoints("ids invalidos") int idInvalido) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Usuario(idInvalido, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @Theory
    public void lanzarExcepcion_cuandoNombreEsInvalido(@FromDataPoints("cadenas invalidas") String nombreInvalido) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Usuario(ID, nombreInvalido, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Theory
    public void lanzarExcepcion_cuandoApellidoEsInvalido(@FromDataPoints("cadenas invalidas") String apellidoInvalido) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Usuario(ID, NOMBRE, apellidoInvalido, EMAIL, CLAVE, ROL, TELEFONO));
        assertEquals("El apellido es inválido", exception.getMessage());
    }

    @Theory
    public void lanzarExcepcion_cuandoEmailEsInvalido(@FromDataPoints("emails invalidos") String emailInvalido) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Usuario(ID, NOMBRE, APELLIDO, emailInvalido, CLAVE, ROL, TELEFONO));
        assertEquals("El email es inválido", exception.getMessage());
    }

    @Theory
    public void lanzarExcepcion_cuandoClaveEsInvalida(@FromDataPoints("cadenas invalidas") String claveInvalida) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Usuario(ID, NOMBRE, APELLIDO, EMAIL, claveInvalida, ROL, TELEFONO));
        assertEquals("La clave es inválida", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoTelefonoEsNulo() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Usuario(ID, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, null));
        assertEquals("El teléfono es inválido", exception.getMessage());
    }
}