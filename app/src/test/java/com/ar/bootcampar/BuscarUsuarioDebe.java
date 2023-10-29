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

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.IDatabase;
import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;
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
public class BuscarUsuarioDebe {
    @Test
    public void recibirElIdABuscarCorrectamente() {
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarUsuarioOExplotar(ID);

        assertEquals(String.valueOf(ID), spy.getSelectionArgs()[0]);
    }

    private static CursorWrapperStub crearCursorStub() {
        return new CursorWrapperStub.Builder()
                .conColumnas(Arrays.asList("Id", "Nombre", "Apellido", "Email", "Clave", "Rol", "Telefono"))
                .conValores(Arrays.asList(ID, NOMBRE, APELLIDO, EMAIL, CLAVE, Rol.asInt(ROL), TELEFONO))
                .conCountRetornando(1)
                .build();
    }

    @Test
    public void buscarDatosEnTablaUsuario_cuandoSeBuscaPorId() {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarUsuarioOExplotar(ID);

        assertEquals("Usuarios", spy.getTableName());
    }

    @Test
    public void retornarNull_cuandoNoSeEncuentraMail() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(0)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Usuario resultado = sut.buscarUsuarioONada(OTRO_EMAIL);

        assertNull(resultado);
    }

    @Test
    public void buscarDatosEnTablaUsuario_cuandoSeBuscaPorEmail() {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarUsuarioONada(EMAIL);

        assertEquals("Usuarios", spy.getTableName());
    }

    @Test
    public void lanzarExcepcion_cuandoSeEncuentranVariosUsuariosConElMismoMail() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(3)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarUsuarioONada(EMAIL));
        assertTrue(exception.getMessage().startsWith("Se encontraron varios usuarios"));
        assertTrue(exception.getMessage().endsWith(EMAIL));
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
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarUsuarioOExplotar(ID));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar un único usuario"));
        assertTrue(exception.getMessage().contains(String.valueOf(ID)));
        assertTrue(exception.getMessage().endsWith(String.valueOf(countInvalidos)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorEmail() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarUsuarioONada(EMAIL);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnUsuarioPorEmail() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarUsuarioONada(EMAIL);
            return true;
        }, BuscarUsuarioDebe::crearCursorStub);
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranMuchosPorEmail() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarUsuarioONada(EMAIL);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarUsuarioOExplotar(ID);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnUsuarioPorId() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarUsuarioOExplotar(ID);
            return true;
        }, BuscarUsuarioDebe::crearCursorStub);
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranVariosUsuariosPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarUsuarioOExplotar(ID);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoNoSeEncuentraPorEmail() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarUsuarioONada(EMAIL);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnUsuarioPorEmail() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarUsuarioONada(EMAIL);
            return true;
        }, BuscarUsuarioDebe::crearCursorStub);
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentranMuchosPorEmail() {
        probarCerrarCursorAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarUsuarioONada(EMAIL);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoNoSeEncuentraPorId() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarUsuarioOExplotar(ID);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnUsuarioPorId() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarUsuarioOExplotar(ID);
            return true;
        }, BuscarUsuarioDebe::crearCursorStub);
    }
}