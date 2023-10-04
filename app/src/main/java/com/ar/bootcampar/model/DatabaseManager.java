package com.ar.bootcampar.model;

import android.content.Context;

public class DatabaseManager {
    private IDatabase database;

    public DatabaseManager(Context applicationContext) {
        database = Database.CreateWith(applicationContext);
    }

    public Usuario registrarUsuario(String nombre, String apellido, String email, String clave, Rol rol, String telefono, String invitacion) {
        Grupo grupo = database.buscarGrupoONada(invitacion);
        if (grupo == null) {
            throw new RuntimeException(String.format("La invitacion %s es inválida.", invitacion));
        }

        Usuario usuario = database.buscarUsuarioONada(email);
        if (usuario != null) {
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el e-mail %s", email));
        }

        usuario = database.crearUsuario(nombre, apellido, email, clave, rol, telefono);
        Division division = database.crearDivision(usuario, grupo);

        return usuario;
    }
}