package com.ar.bootcampar.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private final long id;
    private final String nombre;
    private final String apellido;
    private final String email;
    private final String clave;
    private final Rol rol;
    private final String telefono;

    public Usuario(long id, String nombre, String apellido, String email, String clave, Rol rol, String telefono) {
        if (id <= 0) throw new RuntimeException("El id es inválido");
        if (nombre == null || nombre.trim().isEmpty()) throw new RuntimeException("El nombre es inválido");
        if (apellido == null || apellido.trim().isEmpty()) throw new RuntimeException("El apellido es inválido");
        // TODO: Encontrar como validar email
        if (email == null || email.trim().isEmpty()) throw new RuntimeException("El email es inválido");
        if (clave == null || clave.trim().isEmpty()) throw new RuntimeException("La clave es inválida");
        if (telefono == null) throw new RuntimeException("El teléfono es inválido");

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.rol = rol;
        this.telefono = telefono;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getClave() { return clave; }
    public Rol getRol() { return rol; }
    public String getTelefono() { return telefono; }
}