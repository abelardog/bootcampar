package com.ar.bootcampar.support;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Course;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.Usuario;

public class DummyMaker {
    public static Usuario crearUsuarioDePrueba() {
        return new Usuario(ID, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);
    }

    public static Course crearCursoDePrueba() {
        return new Course(ID, TITULO_CURSO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO, IMAGEN_CURSO);
    }

    public static Grupo crearGrupoDePrueba() {
        return new Grupo(ID, NOMBRE_GRUPO, INVITACION_GRUPO);
    }

    public static Inscripcion crearInscripcionDePrueba() {
        return new Inscripcion(ID, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);
    }
}