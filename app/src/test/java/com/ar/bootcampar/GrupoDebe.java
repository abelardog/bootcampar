package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.ar.bootcampar.model.Grupo;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class GrupoDebe {
    @Test
    public void serCreado_cuandoLosDatosSonCorrectos() {
        Grupo sut = new Grupo(ID, NOMBRE_GRUPO, INVITACION_GRUPO);

        assertEquals(ID, sut.getId());
        assertEquals(NOMBRE_GRUPO, sut.getNombre());
        assertEquals(INVITACION_GRUPO, sut.getInvitacion());
    }

    @DataPoints("ids invalidos")
    public static int[] idInvalidos() {
        return new int[] { 0, -1 };
    }

    @Theory
    public void lanzarExcepcion_cuandoIdEsInvalido(@FromDataPoints("ids invalidos") int idInvalido) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Grupo(idInvalido, NOMBRE_GRUPO, INVITACION_GRUPO));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @DataPoints("cadenas invalidas")
    public static String[] nombresInvalidos() {
        return new String[] { "", null, "   " };
    }

    @Theory
    public void lanzarExcepcion_cuandoNombreEsInvalido(@FromDataPoints("cadenas invalidas") String nombreInvalido) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Grupo(ID, nombreInvalido, INVITACION_GRUPO));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Theory
    public void lanzarExcepcion_cuandoInvitacionEsInvalida(@FromDataPoints("cadenas invalidas") String invitacionInvalida) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Grupo(ID, NOMBRE_GRUPO, invitacionInvalida));
        assertEquals("La invitación es inválida", exception.getMessage());
    }
}