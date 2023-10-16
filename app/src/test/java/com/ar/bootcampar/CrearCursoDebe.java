package com.ar.bootcampar;

import static org.junit.Assert.*;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Curso;

import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;

public class CrearCursoDebe {
    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearCursoConTituloInvalido() {
        SqliteDatabaseWrapperSpy stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearCurso(TITULO_CURSO_INVALIDO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertEquals("El título es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearCursoConDescripcionInvalida() {
        SqliteDatabaseWrapperSpy stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearCurso(TITULO_CURSO, DESCRIPCION_CURSO_INVALIDA, ES_FAVORITO_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertEquals("La descripción es inválida", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearCursoConImagenInvalida() {
        SqliteDatabaseWrapperSpy stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearCurso(TITULO_CURSO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO, IMAGEN_CURSO_INVALIDA, NIVEL_CURSO));
        assertEquals("El link de imágen es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearCursoConNivelInvalido() {
        SqliteDatabaseWrapperSpy stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearCurso(TITULO_CURSO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO, IMAGEN_CURSO, NIVEL_CURSO_INVALIDO));
        assertEquals("El nivel es inválido", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElTituloEsInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearCurso(TITULO_CURSO_INVALIDO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaDescripcionEsInvalida() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearCurso(TITULO_CURSO, DESCRIPCION_CURSO_INVALIDA, ES_FAVORITO_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaImagenEsInvalida() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearCurso(TITULO_CURSO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO, IMAGEN_CURSO_INVALIDA, NIVEL_CURSO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElNivelInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearCurso(TITULO_CURSO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO, IMAGEN_CURSO, NIVEL_CURSO_INVALIDO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeCurso() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearCurso(OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, OTRO_ES_FAVORITO_CURSO, OTRA_IMAGEN_CURSO, OTRO_NIVEL_CURSO);

        assertEquals(OTRO_TITULO_CURSO, spy.getInsertedValues().get("Titulo"));
        assertEquals(OTRA_DESCRIPCION_CURSO, spy.getInsertedValues().get("Descripcion"));
        assertEquals(OTRA_IMAGEN_CURSO, spy.getInsertedValues().get("Imagen"));
        assertEquals(OTRO_NIVEL_CURSO, spy.getInsertedValues().get("Nivel"));
    }

    @Test
    public void insertarDatosEnTablaCurso() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearCurso(OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, OTRO_ES_FAVORITO_CURSO, OTRA_IMAGEN_CURSO, OTRO_NIVEL_CURSO);

        assertEquals("Cursos", spy.getTableName());
    }

    @Test
    public void retornarCurso_cuandoSeInsertaCursoEnBaseDeDatos() {
        ISQLiteDatabaseWrapper stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(14)
                .build();
        Database database = new TestableDatabase(stub);
        Curso sut = database.crearCurso(OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, OTRO_ES_FAVORITO_CURSO, OTRA_IMAGEN_CURSO, OTRO_NIVEL_CURSO);

        assertNotNull(sut);
        assertEquals(14, sut.getId());
        assertEquals(OTRO_TITULO_CURSO, sut.getTitle());
        assertEquals(OTRA_DESCRIPCION_CURSO, sut.getDescription());
        assertEquals(OTRA_IMAGEN_CURSO, sut.getImageName());
        assertEquals(OTRO_NIVEL_CURSO, sut.getNivel());
    }

    @Test
    public void lanzarExcepcion_cuandoInsertRetornaError() {
        ISQLiteDatabaseWrapper stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearCurso(OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, OTRO_ES_FAVORITO_CURSO, OTRA_IMAGEN_CURSO, OTRO_NIVEL_CURSO));
        assertEquals("Error creando curso", exception.getMessage());
    }

    @Test
    public void hacerRollback_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearCurso(OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, OTRO_ES_FAVORITO_CURSO, OTRA_IMAGEN_CURSO, OTRO_NIVEL_CURSO));
        assertTrue(spy.getBeginTransactionCalled());
        assertFalse(spy.getTransactionSuccessfulCalled());
        assertTrue(spy.getEndTransactionCalled());
    }

    @Test
    public void cerrarConexion_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearCurso(OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, OTRO_ES_FAVORITO_CURSO, OTRA_IMAGEN_CURSO, OTRO_NIVEL_CURSO));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarConexion_cuandoSeInsertaCursoEnBaseDeDatos() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        database.crearCurso(OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, OTRO_ES_FAVORITO_CURSO, OTRA_IMAGEN_CURSO, OTRO_NIVEL_CURSO);
        assertTrue(spy.getCloseCalled());
    }
}
