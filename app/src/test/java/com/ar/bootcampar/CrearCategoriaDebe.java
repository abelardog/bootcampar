package com.ar.bootcampar;

import static org.junit.Assert.*;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Categoria;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;

public class CrearCategoriaDebe {
    @Test
    public void lanzarExcepcion_cuandoSeIntentaCrearCategoriaConNombreInvalido() {
        SqliteDatabaseWrapperSpy stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.crearCategoria("", DESCRIPCION_CATEGORIA));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElNombreEsInvalido() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.crearCategoria("", DESCRIPCION_CATEGORIA));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeCategoria() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearCategoria(NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA);

    assertEquals(NOMBRE_CATEGORIA, spy.getInsertedValues().get("Nombre"));
        assertEquals(DESCRIPCION_CATEGORIA, spy.getInsertedValues().get("Descripcion"));
    }

    @Test
    public void insertarDatosEnTablaCategoria() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.crearCategoria(NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA);

        assertEquals("Categorias", spy.getTableName());
    }

    @Test
    public void retornarGrupo_cuandoSeInsertaCategoriaEnBaseDeDatos() {
        ISQLiteDatabaseWrapper stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(14)
                .build();
        Database database = new TestableDatabase(stub);
        Categoria sut = database.crearCategoria(NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA);

        assertNotNull(sut);
        assertEquals(14, sut.getId());
        assertEquals(NOMBRE_CATEGORIA, sut.getNombre());
        assertEquals(DESCRIPCION_CATEGORIA, sut.getDescripcion());
    }

    @Test
    public void lanzarExcepcion_cuandoInsertRetornaError() {
        ISQLiteDatabaseWrapper stub = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(stub);
        Exception exception = assertThrows(RuntimeException.class, () -> database.crearCategoria(NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA));
        assertEquals("Error creando categoría", exception.getMessage());
    }

    @Test
    public void hacerRollback_cuandoInsertRetornaError() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(-1)
                .build();
        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.crearCategoria(NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA));
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
        assertThrows(RuntimeException.class, () -> database.crearCategoria(NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeInsertaCategoriaEnBaseDeDatos() {
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        database.crearCategoria(NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA);
        assertTrue(spy.getCloseCalled());
    }
}