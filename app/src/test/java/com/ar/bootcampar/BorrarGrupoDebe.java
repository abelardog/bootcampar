package com.ar.bootcampar;

import static com.ar.bootcampar.support.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;
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
public class BorrarGrupoDebe {
    @Test
    public void recibirElIdParaBorrar() {
        Grupo grupo = crearGrupoDePrueba();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conDeleteRetornando(1)
                .build();

        Database sut = new TestableDatabase(spy);
        sut.borrarGrupo(grupo);

        assertEquals(String.valueOf(ID), spy.getWhereArgs()[0]);
    }

    private static Grupo crearGrupoDePrueba() {
        return new Grupo(ID, NOMBRE_GRUPO, INVITACION_GRUPO);
    }
}