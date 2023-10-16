package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static org.junit.Assert.assertNull;

import com.ar.bootcampar.model.utilities.Tupla;
import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.support.TestableLogicServices;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class RegistracionDebe {
    @DataPoints("cadenas invalidas")
    public static String[] cadenasInvalidas() {
        return new String[] { "", "  ", null };
    }

    @Theory
    public void retornarNulo_cuandoElNombreEsInvalido(@FromDataPoints("cadenas invalidas") String nombreInvalido) {
        Tupla<Usuario, String> resultado = new TestableLogicServices().registrarUsuario(nombreInvalido, APELLIDO, EMAIL, CLAVE, CLAVE, Rol.Estudiante, TELEFONO, "112233");
        assertNull(resultado.izquierda);
    }

    @Theory
    public void retornarNulo_cuandoElApellidoEsInvalido(@FromDataPoints("cadenas invalidas") String apellidoInvalido) {
        Tupla<Usuario, String> resultado = new TestableLogicServices().registrarUsuario(NOMBRE, apellidoInvalido, EMAIL, CLAVE, CLAVE, Rol.Estudiante, TELEFONO, "112233");
        assertNull(resultado.izquierda);
    }
}