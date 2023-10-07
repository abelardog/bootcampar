package com.ar.bootcampar.model;

import java.io.Serializable;

public class Leccion implements Serializable {
    private long id;
    private String titulo;
    private String contenido;
    private int duracion;
    private int orden;
    private Course curso;

    public Leccion(long id, String titulo, String contenido, int duracion, int orden, Course curso) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.duracion = duracion;
        this.orden = orden;
        this.curso = curso;
    }

    public long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getContenido() { return contenido; }
    public int getDuracion() { return duracion; }
    public int getOrden() { return orden; }
    public Course getCurso() { return curso; }
}