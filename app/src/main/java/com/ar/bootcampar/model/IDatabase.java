package com.ar.bootcampar.model;

import java.util.List;

public interface IDatabase {
    Usuario crearUsuario(String nombre, String apellido, String email, String clave, Rol rol, String telefono);
    Usuario buscarUsuarioOExplotar(long id);
    Usuario buscarUsuarioONada(String email);
    Usuario modificarUsuario(Usuario usuario, String nuevoNombre, String nuevoApellido, String nuevoEmail, String nuevaClave, Rol nuevoRol, String nuevoTelefono);
    void borrarUsuario(Usuario usuario);

    Division crearDivision(Usuario usuario, Grupo grupo);
    void borrarDivision(Division division);
    Division modificarDivision(Division division, Usuario nuevoUsuario, Grupo nuevoGrupo);

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
    List<Categoria> listarCategorias();

    Inscripcion crearInscripcion(Usuario usuario, Curso curso, int puntuacion, boolean favorito, int ultimaLeccion);
    void borrarInscripcion(Inscripcion inscripcion);
    Inscripcion modificarInscripcion(Inscripcion inscripcion, Usuario nuevoUsuario, Curso nuevoCurso, int nuevaPuntuacion, boolean nuevoFavorito, int nuevaUltimaLeccion);
    List<Inscripcion> buscarInscripciones(Usuario usuario);
    Inscripcion buscarInscripcionOExplotar(long id);

    Curricula crearCurricula(Curso curso, Grupo grupo);
    Curricula modificarCurricula(Curricula curricula, Curso nuevocourse, Grupo nuevogrupo);
    void borrarCurricula(Curricula curricula);

    void borrarCategorizacion(Categorizacion categorizacion);
    Categorizacion crearCategorizacion(Curso curso, Categoria categoria);
    Categorizacion modificarCategorizacion(Categorizacion categorizacion, Curso nuevoCurso, Categoria nuevaCategoria);

    Curso crearCurso(String title, String description, Boolean isFavorite, String imageName);
    Curso modificarCurso(Curso curso, String title, String description, Boolean isFavorite, String imageName);
    void borrarCurso(Curso curso);
}