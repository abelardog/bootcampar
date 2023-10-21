package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearLeccionDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtraLeccionDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtroCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtroUsuarioDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Leccion;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ModificarLeccionDebe {
    @Test
    public void lanzarExcepcion_cuandoSeQuiereModificarObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarLeccion(null, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertEquals("La lección es nula", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnTituloInvalido() {
        Leccion leccion = crearLeccionDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarLeccion(leccion, null, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertEquals("El título es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarDuracionInvalida() {
        Leccion leccion = crearLeccionDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarLeccion(leccion, TITULO_LECCION, CONTENIDO_LECCION, -1, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertEquals("La duración es inválida", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnCursoInvalido() {
        Leccion leccion = crearLeccionDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarLeccion(leccion, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, null));
        assertEquals("El curso es nulo", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElTituloEsInvalido() {
        Leccion leccion = crearLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarLeccion(leccion, null, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElCursoEsInvalido() {
        Leccion leccion = crearLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarLeccion(leccion, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, null));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaDuracionEsInvalida() {
        Leccion leccion = crearLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarLeccion(leccion, TITULO_LECCION, CONTENIDO_LECCION, -1, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeLaLeccion() {
        Leccion leccion = crearOtraLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarLeccion(leccion, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba());

        assertEquals(TITULO_LECCION, spy.getInsertedValues().get("Titulo"));
        assertEquals(CONTENIDO_LECCION, spy.getInsertedValues().get("Contenido"));
        assertEquals(DURACION_LECCION, spy.getInsertedValues().get("Duracion"));
        assertEquals(ORDEN_LECCION, spy.getInsertedValues().get("Orden"));
        assertEquals(VINCULO_LECCION, spy.getInsertedValues().get("Vinculo"));
        assertEquals(ID_CURSO, spy.getInsertedValues().get("CursoId"));
    }

    @Test
    public void insertarDatosEnTablaLeccion() {
        Leccion leccion = crearLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarLeccion(leccion, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba());

        assertEquals("Lecciones", spy.getTableName());
    }

    @Test
    public void retornarLeccionModificado_cuandoSeModificaLeccionEnBaseDeDatos() {
        Curso curso = crearOtroCursoDePrueba();
        Leccion leccion = crearLeccionDePrueba();

        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        Leccion sut = database.modificarLeccion(leccion, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, curso);

        assertNotNull(sut);
        assertEquals(ID_LECCION, sut.getId());
        assertEquals(curso, sut.getCurso());
        assertEquals(TITULO_LECCION, sut.getTitulo());
        assertEquals(CONTENIDO_LECCION, sut.getContenido());
        assertEquals(DURACION_LECCION, sut.getDuracion());
        assertEquals(ORDEN_LECCION, sut.getOrden());
        assertEquals(VINCULO_LECCION, sut.getVinculo());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[]{0, -1, 2};
    }

    @Theory
    public void lanzarExcepcion_cuandoUpdateRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Leccion leccion = crearLeccionDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(affectedRowsInvalido)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.modificarLeccion(leccion, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertTrue(exception.getMessage().startsWith("Se esperaba modificar una única lección pero se modificaron "));
        assertTrue(exception.getMessage().contains(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaError() {
        Leccion leccion = crearLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(0)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.modificarLeccion(leccion, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba()));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaExito() {
        Leccion leccion = crearLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.modificarLeccion(leccion, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION, crearCursoDePrueba());
        assertTrue(spy.getCloseCalled());
    }
}