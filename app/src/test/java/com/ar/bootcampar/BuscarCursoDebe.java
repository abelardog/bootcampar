package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Curso;
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
public class BuscarCursoDebe {
    @Test
    public void recibirElIdABuscarCorrectamente() {
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarCursoOExplotar(ID_CURSO);

        assertEquals(String.valueOf(ID_CURSO), spy.getSelectionArgs()[0]);
    }

    private static CursorWrapperStub crearCursorStub() {
        return new CursorWrapperStub.Builder()
                .conColumnas(Arrays.asList("Id", "Titulo", "Descripcion", "Imagen", "Nivel"))
                .conValores(Arrays.asList(ID_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO))
                .conCountRetornando(1)
                .build();
    }

    @Test
    public void buscarDatosEnTablaCurso_cuandoSeBuscaPorId() {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarCursoOExplotar(ID_CURSO);

        assertEquals("Cursos", spy.getTableName());
    }

    @Test
    public void retornarNull_cuandoNoSeEncuentraNombre() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(0)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Curso resultado = sut.buscarCursoONada(TITULO_CURSO);

        assertNull(resultado);
    }

    @Test
    public void buscarDatosEnTablaCurso_cuandoSeBuscaPorTitulo() {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarCursoONada(TITULO_CURSO);

        assertEquals("Cursos", spy.getTableName());
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
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarCursoONada(TITULO_CURSO));
        assertTrue(exception.getMessage().startsWith("Se encontraron varios cursos con el mismo título "));
        assertTrue(exception.getMessage().endsWith(TITULO_CURSO));
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
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarCursoOExplotar(ID_CURSO));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar un único curso con id"));
        assertTrue(exception.getMessage().contains(String.valueOf(ID_CURSO)));
        assertTrue(exception.getMessage().endsWith(String.valueOf(countInvalidos)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorInvitacion() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCursoONada(TITULO_CURSO);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnCursoPorInvitacion() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCursoONada(TITULO_CURSO);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranMuchosPorInvitacion() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarCursoONada(TITULO_CURSO);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCursoOExplotar(ID_CURSO);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnCursoPorId() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCursoOExplotar(ID_CURSO);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranVariosCursosPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarCursoOExplotar(ID_CURSO);
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

    private static void probarCerrarBaseDeDatosAlBuscarConExito(Function<IDatabase, Boolean> assertion) {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        assertion.apply(sut);

        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarCursor_cuandoNoSeEncuentraPorTitulo() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCursoONada(TITULO_CURSO);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnCursoPorTitulo() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCursoONada(TITULO_CURSO);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentranMuchosPorTitulo() {
        probarCerrarCursorAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarCursoONada(TITULO_CURSO);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoNoSeEncuentraPorId() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCursoOExplotar(ID_CURSO);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnCursoPorId() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCursoOExplotar(ID_CURSO);
            return true;
        });
    }

    private static void probarCerrarCursorAlBuscarSinExito(int count, Function<IDatabase, Boolean> assertion) {
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

        assertTrue(cursorStub.getCloseCalled());
    }

    private static void probarCerrarCursorAlBuscarConExito(Function<IDatabase, Boolean> assertion) {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        assertion.apply(sut);

        assertTrue(cursorStub.getCloseCalled());
    }
}