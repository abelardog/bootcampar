package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Course;
import com.ar.bootcampar.model.Leccion;
import com.ar.bootcampar.model.Usuario;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Theories.class)
public class LeccionDebe {
    @Test
    public void serCreado_cuandoLosDatosSonCorrectos() {
        Course curso = new Course(IMAGEN_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO);
        Leccion sut = new Leccion(ID, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, curso);

        assertEquals(ID, sut.getId());
        assertEquals(TITULO_LECCION, sut.getTitulo());
        assertEquals(CONTENIDO_LECCION, sut.getContenido());
        assertEquals(DURACION_LECCION, sut.getDuracion());
        assertEquals(ORDEN_LECCION, sut.getOrden());
        assertSame(curso, sut.getCurso());
    }

    @DataPoints("ids invalidos")
    public static int[] idInvalidos() {
        return new int[] { 0, -1 };
    }

    @Theory
    public void lanzarExcepcion_cuandoIdEsInvalido(@FromDataPoints("ids invalidos") int idInvalido) {
        Course curso = new Course(IMAGEN_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO);

        Exception exception = assertThrows(RuntimeException.class, () -> new Leccion(idInvalido, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, curso));
        assertEquals("El id es inválido", exception.getMessage());
    }
}