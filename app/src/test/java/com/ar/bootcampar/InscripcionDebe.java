package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.Usuario;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class InscripcionDebe {
    @Test
    public void serCreado_cuandoLosDatosSonCorrectos() {
        Usuario usuario = crearUsuarioPorDefecto();
        Curso curso = crearCursoPorDefecto();
        Inscripcion sut = new Inscripcion(ID, usuario, curso, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);

        assertEquals(ID, sut.getId());
        assertSame(usuario, sut.getUsuario());
        assertSame(curso, sut.getCurso());
        assertEquals(PUNTUACION_INSCRIPCION, sut.getPuntuacion());
        assertEquals(FAVORITO_INSCRIPCION, sut.getFavorito());
        assertEquals(ULTIMA_LECCION_INSCRIPCION, sut.getUltimaLeccion());
    }

    private static Curso crearCursoPorDefecto() {
        return new Curso(ID, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO);
    }

    private static Usuario crearUsuarioPorDefecto() {
        return new Usuario(ID, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);
    }

    @DataPoints("ids invalidos")
    public static int[] idInvalidos() {
        return new int[] { 0, -1 };
    }

    @Theory
    public void lanzarExcepcion_cuandoIdEsInvalido(@FromDataPoints("ids invalidos") int idInvalido) {
        Usuario usuario = crearUsuarioPorDefecto();
        Curso curso = crearCursoPorDefecto();

        Exception exception = assertThrows(RuntimeException.class, () -> new Inscripcion(idInvalido, usuario, curso, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoElUsuarioEsInvalido() {
        Curso curso = crearCursoPorDefecto();

        Exception exception = assertThrows(RuntimeException.class, () -> new Inscripcion(ID, USUARIO_INVALIDO, curso, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("El usuario es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoElCursoEsInvalido() {
        Usuario usuario = crearUsuarioPorDefecto();

        Exception exception = assertThrows(RuntimeException.class, () -> new Inscripcion(ID, usuario, CURSO_INVALIDO, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("El curso es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoLaPuntuacionEsInvalida() {
        Usuario usuario = crearUsuarioPorDefecto();
        Curso curso = crearCursoPorDefecto();

        Exception exception = assertThrows(RuntimeException.class, () -> new Inscripcion(ID, usuario, curso, PUNTUACION_INSCRIPCION_INVALIDA, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("La puntuación es inválida", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoLaUltimaLeccionEsInvalida() {
        Usuario usuario = crearUsuarioPorDefecto();
        Curso curso = crearCursoPorDefecto();

        Exception exception = assertThrows(RuntimeException.class, () -> new Inscripcion(ID, usuario, curso, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION_INVALIDA));
        assertEquals("La última lección es inválida", exception.getMessage());
    }
}