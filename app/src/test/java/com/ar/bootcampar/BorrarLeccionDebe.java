package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearLeccionDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Leccion;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class BorrarLeccionDebe {
    @Test
    public void lanzarExcepcion_cuandoSeBorraObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.borrarLeccion(null));
        assertEquals("La lección es nula", exception.getMessage());
    }

    @Test
    public void recibirElIdParaBorrar() {
        Leccion leccion = crearLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.borrarLeccion(leccion);

        assertEquals(String.valueOf(ID_LECCION), spy.getWhereArgs()[0]);
    }

    @Test
    public void borrarDatosDeLaTablaLeccion() {
        Leccion leccion = crearLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.borrarLeccion(leccion);

        assertEquals("Lecciones", spy.getTableName());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[] { 0, -1, 2 };
    }

    @Theory
    public void lanzarExcepcion_cuandoDeleteRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Leccion leccion = crearLeccionDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(affectedRowsInvalido)
                .build();

        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.borrarLeccion(leccion));
        assertTrue(exception.getMessage().startsWith("Se esperaba borrar una única lección pero se borraron"));
        assertTrue(exception.getMessage().endsWith(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoBorrarRetornaError() {
        Leccion leccion = crearLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(-1)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.borrarLeccion(leccion));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoBorrarRetornaExito() {
        Leccion leccion = crearLeccionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.borrarLeccion(leccion);
        assertTrue(spy.getCloseCalled());
    }
}