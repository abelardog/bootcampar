package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static org.junit.Assert.assertNull;

import com.ar.bootcampar.model.utilities.ICursorWrapper;
import com.ar.bootcampar.model.IDatabase;
import com.ar.bootcampar.model.utilities.Tupla;
import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.support.CursorWrapperStub;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;
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
        Tupla<Usuario, String> resultado = new TestableLogicServices().registrarUsuario(nombreInvalido, APELLIDO, EMAIL, CLAVE, CLAVE, Rol.Estudiante, TELEFONO, INVITACION_GRUPO);
        assertNull(resultado.izquierda);
    }

    @Theory
    public void retornarNulo_cuandoElApellidoEsInvalido(@FromDataPoints("cadenas invalidas") String apellidoInvalido) {
        Tupla<Usuario, String> resultado = new TestableLogicServices().registrarUsuario(NOMBRE, apellidoInvalido, EMAIL, CLAVE, CLAVE, Rol.Estudiante, TELEFONO, INVITACION_GRUPO);
        assertNull(resultado.izquierda);
    }

    @Theory
    public void retornarNulo_cuandoElEmailEsInvalido(@FromDataPoints("cadenas invalidas") String emailInvalido) {
        Tupla<Usuario, String> resultado = new TestableLogicServices().registrarUsuario(NOMBRE, APELLIDO, emailInvalido, CLAVE, CLAVE, Rol.Estudiante, TELEFONO, INVITACION_GRUPO);
        assertNull(resultado.izquierda);
    }

    @Theory
    public void retornarNulo_cuandoLaClaveEsInvalida(@FromDataPoints("cadenas invalidas") String claveInvalida) {
        Tupla<Usuario, String> resultado = new TestableLogicServices().registrarUsuario(NOMBRE, APELLIDO, EMAIL, claveInvalida, CLAVE, Rol.Estudiante, TELEFONO, INVITACION_GRUPO);
        assertNull(resultado.izquierda);
    }

    @Theory
    public void retornarNulo_cuandoLaConfirmacionDeClaveEsInvalida(@FromDataPoints("cadenas invalidas") String claveInvalida) {
        Tupla<Usuario, String> resultado = new TestableLogicServices().registrarUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, claveInvalida, Rol.Estudiante, TELEFONO, INVITACION_GRUPO);
        assertNull(resultado.izquierda);
    }

    @Theory
    public void retornarNulo_cuandoLaInvitacionEsInvalida(@FromDataPoints("cadenas invalidas") String invitacionInvalida) {
        Tupla<Usuario, String> resultado = new TestableLogicServices().registrarUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, CLAVE, Rol.Estudiante, TELEFONO, invitacionInvalida);
        assertNull(resultado.izquierda);
    }

    @Theory
    public void retornarNulo_cuandoLasClavesSonDistintas() {
        Tupla<Usuario, String> resultado = new TestableLogicServices().registrarUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, OTRA_CLAVE, Rol.Estudiante, TELEFONO, INVITACION_GRUPO);
        assertNull(resultado.izquierda);
    }

    @Theory
    public void retornarNulo_cuandoElGrupoNoExiste() {
        ICursorWrapper cursorWrapper = new CursorWrapperStub.Builder().conCountRetornando(0).build();
        SqliteDatabaseWrapperSpy databaseSpy = new SqliteDatabaseWrapperSpy.Builder().conQueryRetornando(cursorWrapper).build();
        IDatabase database = new TestableDatabase(databaseSpy);
        Tupla<Usuario, String> resultado = new TestableLogicServices(database).registrarUsuario(NOMBRE, APELLIDO, EMAIL, CLAVE, CLAVE, Rol.Estudiante, TELEFONO, INVITACION_GRUPO);
        assertNull(resultado.izquierda);
    }
}