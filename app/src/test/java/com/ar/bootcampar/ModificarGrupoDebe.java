package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearGrupoDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
import com.ar.bootcampar.support.SqliteDatabaseWrapperSpy;
import com.ar.bootcampar.support.TestableDatabase;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ModificarGrupoDebe {
    @Test
    public void lanzarExcepcion_cuandoSeQuiereModificarObjetoNulo() {
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarGrupo(null, NOMBRE_GRUPO, INVITACION_GRUPO));
        assertEquals("El grupo es nulo", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnNombreInvalido() {
        Grupo grupo = crearGrupoDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarGrupo(grupo, "", INVITACION_GRUPO));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Test
    public void lanzarExcepcion_cuandoSeQuiereUsarUnaInvitacionInvalida() {
        Grupo grupo = crearGrupoDePrueba();
        Database sut = new TestableDatabase(new SqliteDatabaseWrapperSpy.Builder().build());
        Exception exception = assertThrows(RuntimeException.class, () -> sut.modificarGrupo(grupo, NOMBRE, ""));
        assertEquals("La invitación es inválida", exception.getMessage());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoElNombreEsInvalido() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarGrupo(grupo, NOMBRE_GRUPO_INVALIDO, INVITACION_GRUPO));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void noSetearTransaccionComoExitosa_cuandoLaInvitacionEsInvalida() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conInsertRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> sut.modificarGrupo(grupo, NOMBRE_GRUPO, INVITACION_GRUPO_INVALIDA));
        assertFalse(spy.getTransactionSuccessfulCalled());
    }

    @Test
    public void recibirTodosLosDatosDelGrupo() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarGrupo(grupo, OTRO_NOMBRE_GRUPO, OTRA_INVITACION_GRUPO);

        assertEquals(OTRO_NOMBRE_GRUPO, spy.getInsertedValues().get("Nombre"));
        assertEquals(OTRA_INVITACION_GRUPO, spy.getInsertedValues().get("Invitacion"));
    }

    @Test
    public void insertarDatosEnTablaGrupo() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database sut = new TestableDatabase(spy);
        sut.modificarGrupo(grupo, OTRO_NOMBRE_GRUPO, OTRA_INVITACION_GRUPO);

        assertEquals("Grupos", spy.getTableName());
    }

    @Test
    public void retornarGrupoModificado_cuandoSeModificaGrupoEnBaseDeDatos() {
        Grupo grupo = crearGrupoDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();
        Database database = new TestableDatabase(spy);
        Grupo sut = database.modificarGrupo(grupo, OTRO_NOMBRE_GRUPO, OTRA_INVITACION_GRUPO);

        assertNotNull(sut);
        assertEquals(ID, sut.getId());
        assertEquals(OTRO_NOMBRE_GRUPO, sut.getNombre());
        assertEquals(OTRA_INVITACION_GRUPO, sut.getInvitacion());
    }

    @DataPoints("affected rows invalidos")
    public static int[] affectedRowsInvalidos() {
        return new int[]{0, -1, 2};
    }

    @Theory
    public void lanzarExcepcion_cuandoUpdateRetornaError(@FromDataPoints("affected rows invalidos") int affectedRowsInvalido) {
        Grupo grupo = crearGrupoDePrueba();
        ISQLiteDatabaseWrapper spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(affectedRowsInvalido)
                .build();
        Database database = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> database.modificarGrupo(grupo, NOMBRE_GRUPO, INVITACION_GRUPO));
        assertTrue(exception.getMessage().startsWith("Se esperaba modificar un único grupo pero se modificaron "));
        assertTrue(exception.getMessage().contains(String.valueOf(affectedRowsInvalido)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaError() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(0)
                .build();

        Database database = new TestableDatabase(spy);
        assertThrows(RuntimeException.class, () -> database.modificarGrupo(grupo, NOMBRE_GRUPO, INVITACION_GRUPO));
        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarBaseDeDatos_cuandoUpdateRetornaExito() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conUpdateRetornando(1)
                .build();

        Database database = new TestableDatabase(spy);
        database.modificarGrupo(grupo, NOMBRE_GRUPO, INVITACION_GRUPO);
        assertTrue(spy.getCloseCalled());
    }
}