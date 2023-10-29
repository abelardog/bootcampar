package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearGrupoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;
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
import com.ar.bootcampar.model.Inscripcion;
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
public class BuscarInscripcionDebe {
    @Test
    public void recibirElIdABuscarCorrectamente() {
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarInscripcionOExplotar(ID_INSCRIPCION);

        assertEquals(String.valueOf(ID_INSCRIPCION), spy.getSelectionArgs()[0]);
    }

    private static CursorWrapperStub crearCursorStub() {
        return new CursorWrapperStub.Builder()
                .conColumnas(Arrays.asList("Inscripciones_Id", "Inscripciones_CursoId", "Cursos_Id", "Cursos_Titulo", "Cursos_Descripcion", "Cursos_Nivel", "Cursos_Imagen", "Inscripciones_UsuarioId", "Usuarios_Id", "Usuarios_Nombre", "Usuarios_Apellido", "Usuarios_Email", "Usuarios_Clave", "Usuarios_Rol", "Usuarios_Telefono", "Inscripciones_Puntuacion", "Inscripciones_Favorito", "Inscripciones_UltimaLeccion"))
                .conValores(Arrays.asList(ID_INSCRIPCION, ID_CURSO, ID_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, NIVEL_CURSO, IMAGEN_CURSO, ID_USUARIO, ID_USUARIO, NOMBRE, APELLIDO, EMAIL, CLAVE, Rol.asInt(ROL), TELEFONO, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION? 1 : 0, ULTIMA_LECCION_INSCRIPCION))
                .conCountRetornando(1)
                .build();
    }

    @Test
    public void buscarDatosEnTablaInscripciones_cuandoSeBuscaPorId() {
        CursorWrapperStub cursorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarInscripcionOExplotar(ID_INSCRIPCION);

        assertTrue(spy.getTableName().contains("Inscripciones"));
        assertTrue(spy.getTableName().contains("Cursos"));
        assertTrue(spy.getTableName().contains("Usuarios"));
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
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarInscripcionOExplotar(ID));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar una única inscripción con id"));
        assertTrue(exception.getMessage().contains(String.valueOf(ID)));
        assertTrue(exception.getMessage().endsWith(String.valueOf(countInvalidos)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarInscripcionOExplotar(ID_INSCRIPCION);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnaInscripcionPorId() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarInscripcionOExplotar(ID_INSCRIPCION);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranVariasInscripcionesPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarInscripcionOExplotar(ID_INSCRIPCION);
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
            sut.buscarInscripcionOExplotar(ID_INSCRIPCION);
            return true;
        });
    }

    @Test
    public void cerrarCursor_cuandoSeEncuentraUnaInscripcionPorId() {
        probarCerrarCursorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarInscripcionOExplotar(ID_INSCRIPCION);
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
    public void recibirElUsuarioYCursoCorrectamente() {
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarInscripcionONada(crearUsuarioDePrueba(), crearCursoDePrueba());

        assertEquals(String.valueOf(ID_CURSO), spy.getSelectionArgs()[0]);
        assertEquals(String.valueOf(ID_USUARIO), spy.getSelectionArgs()[1]);
    }

    @Test
    public void retornarNull_cuandoLaInscripcionNoSeEncuentra() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(0)
                .build();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Inscripcion resultado = sut.buscarInscripcionONada(crearUsuarioDePrueba(), crearCursoDePrueba());

        assertNull(resultado);
    }

    @Test
    public void retornarInscripcion_cuandoSeEncuentraPorUsuarioYCurso() {
        Usuario usuario = crearUsuarioDePrueba();
        Curso curso = crearCursoDePrueba();
        CursorWrapperStub cursorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Inscripcion inscripcion = sut.buscarInscripcionONada(usuario, curso);

        assertNotNull(inscripcion);
        assertEquals(ID_INSCRIPCION, inscripcion.getId());
        assertEquals(usuario.getId(), inscripcion.getUsuario().getId());
        assertEquals(curso.getId(), inscripcion.getCurso().getId());
    }

    @Test
    public void lanzarExcepcion_cuandoSeEncuentranMasDeUnaInscripcionPorUsuarioYCurso() {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(2)
                .build();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarInscripcionONada(crearUsuarioDePrueba(), crearCursoDePrueba()));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar una única inscripción pero se encontraron "));
        assertTrue(exception.getMessage().endsWith(String.valueOf(2)));
    }
}