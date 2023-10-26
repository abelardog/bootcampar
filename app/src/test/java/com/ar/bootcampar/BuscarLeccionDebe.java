package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.TestChecker.probarCerrarBaseDeDatosAlBuscarConExito;
import static com.ar.bootcampar.support.TestChecker.probarCerrarBaseDeDatosAlBuscarSinExito;
import static com.ar.bootcampar.support.TestChecker.probarCerrarCursorAlBuscarConExito;
import static com.ar.bootcampar.support.TestChecker.probarCerrarCursorAlBuscarSinExito;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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

@RunWith(Theories.class)
public class BuscarLeccionDebe {
    @Test
    public void recibirElIdABuscarCorrectamente() {
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarLeccionOExplotar(ID_LECCION);

        assertEquals(String.valueOf(ID_LECCION), spy.getSelectionArgs()[0]);
    }

    private static CursorWrapperStub crearCursorStub() {
        return new CursorWrapperStub.Builder()
                .conColumnas(Arrays.asList("Lecciones_Id", "Lecciones_Titulo", "Lecciones_Contenido", "Lecciones_Duracion", "Lecciones_Orden", "Lecciones_Vinculo",
                        "Cursos_Id", "Cursos_Titulo", "Cursos_Descripcion", "Cursos_Imagen", "Cursos_Nivel"))
                .conValores(Arrays.asList(ID_LECCION, TITULO_LECCION, CONTENIDO_LECCION, DURACION_LECCION, ORDEN_LECCION, VINCULO_LECCION,
                    ID_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO))
                .conCountRetornando(1)
                .build();
    }

    @Test
    public void buscarDatosEnTablaLeccionYcursos_cuandoSeBuscaPorId() {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarLeccionOExplotar(ID_LECCION);

        assertTrue(spy.getTableName().contains("Lecciones"));
        assertTrue(spy.getTableName().contains("Cursos"));
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
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarLeccionOExplotar(ID_LECCION));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar una única lección con id"));
        assertTrue(exception.getMessage().contains(String.valueOf(ID_LECCION)));
        assertTrue(exception.getMessage().endsWith(String.valueOf(countInvalidos)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarLeccionOExplotar(ID_LECCION);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnLeccionPorId() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarLeccionOExplotar(ID_LECCION);
            return true;
        }, BuscarLeccionDebe::crearCursorStub);
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranVariosLeccionesPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarLeccionOExplotar(ID_LECCION);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoNoSeEncuentraPorId() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarLeccionOExplotar(ID_LECCION);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnLeccionPorId() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarLeccionOExplotar(ID_LECCION);
            return true;
        }, BuscarLeccionDebe::crearCursorStub);
    }
}