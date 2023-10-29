package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtroCursoDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ModificarCursoDebe {
    @Test
    public void lanzarExcepcion_cuandoSeQuiereModificarObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarCurso(null, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertEquals("El curso es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnTituloInvalido() {
        Curso curso = crearCursoDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarCurso(curso, TITULO_CURSO_INVALIDO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertEquals("El título es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnaDescripcionInvalida() {
        Curso curso = crearCursoDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO_INVALIDA, IMAGEN_CURSO, NIVEL_CURSO));
        assertEquals("La descripción es inválida", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnaImagenInvalida() {
        Curso curso = crearCursoDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO_INVALIDA, NIVEL_CURSO));
        assertEquals("El link de imagen es inválida", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnNivelInvalido() {
        Curso curso = crearCursoDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO_INVALIDO));
        assertEquals("El nivel es inválido", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElTituloEsInvalido() {
        Curso curso = crearCursoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarCurso(curso, TITULO_CURSO_INVALIDO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaDescripcionEsInvalida() {
        Curso curso = crearCursoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO_INVALIDA, IMAGEN_CURSO, NIVEL_CURSO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaImagenEsInvalida() {
        Curso curso = crearOtroCursoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO_INVALIDA, NIVEL_CURSO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElNivelInvalido() {
        Curso curso = crearOtroCursoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO_INVALIDO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeCurso() {
        Curso curso = crearCursoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarCurso(curso, OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, OTRA_IMAGEN_CURSO, OTRO_NIVEL_CURSO);

        assertEquals(OTRO_TITULO_CURSO, spy.getInsertedValues().get("Titulo"));
        assertEquals(OTRA_DESCRIPCION_CURSO, spy.getInsertedValues().get("Descripcion"));
        assertEquals(OTRA_IMAGEN_CURSO, spy.getInsertedValues().get("Imagen"));
        assertEquals(OTRO_NIVEL_CURSO, spy.getInsertedValues().get("Nivel"));
    }

    @Test
    public void insertarDatosEnTablaCursoDePrueba() {
        Curso curso = crearCursoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO);

        assertEquals("Cursos", spy.getTableName());
    }

    @Test
    public void retornarCursoModificado_cuandoSeModificaCursoEnBaseDeDatos() {
        Curso curso = crearCursoDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        Curso sut = database.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO);

        assertNotNull(sut);
        assertEquals(18, sut.getId());
        assertEquals(TITULO_CURSO, sut.getTitulo());
        assertEquals(DESCRIPCION_CURSO, sut.getDescripcion());
        assertEquals(IMAGEN_CURSO, sut.getImagen());
        assertEquals(NIVEL_CURSO, sut.getNivel());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[]{0, -1, 2};
    }

    @Theory
    public void lanzarExcepcion_cuandoUpdateRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Curso curso = crearCursoDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(affectedRowsInvalido)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertTrue(exception.getMessage().startsWith("Se esperaba modificar un único curso pero se modificaron "));
        assertTrue(exception.getMessage().contains(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaError() {
        Curso curso = crearCursoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(0)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaExito() {
        Curso curso = crearCursoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.modificarCurso(curso, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO);
        assertTrue(spy.getCloseCalled());
    }
}