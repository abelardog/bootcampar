package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.TestChecker.probarCerrarBaseDeDatosAlBuscarConExito;
import static com.ar.bootcampar.support.TestChecker.probarCerrarBaseDeDatosAlBuscarSinExito;
import static com.ar.bootcampar.support.TestChecker.probarCerrarCursorAlBuscarConExito;
import static com.ar.bootcampar.support.TestChecker.probarCerrarCursorAlBuscarSinExito;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Categoria;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.IDatabase;
import com.ar.bootcampar.support.CursorWrapperStub;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.function.Function;

@RunWith(Theories.class)
public class BuscarCategoriaDebe {
    @Test
    public void recibirElIdABuscarCorrectamente() {
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarCategoriaOExplotar(ID);

        assertEquals(String.valueOf(ID), spy.getSelectionArgs()[0]);
    }

    private static CursorWrapperStub crearCursorStub() {
        return new CursorWrapperStub.Builder()
                .conColumnas(Arrays.asList("Id", "Nombre", "Descripcion"))
                .conValores(Arrays.asList(ID_CATEGORIA, NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA))
                .conCountRetornando(1)
                .build();
    }

    @Test
    public void buscarDatosEnTablaGrupo_cuandoSeBuscaPorId() {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarCategoriaOExplotar(ID_CATEGORIA);

        assertEquals("Categorias", spy.getTableName());
    }

    @Test
    public void retornarNull_cuandoNoSeEncuentraElNombre() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(0)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Categoria resultado = sut.buscarCategoriaONada(NOMBRE_CATEGORIA);

        assertNull(resultado);
    }

    @Test
    public void buscarDatosEnTablaCategoria_cuandoSeBuscaPorInvitacion() {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarCategoriaONada(NOMBRE_CATEGORIA);

        assertEquals("Categorias", spy.getTableName());
    }

    @Test
    public void lanzarExcepcion_cuandoSeEncuentranVariasCategoriasConElMismoNombre() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(3)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarCategoriaONada(NOMBRE_CATEGORIA));
        assertTrue(exception.getMessage().startsWith("Se encontraron varias categorías con el mismo nombre "));
        assertTrue(exception.getMessage().endsWith(NOMBRE_CATEGORIA));
    }

    @DataPoints("count invalidos")
    public static int[] countInvalidos() {
        return new int[]{0, 2};
    }

    @Theory
    public void lanzarExcepcion_cuandoNoSeEncuentraUnoSoloPorId(@FromDataPoints("count invalidos") int countInvalidos) {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(countInvalidos)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarCategoriaOExplotar(ID_CATEGORIA));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar una única categoría con id"));
        assertTrue(exception.getMessage().contains(String.valueOf(ID_CATEGORIA)));
        assertTrue(exception.getMessage().endsWith(String.valueOf(countInvalidos)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorNombre() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCategoriaONada(NOMBRE_CATEGORIA);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnGrupoPorInvitacion() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCategoriaONada(NOMBRE_CATEGORIA);
            return true;
        }, BuscarCategoriaDebe::crearCursorStub);
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranMuchosPorNombre() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarCategoriaONada(NOMBRE_CATEGORIA);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCategoriaOExplotar(ID_CATEGORIA);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnaCategoriaPorId() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCategoriaOExplotar(ID_CATEGORIA);
            return true;
        }, BuscarCategoriaDebe::crearCursorStub);
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranVariasCategoriasPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarCategoriaOExplotar(ID_CATEGORIA);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoNoSeEncuentraPorInvitacion() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCategoriaONada(NOMBRE_CATEGORIA);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnaCategoriaPorNombre() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCategoriaONada(NOMBRE_CATEGORIA);
            return true;
        }, BuscarCategoriaDebe::crearCursorStub);
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentranMuchosPorInvitacion() {
        probarCerrarCursorAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarCategoriaONada(NOMBRE_CATEGORIA);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoNoSeEncuentraPorId() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCategoriaOExplotar(ID_CATEGORIA);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnaCategoriaPorId() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCategoriaOExplotar(ID_CATEGORIA);
            return true;
        }, BuscarCategoriaDebe::crearCursorStub);
    }
}