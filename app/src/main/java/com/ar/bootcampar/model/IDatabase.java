package com.ar.bootcampar.model;

public interface IDatabase {
    Usuario crearUsuario(String nombre, String apellido, String email, String clave, Rol rol, String telefono);
    Usuario buscarUsuarioOExplotar(long id);
    Usuario buscarUsuarioONada(String email);
    Usuario modificarUsuario(Usuario usuario, String nuevoNombre, String nuevoApellido, String nuevoEmail, String nuevaClave, Rol nuevoRol, String nuevoTelefono);
    void borrarUsuario(Usuario usuario);
    Division crearDivision(Usuario usuario, Grupo grupo);
    Grupo buscarGrupoONada(String invitacion);
    Categoria crearCategoria(String nombre, String descripcion);
}