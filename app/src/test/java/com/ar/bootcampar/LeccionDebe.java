package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Course;
import com.ar.bootcampar.model.Leccion;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;

@RunWith(Theories.class)
public class LeccionDebe {
    @Test
    public void serCreado_cuandoLosDatosSonCorrectos() {
        Course curso = crearCursoPorDefecto();
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
        Course curso = crearCursoPorDefecto();

        Exception exception = assertThrows(RuntimeException.class, () -> new Leccion(idInvalido, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, curso));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @NonNull
    private static Course crearCursoPorDefecto() {
        return new Course(ID, TITULO_CURSO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO, IMAGEN_CURSO);
    }

    @DataPoints("cadenas invalidas")
    public static String[] nombresInvalidos() {
        return new String[] { "", null, "   " };
    }

    @Theory
    public void lanzarExcepcion_cuandoTituloEsInvalido(@FromDataPoints("cadenas invalidas") String tituloInvalido) {
        Course curso = crearCursoPorDefecto();

        Exception exception = assertThrows(RuntimeException.class, () -> new Leccion(ID, tituloInvalido, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, curso));
        assertEquals("El título es inválido", exception.getMessage());
    }

    @Theory
    public void lanzarExcepcion_cuandoContenidoEsInvalido(@FromDataPoints("cadenas invalidas") String contenidoInvalido) {
        Course curso = crearCursoPorDefecto();

        Exception exception = assertThrows(RuntimeException.class, () -> new Leccion(ID, TITULO_LECCION, contenidoInvalido, DURACION_LECCION, ORDEN_LECCION, curso));
        assertEquals("El contenido es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoDuracionEsInvalida() {
        Course curso = crearCursoPorDefecto();

        Exception exception = assertThrows(RuntimeException.class, () -> new Leccion(ID, TITULO_LECCION, CONTENIDO_LECCION, -1, ORDEN_LECCION, curso));
        assertEquals("La duración es inválida", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoCursoEsInvalido() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Leccion(ID, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, null));
        assertEquals("El curso es inválido", exception.getMessage());
    }
}