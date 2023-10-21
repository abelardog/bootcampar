package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCategoriaDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearCursoDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Categoria;
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
public class BorrarCategoriaDebe {
    @Test
    public void lanzarExcepcion_cuandoSeBorraObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.borrarCategoria(null));
        assertEquals("La categoría es nula", exception.getMessage());
    }

    @Test
    public void recibirElIdParaBorrar() {
        Categoria categoria = crearCategoriaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.borrarCategoria(categoria);

        assertEquals(String.valueOf(ID_CATEGORIA), spy.getWhereArgs()[0]);
    }

    @Test
    public void borrarDatosDeLaTablaCategoria() {
        Categoria categoria = crearCategoriaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.borrarCategoria(categoria);

        assertEquals("Categorias", spy.getTableName());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[] { 0, -1, 2 };
    }

    @Theory
    public void lanzarExcepcion_cuandoDeleteRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Categoria categoria = crearCategoriaDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(affectedRowsInvalido)
                .build();

        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.borrarCategoria(categoria));
        assertTrue(exception.getMessage().startsWith("Se esperaba borrar una única categoría pero se borraron"));
        assertTrue(exception.getMessage().endsWith(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarConexion_cuandoBorrarRetornaError() {
        Categoria categoria = crearCategoriaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(-1)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.borrarCategoria(categoria));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarConexion_cuandoBorrarRetornaExito() {
        Categoria categoria = crearCategoriaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.borrarCategoria(categoria);
        assertTrue(spy.getCloseCalled());
    }
}