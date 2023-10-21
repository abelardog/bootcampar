package com.ar.bootcampar;

import static org.junit.Assert.*;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCursoDePrueba;

import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Leccion;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;

public class CrearLeccionDebe {
    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearLeccionConTituloInvalido() {
        SqliteDatabaseWrapperSpy dummy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();

        Database sut = new TestableDatabase(dummy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearLeccion(null, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertEquals("El título es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearLeccionConCursoInvalido() {
        SqliteDatabaseWrapperSpy dummy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(dummy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, null));
        assertEquals("El curso es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearLeccionConDuracionInvalida() {
        SqliteDatabaseWrapperSpy dummy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(dummy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, -1, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertEquals("La duración es inválida", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElTituloEsInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearLeccion(null, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaDuracionEsInvalida() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, -1, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElCursoEsInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, null));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeLaLeccion() {
        Curso curso = crearCursoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, curso);

        assertEquals(TITULO_LECCION, spy.getInsertedValues().get("Titulo"));
        assertEquals(CONTENIDO_LECCION, spy.getInsertedValues().get("Contenido"));
        assertEquals(DURACION_LECCION, spy.getInsertedValues().get("Duracion"));
        assertEquals(ORDEN_LECCION, spy.getInsertedValues().get("Orden"));
        assertEquals(VINCULO_LECCION, spy.getInsertedValues().get("Vinculo"));
        assertEquals(ID_CURSO, spy.getInsertedValues().get("CursoId"));
    }

    @Test
    public void insertarDatosEnTablaLeccion() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba());

        assertEquals("Lecciones", spy.getTableName());
    }

    @Test
    public void retornarLeccion_cuandoSeInsertaLeccionEnBaseDeDatos() {
        Curso curso = crearCursoDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(14)
                .build();
        Database database = new TestableDatabase(spy);
        Leccion sut = database.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, curso);

        assertNotNull(sut);
        assertEquals(14, sut.getId());
        assertEquals(TITULO_LECCION, sut.getTitulo());
        assertEquals(CONTENIDO_LECCION, sut.getContenido());
        assertEquals(DURACION_LECCION, sut.getDuracion());
        assertEquals(ORDEN_LECCION, sut.getOrden());
        assertEquals(VINCULO_LECCION, sut.getVinculo());
        assertSame(curso, sut.getCurso());
    }

    @Test
    public void lanzarExcepcion_cuandoInsertRetornaError() {
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertEquals("Error creando lección", exception.getMessage());
    }

    @Test
    public void hacerRollback_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
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
        assertThrows(RuntimeException.class, () -> database.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeInsertaLeccionEnBaseDeDatos() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        database.crearLeccion(TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba());
        assertTrue(spy.getCloseCalled());
    }
}