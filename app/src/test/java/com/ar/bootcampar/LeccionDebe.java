package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Course;
import com.ar.bootcampar.model.Leccion;

import org.junit.Test;

import static org.junit.Assert.*;

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
}