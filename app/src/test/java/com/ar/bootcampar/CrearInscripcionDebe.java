package com.ar.bootcampar;

import static org.junit.Assert.*;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;

import com.ar.bootcampar.model.Course;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;

public class CrearInscripcionDebe {
    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearInscripcionConUsuarioInvalido() {
        SqliteDatabaseWrapperSpy dummy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(dummy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearInscripcion(USUARIO_INVALIDO, crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("El usuario es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearInscripcionConCursoInvalido() {
        SqliteDatabaseWrapperSpy dummy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(dummy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearInscripcion(crearUsuarioDePrueba(), CURSO_INVALIDO, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("El curso es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearInscripcionConPuntuacionInvalida() {
        SqliteDatabaseWrapperSpy stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearInscripcion(crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION_INVALIDA, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("La puntuación es inválida", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearInscripcionConUltimaLeccionInvalida() {
        SqliteDatabaseWrapperSpy stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearInscripcion(crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION_INVALIDA));
        assertEquals("La última lección es inválida", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElUsuarioEsInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearInscripcion(USUARIO_INVALIDO, crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElCursoEsInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearInscripcion(crearUsuarioDePrueba(), CURSO_INVALIDO, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaPuntuacionEsInvalida() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearInscripcion(crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION_INVALIDA, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaUltimaLeccionEsInvalida() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearInscripcion(crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION_INVALIDA));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeLaInscripcion() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearInscripcion(crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);

        assertEquals(ID_USUARIO, spy.getInsertedValues().get("UsuarioId"));
        assertEquals(ID_CURSO, spy.getInsertedValues().get("CursoId"));
        assertEquals(PUNTUACION_INSCRIPCION, spy.getInsertedValues().get("Puntuacion"));
        assertEquals(FAVORITO_INSCRIPCION, spy.getInsertedValues().get("Favorito"));
        assertEquals(ULTIMA_LECCION_INSCRIPCION, spy.getInsertedValues().get("UltimaLeccion"));
    }

    @Test
    public void insertarDatosEnTablaInscripcion() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearInscripcion(crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);

        assertEquals("Inscripciones", spy.getTableName());
    }

    @Test
    public void retornarInscripcion_cuandoSeInsertaInscripcionEnBaseDeDatos() {
        Usuario usuario = crearUsuarioDePrueba();
        Course curso = crearCursoDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(14)
                .build();
        Database database = new TestableDatabase(spy);
        Inscripcion sut = database.crearInscripcion(usuario, curso, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);

        assertNotNull(sut);
        assertEquals(14, sut.getId());
        assertSame(usuario, sut.getUsuario());
        assertSame(curso, sut.getCurso());
        assertEquals(PUNTUACION_INSCRIPCION, sut.getPuntuacion());
        assertEquals(FAVORITO_INSCRIPCION, sut.getFavorito());
        assertEquals(ULTIMA_LECCION_INSCRIPCION, sut.getUltimaLeccion());
    }

    @Test
    public void lanzarExcepcion_cuandoInsertRetornaError() {
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearInscripcion(crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("Error creando inscripción", exception.getMessage());
    }

    @Test
    public void hacerRollback_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearInscripcion(crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertTrue(spy.getBeginTransactionCalled());
        assertFalse(spy.getTransactionSuccessfulCalled());
        assertTrue(spy.getEndTransactionCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearInscripcion(crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeInsertaInscripcionEnBaseDeDatos() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        database.crearInscripcion(crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);
        assertTrue(spy.getCloseCalled());
    }
}