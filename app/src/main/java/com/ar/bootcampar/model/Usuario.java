package com.ar.bootcampar.model;

public class Usuario {
    private final long id;
    private final String nombre;
    private final String apellido;
    private final String email;
    private final String clave;
    private final Rol rol;
    private final String telefono;

    public Usuario(long id, String nombre, String apellido, String email, String clave, Rol rol, String telefono) {
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