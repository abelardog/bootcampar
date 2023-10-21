package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearGrupoDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Curricula;
import com.ar.bootcampar.model.Curso;
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
public class BuscarCurriculaDebe {
    @Test
    public void recibirElIdABuscarCorrectamente() {
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarCurriculaOExplotar(ID_CURRICULA);

        assertEquals(String.valueOf(ID_CURRICULA), spy.getSelectionArgs()[0]);
    }

    private static CursorWrapperStub crearCursorStub() {
        return new CursorWrapperStub.Builder()
                .conColumnas(Arrays.asList("Curriculas_Id", "Curriculas_CursoId",
                        "Cursos_Id", "Cursos_Titulo", "Cursos_Descripcion", "Cursos_Nivel", "Cursos_Imagen",
                        "Curriculas_GrupoId", "Grupos_Id", "Grupos_Nombre", "Grupos_Invitacion"))
                .conValores(Arrays.asList(ID_CURRICULA, ID_CURSO, ID_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, NIVEL_CURSO, IMAGEN_CURSO,
                        ID_GRUPO, ID_GRUPO, NOMBRE_GRUPO, INVITACION_GRUPO))
                .conCountRetornando(1)
                .build();
    }

    @Test
    public void buscarDatosEnTablaCurricula_cuandoSeBuscaPorId() {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarCurriculaOExplotar(ID_CURRICULA);

        assertTrue(spy.getTableName().contains("Curriculas"));
        assertTrue(spy.getTableName().contains("Cursos"));
        assertTrue(spy.getTableName().contains("Grupos"));
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
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarCurriculaOExplotar(ID_CURRICULA));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar una única currícula con id "));
        assertTrue(exception.getMessage().contains(String.valueOf(ID_CURRICULA)));
        assertTrue(exception.getMessage().endsWith(String.valueOf(countInvalidos)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCurriculaOExplotar(ID_CURRICULA);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnaCurriculaPorId() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCurriculaOExplotar(ID_CURRICULA);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranVariasCurriculasPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarCurriculaOExplotar(ID_CURRICULA);
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
    public void cerrarCursor_cuandoNoSeEncuentraPorId() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCurriculaOExplotar(ID_CURRICULA);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnaCurriculaPorId() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCurriculaOExplotar(ID_CURRICULA);
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
        } catch (Exception ignored) {
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

    @Test
    public void recibirElCursoYGrupoCorrectamente() {
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarCurriculaONada(crearCursoDePrueba(), crearGrupoDePrueba());

        assertEquals(String.valueOf(ID_CURSO), spy.getSelectionArgs()[0]);
        assertEquals(String.valueOf(ID_GRUPO), spy.getSelectionArgs()[1]);
    }

    @Test
    public void retornarNull_cuandoLaCurriculaNoSeEncuentra() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(0)
                .build();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Curricula resultado = sut.buscarCurriculaONada(crearCursoDePrueba(), crearGrupoDePrueba());

        assertNull(resultado);
    }

    @Test
    public void retornarCurricula_cuandoSeEncuentraPorCursoYGrupo() {
        Curso curso = crearCursoDePrueba();
        Grupo grupo = crearGrupoDePrueba();
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Curricula curricula = sut.buscarCurriculaONada(curso, grupo);

        assertNotNull(curricula);
        assertEquals(ID_CURRICULA, curricula.getId());
        assertEquals(curso.getId(), curricula.getCurso().getId());
        assertEquals(grupo.getId(), curricula.getGrupo().getId());
    }

    @Test
    public void lanzarExcepcion_cuandoSeEncuentranMasDeUnaCurriculaPorCursoYGrupo() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(2)
                .build();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarCurriculaONada(crearCursoDePrueba(), crearGrupoDePrueba()));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar una única currícula pero se encontraron "));
        assertTrue(exception.getMessage().endsWith(String.valueOf(2)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorCursoYGrupo() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCurriculaONada(crearCursoDePrueba(), crearGrupoDePrueba());
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnaCurriculaPorCursoYGrupo() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCurriculaONada(crearCursoDePrueba(), crearGrupoDePrueba());
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranVariasCurriculasPorCursoYGrupo() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarCurriculaONada(crearCursoDePrueba(), crearGrupoDePrueba());
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoNoSeEncuentraPorCursoYGrupo() {
        probarCerrarCursorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarCurriculaONada(crearCursoDePrueba(), crearGrupoDePrueba());
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnaCurriculaPorCursoYGrupo() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarCurriculaONada(crearCursoDePrueba(), crearGrupoDePrueba());
            return true;
        });
    }
}