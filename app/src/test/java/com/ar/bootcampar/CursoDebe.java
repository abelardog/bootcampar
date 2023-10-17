package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import com.ar.bootcampar.model.Curso;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Theories.class)
public class CursoDebe {
    @Test
    public void serCreado_cuandoLosDatosSonCorrectos() {
        Curso sut = new Curso(ID_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO);

        assertEquals(ID_CURSO, sut.getId());
        assertSame(TITULO_CURSO, sut.getTitulo());
        assertSame(DESCRIPCION_CURSO, sut.getDescripcion());
        assertEquals(IMAGEN_CURSO, sut.getImagen());
        assertEquals(NIVEL_CURSO, sut.getNivel());
    }

    @DataPoints("ids invalidos")
    public static int[] idInvalidos() {
        return new int[] { 0, -1 };
    }
    @DataPoints("cadenas invalidas")
    public static String[] cadenasInvalidos() {
        return new String[] { "", null, "   " };
    }
    @DataPoints("links invalidos")
    public static String[] linksInvalidos() {
        return new String[] { "", null, "   ", /*"link", "link.com", "http://link.com", "https://link.com", "www.link.com"*/};
    }
    @DataPoints("niveles invalidos")
    public static int[] nivelesInvalidos() {
        return new int[] { 0, -1, 4 };
    }

    @Theory
    public void lanzarExcepcion_cuandoIdEsInvalido(@FromDataPoints("ids invalidos") int idInvalido) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Curso(idInvalido, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @Theory
    public void lanzarExcepcion_cuandoTituloEsInvalido(@FromDataPoints("cadenas invalidas") String tituloInvalido) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Curso(ID_CURSO, tituloInvalido, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertEquals("El título es inválido", exception.getMessage());
    }

    @Theory
    public void lanzarExcepcion_cuandoDescripcionEsInvalida(@FromDataPoints("cadenas invalidas") String descripcionInvalida) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Curso(ID_CURSO, TITULO_CURSO, descripcionInvalida, IMAGEN_CURSO, NIVEL_CURSO));
        assertEquals("La descripción es inválida", exception.getMessage());
    }

    @Theory
    public void lanzarExcepcion_cuandoLinkImagenEsInvalido(@FromDataPoints("links invalidos") String imagenInvalida) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Curso(ID_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, imagenInvalida, NIVEL_CURSO));
        assertEquals("El link de imagen es inválida", exception.getMessage());
    }
    @Theory
    public void lanzarExcepcion_cuandoNivelEsInvalido(@FromDataPoints("niveles invalidos") int nivelInvalido) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Curso(ID_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, nivelInvalido));
        assertEquals("El nivel es inválido", exception.getMessage());
    }
}