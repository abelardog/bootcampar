package com.ar.bootcampar.model;

import java.util.List;

public interface IDatabase {
    Usuario crearUsuario(String nombre, String apellido, String email, String clave, Rol rol, String telefono);
    Usuario buscarUsuarioOExplotar(long id);
    Usuario buscarUsuarioONada(String email);
    Usuario modificarUsuario(Usuario usuario, String nuevoNombre, String nuevoApellido, String nuevoEmail, String nuevaClave, Rol nuevoRol, String nuevoTelefono);
    void borrarUsuario(Usuario usuario);

    Division crearDivision(Usuario usuario, Grupo grupo);

    Grupo crearGrupo(String nombre, String invitacion);
    Grupo buscarGrupoONada(String invitacion);
    Grupo buscarGrupoOExplotar(long id);

    void borrarGrupo(Grupo grupo);
    Grupo modificarGrupo(Grupo grupo, String nuevoNombre, String nuevaInvitacion);

    Leccion crearLeccion(String titulo, String contenido, int duracion, int orden, Curso curso);
    Leccion modificarLeccion(Leccion leccion, String nuevoTitulo, String nuevoContenido, int nuevaDuracion, int nuevoOrden, Curso nuevoCurso);
    List<Leccion> buscarLecciones(Curso curso);
    void borrarLeccion(Leccion leccion);

    Categoria crearCategoria(String nombre, String descripcion);
    void borrarCategoria(Categoria categoria);
    Categoria modificarCategoria(Categoria categoria, String nuevoNombre, String nuevaDescripcion);

    Inscripcion crearInscripcion(Usuario usuario, Curso curso, int puntuacion, boolean favorito, int ultimaLeccion);
    void borrarInscripcion(Inscripcion inscripcion);
    Inscripcion modificarInscripcion(Inscripcion inscripcion, Usuario nuevoUsuario, Curso nuevoCurso, int nuevaPuntuacion, boolean nuevoFavorito, int nuevaUltimaLeccion);
    List<Inscripcion> buscarInscripciones(Usuario usuario);
    Inscripcion buscarInscripcionOExplotar(long id);


    Currículas crearCurriculas(Curso curso, Grupo grupo);
    Currículas modificarCurriulas(Currículas currículas, Curso nuevocourse, Grupo nuevogrupo);
    void borrarCurriculas(Currículas currículas);


















}