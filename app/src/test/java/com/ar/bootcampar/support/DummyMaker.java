package com.ar.bootcampar.support;

import static com.ar.bootcampar.support.Constants.*;

import com.ar.bootcampar.model.Categoria;
import com.ar.bootcampar.model.Curricula;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Division;
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
        return new Curso(ID_CURSO, TITULO_CURSO, DESCRIPCION_CURSO, IMAGEN_CURSO, NIVEL_CURSO);
    }

    public static Categoria crearCategoriaDePrueba() {
        return new Categoria(ID_CATEGORIA, NOMBRE_CATEGORIA, DESCRIPCION_CATEGORIA);
    }

    public static Curso crearOtroCursoDePrueba() {
        return new Curso(OTRO_ID_CURSO, OTRO_TITULO_CURSO, OTRA_DESCRIPCION_CURSO, OTRA_IMAGEN_CURSO, OTRO_NIVEL_CURSO);
    }

    public static Grupo crearGrupoDePrueba() {
        return new Grupo(ID_GRUPO, NOMBRE_GRUPO, INVITACION_GRUPO);
    }

    public static Grupo crearOtroGrupoDePrueba() {
        return new Grupo(OTRO_ID_GRUPO, OTRO_NOMBRE_GRUPO, OTRA_INVITACION_GRUPO);
    }

    public static Inscripcion crearInscripcionDePrueba() {
        return new Inscripcion(ID_INSCRIPCION, crearUsuarioDePrueba(), crearCursoDePrueba(), PUNTUACION_INSCRIPCION, FAVORITO_INSCRIPCION, ULTIMA_LECCION_INSCRIPCION);
    }

    public static Inscripcion crearOtraInscripcionDePrueba() {
        return new Inscripcion(OTRO_ID_INSCRIPCION, crearOtroUsuarioDePrueba(), crearOtroCursoDePrueba(), OTRA_PUNTUACION_INSCRIPCION, OTRO_FAVORITO_INSCRIPCION, OTRA_ULTIMA_LECCION_INSCRIPCION);
    }

    public static Curricula crearCurriculaDePrueba() {
        Curso curso = crearCursoDePrueba();
        Grupo grupo = crearGrupoDePrueba();
        return new Curricula(ID_CURRICULA, curso, grupo);
    }

    public static Division crearDivisionDePrueba() {
        return new Division(ID_DIVISION, crearUsuarioDePrueba(), crearGrupoDePrueba());
    }

    public static Division crearOtraDivisionDePrueba() {
        return new Division(OTRO_ID_DIVISION, crearOtroUsuarioDePrueba(), crearOtroGrupoDePrueba());
    }
}