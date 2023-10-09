package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearGrupoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearInscripcionDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtraInscripcionDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtroCursoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearOtroUsuarioDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Course;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ModificarInscripcionDebe {
    @Test
    public void lanzarExcepcion_cuandoSeQuiereModificarObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarInscripcion(null, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("La inscripción es nula", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnUsuarioInvalido() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarInscripcion(inscripcion, USUARIO_INVALIDO, crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("El usuario es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnCursoInvalido() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), CURSO_INVALIDO, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("El curso es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnaPuntuacionInvalida() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION_INVALIDA, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertEquals("La puntuación es inválida", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnaUltimaLeccionInvalida() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION_INVALIDA));
        assertEquals("La última lección es inválida", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElUsuarioEsInvalido() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarInscripcion(inscripcion, USUARIO_INVALIDO, crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElCursoEsInvalido() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder().build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), CURSO_INVALIDO, PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaPuntuacionEsInvalida() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION_INVALIDA, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }


    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaUltimaLeccionEsInvalida() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION_INVALIDA));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaInvitacionEsInvalida() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDeLaInscripcion() {
        Inscripcion inscripcion = crearOtraInscripcionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);

        assertEquals(ID_USUARIO, spy.getInsertedValues().get("UsuarioId"));
        assertEquals(ID_CURSO, spy.getInsertedValues().get("CursoId"));
        assertEquals(PUNTUACION_INSCRIPCION, spy.getInsertedValues().get("Puntuacion"));
        assertEquals(FAVORITO_INSCRIPCION, spy.getInsertedValues().get("Favorito"));
        assertEquals(ULTIMA_LECCION_INSCRIPCION, spy.getInsertedValues().get("UltimaLeccion"));
    }

    @Test
    public void insertarDatosEnTablaInscripcion() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);

        assertEquals("Inscripciones", spy.getTableName());
    }

    @Test
    public void retornarInscripcionModificado_cuandoSeModificaInscripcionEnBaseDeDatos() {
        Usuario usuario = crearOtroUsuarioDePrueba();
        Course curso = crearOtroCursoDePrueba();
        Inscripcion inscripcion = crearInscripcionDePrueba();

        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        Inscripcion sut = database.modificarInscripcion(inscripcion, usuario, curso, OTRA_PUNTUACION_INSCRIPCION, OTRO_FAVORITO_INSCRIPCION, OTRA_ULTIMA_LECCION_INSCRIPCION);

        assertNotNull(sut);
        assertEquals(ID_INSCRIPCION, sut.getId());
        assertEquals(usuario, sut.getUsuario());
        assertEquals(curso, sut.getCurso());
        assertEquals(OTRA_PUNTUACION_INSCRIPCION, sut.getPuntuacion());
        assertEquals(OTRO_FAVORITO_INSCRIPCION, sut.getFavorito());
        assertEquals(OTRA_ULTIMA_LECCION_INSCRIPCION, sut.getUltimaLeccion());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[]{0, -1, 2};
    }

    @Theory
    public void lanzarExcepcion_cuandoUpdateRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(affectedRowsInvalido)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertTrue(exception.getMessage().startsWith("Se esperaba modificar una única inscripción pero se modificaron "));
        assertTrue(exception.getMessage().contains(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaError() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(0)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaExito() {
        Inscripcion inscripcion = crearInscripcionDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.modificarInscripcion(inscripcion, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);
        assertTrue(spy.getCloseCalled());
    }
}