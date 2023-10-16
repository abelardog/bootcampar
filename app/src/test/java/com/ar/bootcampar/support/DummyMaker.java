package com.ar.bootcampar.support;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.Usuario;

public class DummyMaker {
    public static Usuario crearUsuarioDePrueba() {
        return new Usuario(ID_USUARIO, NOMBRE, APELLIDO, EMAIL, CLAVE, ROL, TELEFONO);
    }

    public static Usuario crearOtroUsuarioDePrueba() {
        return new Usuario(OTRO_ID_USUARIO, OTRO_NOMBRE, OTRO_APELLIDO, OTRO_EMAIL, OTRA_CLAVE, OTRO_ROL, OTRO_TELEFONO);
    }

    public static Curso crearCursoDePrueba() {
        return new Curso(ID_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, ES_FAVORITO_CURSO, IMAGEN_CURSO, NIVEL_CURSO);
    }

    public static Curso crearOtroCursoDePrueba() {
        return new Curso(OTRO_ID_CURSO, OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, OTRO_ES_FAVORITO_CURSO, OTRA_IMAGEN_CURSO, OTRO_NIVEL_CURSO);
        return new Curso(ID_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, NIVEL_CURSO, IMAGEN_CURSO);
    }

    public static Curso crearOtroCursoDePrueba() {
        return new Curso(OTRO_ID_CURSO, OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, NIVEL_CURSO, OTRA_IMAGEN_CURSO);
    }

    public static Grupo crearGrupoDePrueba() {
        return new Grupo(ID, NOMBRE_GRUPO, INVITACION_GRUPO);
    }

    public static Inscripcion crearInscripcionDePrueba() {
        return new Inscripcion(ID_INSCRIPCION, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);
    }

    public static Inscripcion crearOtraInscripcionDePrueba() {
        return new Inscripcion(OTRO_ID_INSCRIPCION, crearOtroUsuarioDePrueba(), crearOtroCursoDePrueba(), OTRA_PUNTUACION_INSCRIPCION, OTRO_FAVORITO_INSCRIPCION, OTRA_ULTIMA_LECCION_INSCRIPCION);
    }
}