package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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
public class BorrarGrupoDebe {
    @Test
    public void lanzarExcepcion_cuandoSeBorraObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.borrarGrupo(null));
        assertEquals("El grupo es nulo", exception.getMessage());
    }

    @Test
    public void recibirElIdParaBorrar() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.borrarGrupo(grupo);

        assertEquals(String.valueOf(ID), spy.getWhereArgs()[0]);
    }

    @Test
    public void borrarDatosDeLaTablaGrupo() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.borrarGrupo(grupo);

        assertEquals("Grupos", spy.getTableName());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[] { 0, -1, 2 };
    }

    @Theory
    public void lanzarExcepcion_cuandoDeleteRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Grupo grupo = crearGrupoDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(affectedRowsInvalido)
                .build();

        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.borrarGrupo(grupo));
        assertTrue(exception.getMessage().startsWith("Se esperaba borrar un único grupo pero se borraron"));
        assertTrue(exception.getMessage().endsWith(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoBorrarRetornaError() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(-1)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.borrarGrupo(grupo));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoBorrarRetornaExito() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.borrarGrupo(grupo);
        assertTrue(spy.getCloseCalled());
    }

    private static Grupo crearGrupoDePrueba() {
        return new Grupo(ID, NOMBRE_GRUPO, INVITACION_GRUPO);
    }
}