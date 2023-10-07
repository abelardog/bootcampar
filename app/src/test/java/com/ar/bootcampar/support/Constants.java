package com.ar.bootcampar.support;

import com.ar.bootcampar.model.Rol;

public class Constants {
    // Constantes para Usuario
    public static final long ID = 1;
    public static final String NOMBRE = "Luis";
    public static final String APELLIDO = "Agote";
    public static final String EMAIL = "luis.agote@gmail.com";
    public static final String CLAVE = "123456";
    public static final Rol ROL = Rol.Estudiante;
    public static final String TELEFONO = "1234-5678";
    public static final String OTRO_EMAIL = "juan.perez@gmail.com";
    public static final String NOMBRE_INVALIDO = "";
    public static final String APELLIDO_INVALIDO = "";
    public static final String EMAIL_INVALIDO = "";
    public static final String CLAVE_INVALIDA = "";

    // Constantes para Leccion
    public static final String TITULO_LECCION = "Introducción a JavaScript";
    public static final String CONTENIDO_LECCION = "https://www.youtube.com/video/xyz";
    public static final int DURACION_LECCION = 10;
    public static final int ORDEN_LECCION = 1;

    // Constantes para Curso
    public static final String IMAGEN_CURSO = "https://www.imgur.com/xyz";
    public static final String TITULO_CURSO = "Maestría en JavaScript";
    public static final String DESCRIPCION_CURSO = "Con este curso sabrá programar en JavaScript desde un carrito de compras a un e-commerce!";
    @Deprecated
    public static final boolean ES_FAVORITO_CURSO = true;

    // Constantes para Grupo
    public static final String NOMBRE_GRUPO = "Grupo de Programación en JavaScript";
    public static final String INVITACION_GRUPO = "112233";
    public static final String OTRO_NOMBRE_GRUPO = "Grupo de Programación en JS";
    public static final String OTRA_INVITACION_GRUPO = "123123";
    public static final String NOMBRE_GRUPO_INVALIDO = "";
    public static final String INVITACION_GRUPO_INVALIDA = "";
}