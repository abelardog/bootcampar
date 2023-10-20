package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCategoriaDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearGrupoDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Categoria;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ModificarCategoriaDebe {
    @Test
    public void lanzarExcepcion_cuandoSeQuiereModificarObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarCategoria(null, NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA));
        assertEquals("La categoría es nula", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnNombreInvalido() {
        Categoria categoria = crearCategoriaDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarCategoria(categoria, "", DESCRIPCION_CATEGORIA));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElNombreEsInvalido() {
        Categoria categoria = crearCategoriaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarCategoria(categoria, NOMBRE_CATEGORIA_INVALIDA, DESCRIPCION_CATEGORIA));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeLaCategoria() {
        Categoria categoria = crearCategoriaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarCategoria(categoria, OTRO_NOMBRE_CATEGORIA, OTRA_DESCRIPCION_CATEGORIA);

        assertEquals(OTRO_NOMBRE_CATEGORIA, spy.getInsertedValues().get("Nombre"));
        assertEquals(OTRA_DESCRIPCION_CATEGORIA, spy.getInsertedValues().get("Descripcion"));
    }

    @Test
    public void insertarDatosEnTablaCategoria() {
        Categoria categoria = crearCategoriaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarCategoria(categoria, OTRO_NOMBRE_CATEGORIA, OTRA_DESCRIPCION_CATEGORIA);

        assertEquals("Categorias", spy.getTableName());
    }

    @Test
    public void retornarCategoriaModificada_cuandoSeModificaCategoriaEnBaseDeDatos() {
        Categoria categoria = crearCategoriaDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        Categoria sut = database.modificarCategoria(categoria, OTRO_NOMBRE_CATEGORIA, OTRA_DESCRIPCION_CATEGORIA);

        assertNotNull(sut);
        assertEquals(ID_CATEGORIA, sut.getId());
        assertEquals(OTRO_NOMBRE_CATEGORIA, sut.getNombre());
        assertEquals(OTRA_DESCRIPCION_CATEGORIA, sut.getDescripcion());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[]{0, -1, 2};
    }

    @Theory
    public void lanzarExcepcion_cuandoUpdateRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Categoria categoria = crearCategoriaDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(affectedRowsInvalido)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.modificarCategoria(categoria, NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA));
        assertTrue(exception.getMessage().startsWith("Se esperaba modificar una única categoría pero se modificaron "));
        assertTrue(exception.getMessage().contains(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaError() {
        Categoria categoria = crearCategoriaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(0)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.modificarCategoria(categoria, NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaExito() {
        Categoria categoria = crearCategoriaDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.modificarCategoria(categoria, NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA);
        assertTrue(spy.getCloseCalled());
    }
}