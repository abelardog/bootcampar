package com.ar.bootcampar.model;

import com.ar.bootcampar.model.utilities.Guardia;

import java.io.Serializable;

public class Curso implements Serializable {
    private final long id;
    private final String imagen;
    private final String titulo;
    private final String descripcion;
    private final int nivel;

    public Curso(long id, String titulo, String descripcion, String imagen, int nivel) {
        Guardia.esIdentificadorValido(id);
        Guardia.esCadenaNoVacia(titulo, "El título es inválido");
        Guardia.esCadenaNoVacia(descripcion, "La descripción es inválida");
        // TODO: Encontrar como validar link
        Guardia.esCadenaNoVacia(imagen, "El link de imagen es inválida");
        if (nivel <= 0 || nivel > 3) throw new RuntimeException("El nivel es inválido");

        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.nivel = nivel;
    }

    public String getImagen() { return imagen; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public long getId() { return id; }
    public int getNivel() { return nivel; }
}