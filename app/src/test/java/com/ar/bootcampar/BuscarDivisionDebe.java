package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearGrupoDePrueba;
import static com.ar.bootcampar.support.DummyMaker.crearUsuarioDePrueba;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Division;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.IDatabase;
import com.ar.bootcampar.model.Rol;
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
public class BuscarDivisionDebe {
    @Test
    public void recibirElIdABuscarCorrectamente() {
        CursorWrapperStub usuariorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarDivisionOExplotar(ID_DIVISION);

        assertEquals(String.valueOf(ID_DIVISION), spy.getSelectionArgs()[0]);
    }

    private static CursorWrapperStub crearCursorStub() {
        return new CursorWrapperStub.Builder()
                .conColumnas(Arrays.asList("Divisiones_Id",
                        "Divisiones_UsuarioId", "Usuarios_Id", "Usuarios_Nombre", "Usuarios_Apellido", "Usuarios_Email", "Usuarios_Clave", "Usuarios_Rol", "Usuarios_Telefono",
                        "Divisiones_GrupoId", "Grupos_Id", "Grupos_Nombre", "Grupos_Invitacion"))
                .conValores(Arrays.asList(ID_DIVISION,
                        ID_USUARIO, ID_USUARIO, NOMBRE, APELLIDO, EMAIL, CLAVE, Rol.asInt(ROL), TELEFONO,
                        ID_GRUPO, ID_GRUPO, NOMBRE_GRUPO, INVITACION_GRUPO))
                .conCountRetornando(1)
                .build();
    }

    @Test
    public void buscarDatosEnTablaDivision_cuandoSeBuscaPorId() {
        CursorWrapperStub usuariorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarDivisionOExplotar(ID_DIVISION);

        assertTrue(spy.getTableName().contains("Divisiones"));
        assertTrue(spy.getTableName().contains("Usuarios"));
        assertTrue(spy.getTableName().contains("Grupos"));
    }

    @DataPoints("count invalidos")
    public static int[] countInvalidos() {
        return new int[]{0, 2};
    }

