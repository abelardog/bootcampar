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
    Division buscarDivisionOExplotar(long id);
    Division buscarDivisionONada(Usuario usuario, Grupo grupo);

    Grupo crearGrupo(String nombre, String invitacion);
    Grupo buscarGrupoONada(String invitacion);
    Grupo buscarGrupoOExplotar(long id);
    List<Grupo> listarGrupos();
    void borrarGrupo(Grupo grupo);
    Grupo modificarGrupo(Grupo grupo, String nuevoNombre, String nuevaInvitacion);

    Leccion crearLeccion(String titulo, String contenido, int duracion, int orden, String vinculo, Curso curso);
    Leccion modificarLeccion(Leccion leccion, String nuevoTitulo, String nuevoContenido, int nuevaDuracion, int nuevoOrden, String nuevoVinculo, Curso nuevoCurso);
    List<Leccion> buscarLecciones(Curso curso);
    Leccion buscarLeccionOExplotar(long id);
    List<Leccion> listarLecciones();
    void borrarLeccion(Leccion leccion);

    Categoria crearCategoria(String nombre, String descripcion);
    void borrarCategoria(Categoria categoria);
    Categoria buscarCategoriaOExplotar(long id);
    Categoria buscarCategoriaONada(String nombre);
    List<Categoria> listarCategorias();
    Categoria modificarCategoria(Categoria categoria, String nuevoNombre, String nuevaDescripcion);

    Inscripcion crearInscripcion(Usuario usuario, Curso curso, int puntuacion, boolean favorito, int ultimaLeccion);
    void borrarInscripcion(Inscripcion inscripcion);
    Inscripcion modificarInscripcion(Inscripcion inscripcion, Usuario nuevoUsuario, Curso nuevoCurso, int nuevaPuntuacion, boolean nuevoFavorito, int nuevaUltimaLeccion);
    List<Inscripcion> buscarInscripciones(Usuario usuario);
    List<Inscripcion> buscarInscripciones(Curso curso);
    Inscripcion buscarInscripcionOExplotar(long id);
    Inscripcion buscarInscripcionONada(Usuario usuario, Curso curso);
    List<Inscripcion> buscarInscripcionesFavoritas(Usuario usuario);

    Curricula crearCurricula(Curso curso, Grupo grupo);
    Curricula modificarCurricula(Curricula curricula, Curso nuevocourse, Grupo nuevogrupo);
    void borrarCurricula(Curricula curricula);
    List<Curricula> listarCurriculas();
    Curricula buscarCurriculaONada(Curso curso, Grupo grupo);
    Curricula buscarCurriculaOExplotar(long id);

    void borrarCategorizacion(Categorizacion categorizacion);
    Categorizacion crearCategorizacion(Curso curso, Categoria categoria);
    Categorizacion buscarCategorizacionONada(Curso curso, Categoria categoria);
    Categorizacion modificarCategorizacion(Categorizacion categorizacion, Curso nuevoCurso, Categoria nuevaCategoria);
    List<Categorizacion> buscarCategorizaciones(Curso curso);

    Curso crearCurso(String title, String description, String imageName, int nivel);
    Curso modificarCurso(Curso curso, String title, String description, String imageName, int nivel);
    void borrarCurso(Curso curso);
    List<Curso> listarCursos();
    Curso buscarCursoONada(String titulo);
    Curso buscarCursoOExplotar(long idCurso);
    List<Curso> buscarCursos(Usuario usuario);
}