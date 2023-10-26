package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearDivisionDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Division;
import com.ar.bootcampar.model.Database;
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
public class BorrarDivisionDebe {
    @Test
    public void lanzarExcepcion_cuandoSeBorraObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.borrarDivision(null));
        assertEquals("La división es nula", exception.getMessage());
    }

    @Test
    public void recibirElIdParaBorrar() {
        Division division = crearDivisionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.borrarDivision(division);

        assertEquals(String.valueOf(ID_DIVISION), spy.getWhereArgs()[0]);
    }

    @Test
    public void borrarDatosDeLaTablaDivision() {
        Division division = crearDivisionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.borrarDivision(division);

        assertEquals("Divisiones", spy.getTableName());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[] { 0, -1, 2 };
    }

    @Theory
    public void lanzarExcepcion_cuandoDeleteRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Division division = crearDivisionDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(affectedRowsInvalido)
                .build();

        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.borrarDivision(division));
        assertTrue(exception.getMessage().startsWith("Se esperaba borrar una única división pero se borraron"));
        assertTrue(exception.getMessage().endsWith(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoBorrarRetornaError() {
        Division division = crearDivisionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(-1)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.borrarDivision(division));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoBorrarRetornaExito() {
        Division division = crearDivisionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.borrarDivision(division);
        assertTrue(spy.getCloseCalled());
    }
}