    @Theory
    public void lanzarExcepcion_cuandoNoSeEncuentraUnoSoloPorId(@FromDataPoints("count invalidos") int countInvalidos) {
        CursorWrapperStub usuariorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(countInvalidos)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarDivisionOExplotar(ID_DIVISION));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar una única división con id "));
        assertTrue(exception.getMessage().contains(String.valueOf(ID_DIVISION)));
        assertTrue(exception.getMessage().endsWith(String.valueOf(countInvalidos)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarDivisionOExplotar(ID_DIVISION);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnaDivisionPorId() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarDivisionOExplotar(ID_DIVISION);
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranVariasDivisionesPorId() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarDivisionOExplotar(ID_DIVISION);
            return true;
        });
    }

    private static void probarCerrarBaseDeDatosAlBuscarSinExito(int count, Function<IDatabase, Boolean> assertion) {
        CursorWrapperStub usuariorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(count)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        try {
            assertion.apply(sut);
        } catch (Exception ex) {
        }

        assertTrue(spy.getCloseCalled());
    }

    private static void probarCerrarBaseDeDatosAlBuscarConExito(Function<IDatabase, Boolean> assertion) {
        CursorWrapperStub usuariorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        assertion.apply(sut);

        assertTrue(spy.getCloseCalled());
    }

    @Test
    public void cerrarUsuarior_cuandoNoSeEncuentraPorId() {
        probarCerrarUsuariorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarDivisionOExplotar(ID_DIVISION);
            return true;
        });
    }

    @Test
    public void cerrarUsuarior_cuandoSeEncuentraUnaDivisionPorId() {
        probarCerrarUsuariorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarDivisionOExplotar(ID_DIVISION);
            return true;
        });
    }

    private static void probarCerrarUsuariorAlBuscarSinExito(int count, Function<IDatabase, Boolean> assertion) {
        CursorWrapperStub usuariorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(count)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        try {
            assertion.apply(sut);
        } catch (Exception ignored) {
        }

        assertTrue(usuariorStub.getCloseCalled());
    }

    private static void probarCerrarUsuariorAlBuscarConExito(Function<IDatabase, Boolean> assertion) {
        CursorWrapperStub usuariorStub = crearCursorStub();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        assertion.apply(sut);

        assertTrue(usuariorStub.getCloseCalled());
    }

    @Test
    public void recibirElUsuarioYGrupoCorrectamente() {
        CursorWrapperStub usuariorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.buscarDivisionONada(crearUsuarioDePrueba(), crearGrupoDePrueba());

        assertEquals(String.valueOf(ID_GRUPO), spy.getSelectionArgs()[0]);
        assertEquals(String.valueOf(ID_USUARIO), spy.getSelectionArgs()[1]);
    }

    @Test
    public void retornarNull_cuandoLaDivisionNoSeEncuentra() {
        CursorWrapperStub usuariorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(0)
                .build();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Division resultado = sut.buscarDivisionONada(crearUsuarioDePrueba(), crearGrupoDePrueba());

        assertNull(resultado);
    }

    @Test
    public void retornarDivision_cuandoSeEncuentraPorUsuarioYGrupo() {
        Usuario usuario = crearUsuarioDePrueba();
        Grupo grupo = crearGrupoDePrueba();
        CursorWrapperStub usuariorStub = crearCursorStub();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Division division = sut.buscarDivisionONada(usuario, grupo);

        assertNotNull(division);
        assertEquals(ID_DIVISION, division.getId());
        assertEquals(usuario.getId(), division.getUsuario().getId());
        assertEquals(grupo.getId(), division.getGrupo().getId());
    }

    @Test
    public void lanzarExcepcion_cuandoSeEncuentranMasDeUnaDivisionPorUsuarioYGrupo() {
        CursorWrapperStub usuariorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(2)
                .build();

        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(usuariorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        Exception exception = assertThrows(RuntimeException.class, () -> sut.buscarDivisionONada(crearUsuarioDePrueba(), crearGrupoDePrueba()));
        assertTrue(exception.getMessage().startsWith("Se esperaba encontrar una única división pero se encontraron "));
        assertTrue(exception.getMessage().endsWith(String.valueOf(2)));
    }

    @Test
    public void cerrarBaseDeDatos_cuandoNoSeEncuentraPorUsuarioYGrupo() {
        probarCerrarBaseDeDatosAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarDivisionONada(crearUsuarioDePrueba(), crearGrupoDePrueba());
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentraUnaDivisionPorUsuarioYGrupo() {
        probarCerrarBaseDeDatosAlBuscarConExito((IDatabase sut) -> {
            sut.buscarDivisionONada(crearUsuarioDePrueba(), crearGrupoDePrueba());
            return true;
        });
    }

    @Test
    public void cerrarBaseDeDatos_cuandoSeEncuentranVariasDivisionesPorUsuarioYGrupo() {
        probarCerrarBaseDeDatosAlBuscarSinExito(2, (IDatabase sut) -> {
            sut.buscarDivisionONada(crearUsuarioDePrueba(), crearGrupoDePrueba());
            return true;
        });
    }

    @Test
    public void cerrarUsuarior_cuandoNoSeEncuentraPorUsuarioYGrupo() {
        probarCerrarUsuariorAlBuscarSinExito(0, (IDatabase sut) -> {
            sut.buscarDivisionONada(crearUsuarioDePrueba(), crearGrupoDePrueba());
            return true;
        });
    }

    @Test
    public void cerrarUsuarior_cuandoSeEncuentraUnaDivisionPorUsuarioYGrupo() {
        probarCerrarUsuariorAlBuscarConExito((IDatabase sut) -> {
            sut.buscarDivisionONada(crearUsuarioDePrueba(), crearGrupoDePrueba());
            return true;
        });
    }
}