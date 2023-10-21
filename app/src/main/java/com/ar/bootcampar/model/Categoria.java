package com.ar.bootcampar.model;

import java.io.Serializable;

public class Categoria implements Serializable {
    private final long id;
    private final String nombre;
    private final String descripcion;

    public Categoria(long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}