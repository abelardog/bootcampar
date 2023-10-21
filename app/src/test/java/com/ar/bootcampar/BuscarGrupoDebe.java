package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.TestChecker.probarCerrarBaseDeDatosAlBuscarConExito;
import static com.ar.bootcampar.support.TestChecker.probarCerrarCursorAlBuscarConExito;
import static com.ar.bootcampar.support.TestChecker.probarCerrarCursorAlBuscarSinExito;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
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
public class BuscarGrupoDebe {
    @Test
    public void recibirElIdABuscarCorrectamente() {
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarGrupoOExplotar(ID);

        assertEquals(String.valueOf(ID), spy.getSelectionArgs()[0]);
    }

    private static CursorWrapperStub crearCursorStub() {
        return new CursorWrapperStub.Builder()
                .conColumnas(Arrays.asList("Id", "Nombre", "Invitacion"))
                .conValores(Arrays.asList(ID, NOMBRE_GRUPO, INVITACION_GRUPO))
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
        sut.buscarGrupoOExplotar(ID);

        assertEquals("Grupos", spy.getTableName());
    }

    @Test
    public void retornarNull_cuandoNoSeEncuentraInvitacion() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(0)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Grupo resultado = sut.buscarGrupoONada(INVITACION_GRUPO);

        assertNull(resultado);
    }

    @Test
    public void buscarDatosEnTablaGrupo_cuandoSeBuscaPorInvitacion() {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarGrupoONada(INVITACION_GRUPO);

        assertEquals("Grupos", spy.getTableName());
    }

    @Test
    public void lanzarExcepcion_cuandoSeEncuentranVariosGruposConLaMismaInvitacion() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(3)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarGrupoONada(INVITACION_GRUPO));
        assertTrue(exception.getMessage().startsWith("Se encontraron varios grupos con la misma invitación "));
        assertTrue(exception.getMessage().endsWith(INVITACION_GRUPO));
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
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarGrupoOExplotar(ID));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar un grupo con id"));
        assertTrue(exception.getMessage().contains(String.valueOf(ID)));
        assertTrue(exception.getMessage().endsWith(String.valueOf(countInvalidos)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorInvitacion() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarGrupoONada(INVITACION_GRUPO);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnGrupoPorInvitacion() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarGrupoONada(INVITACION_GRUPO);
            return true;
        }, BuscarGrupoDebe::crearCursorStub);
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranMuchosPorInvitacion() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarGrupoONada(INVITACION_GRUPO);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarGrupoOExplotar(ID);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnGrupoPorId() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarGrupoOExplotar(ID);
            return true;
        }, BuscarGrupoDebe::crearCursorStub);
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranVariosGruposPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarGrupoOExplotar(ID);
            return true;
        });
    }

    private static void probarCerrarBaseDeDatosAlBuscarSinExito(int count, Function<IDatabase, Boolean> assertion) {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(count)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        try {
            assertion.apply(sut);
        } catch (Exception ex) {
        }

        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarCursor_cuandoNoSeEncuentraPorInvitacion() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarGrupoONada(INVITACION_GRUPO);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnGrupoPorInvitacion() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarGrupoONada(INVITACION_GRUPO);
            return true;
        }, BuscarGrupoDebe::crearCursorStub);
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentranMuchosPorInvitacion() {
        probarCerrarCursorAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarGrupoONada(INVITACION_GRUPO);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoNoSeEncuentraPorId() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarGrupoOExplotar(ID);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnGrupoPorId() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarGrupoOExplotar(ID);
            return true;
        }, BuscarGrupoDebe::crearCursorStub);
    }
}