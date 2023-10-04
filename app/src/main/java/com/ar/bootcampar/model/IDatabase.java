package com.ar.bootcampar.model;

public interface IDatabase {
    Usuario crearUsuario(String nombre, String apellido, String email, String clave, Rol rol, String telefono);
    Usuario buscarUsuario(long id);
    Usuario modificarUsuario(Usuario usuario, String nuevoNombre, String nuevoApellido, String nuevoEmail, String nuevaClave, Rol nuevoRol, String nuevoTelefono);
    void borrarUsuario(Usuario usuario);
}