package com.ar.bootcampar.support;

import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;

public class Constants {
    // Constantes para Usuario
    public static final long ID = 1;
    public static final long ID_USUARIO = 13;
    public static final String NOMBRE = "Luis";
    public static final String APELLIDO = "Agote";
    public static final String EMAIL = "luis.agote@gmail.com";
    public static final String CLAVE = "123456";
    public static final Rol ROL = Rol.Estudiante;
    public static final String TELEFONO = "1234-5678";
    public static final long OTRO_ID_USUARIO = 2;
    public static final String OTRO_NOMBRE = "Juan";
    public static final String OTRO_APELLIDO = "Perez";
    public static final String OTRO_EMAIL = "juan.perez@gmail.com";
    public static final String OTRA_CLAVE = "abcdefg";
    public static final Rol OTRO_ROL = Rol.Administrador;
    public static final String OTRO_TELEFONO = "9876-5432";
    public static final String NOMBRE_INVALIDO = "";
    public static final String APELLIDO_INVALIDO = "";
    public static final String EMAIL_INVALIDO = "";
    public static final String CLAVE_INVALIDA = "";

    // Constantes para Leccion
    public static final String TITULO_LECCION = "Introducción a JavaScript";
    public static final String CONTENIDO_LECCION = "En esta lección usted va a aprender...";
    public static final int DURACION_LECCION = 10;
    public static final int ORDEN_LECCION = 1;
    public static final String VINCULO_LECCION = "https://www.youtube.com/video/xyz";

    // Constantes para Curso
    public static final long ID_CURSO = 18;
    public static final String IMAGEN_CURSO = "https://www.imgur.com/xyz";
    public static final String TITULO_CURSO = "Maestría en JavaScript";
    public static final String DESCRIPCION_CURSO = "Con este curso sabrá programar en JavaScript desde un carrito de compras a un e-commerce!";
    public static final int NIVEL_CURSO = 1;
    public static final long OTRO_ID_CURSO = 19;
    public static final String OTRA_IMAGEN_CURSO = "https://www.imgur.com/abc";
    public static final String OTRO_TITULO_CURSO = "Maestría en JS";
    public static final String OTRA_DESCRIPCION_CURSO = "Con este curso podrá programar en JS.";
    @Deprecated
    public static final boolean OTRO_ES_FAVORITO_CURSO = false;
    public static final int OTRO_NIVEL_CURSO = 3;
    public static final String TITULO_CURSO_INVALIDO = "";
    public static final String DESCRIPCION_CURSO_INVALIDA = "";
    public static final String IMAGEN_CURSO_INVALIDA = "";
    public static final int NIVEL_CURSO_INVALIDO = -5;
    // Constantes para Grupo
    public static final String NOMBRE_GRUPO = "Grupo de Programación en JavaScript";
    public static final String INVITACION_GRUPO = "112233";
    public static final String OTRO_NOMBRE_GRUPO = "Grupo de Programación en JS";
    public static final String OTRA_INVITACION_GRUPO = "123123";
    public static final String NOMBRE_GRUPO_INVALIDO = "";
    public static final String INVITACION_GRUPO_INVALIDA = "";

    // Constantes para Inscripción
    public static long ID_INSCRIPCION = 55;
    public static final int PUNTUACION_INSCRIPCION = 9;
    public static final boolean FAVORITO_INSCRIPCION = true;
    public static final int ULTIMA_LECCION_INSCRIPCION = 4;
    public static final Usuario USUARIO_INVALIDO = null;
    public static final Curso CURSO_INVALIDO = null;
    public static final int PUNTUACION_INSCRIPCION_INVALIDA = -1;
    public static final int ULTIMA_LECCION_INSCRIPCION_INVALIDA = -1;
    public static final int OTRO_ID_INSCRIPCION = 66;
    public static final int OTRA_PUNTUACION_INSCRIPCION = 7;
    public static final boolean OTRO_FAVORITO_INSCRIPCION = false;
    public static final int OTRA_ULTIMA_LECCION_INSCRIPCION = 5;

    // Constantes para Categoría
    public static long ID_CATEGORIA = 13;
    public static String NOMBRE_CATEGORIA = "Principiantes";
    public static String DESCRIPCION_CATEGORIA = "Categoría para principiantes";
}