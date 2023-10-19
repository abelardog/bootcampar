package com.ar.bootcampar.model;

import com.ar.bootcampar.model.utilities.Guardia;

import java.io.Serializable;

public class Leccion implements Serializable {
    private final long id;
    private final String titulo;
    private final String contenido;
    private final int duracion;
    private final int orden;
    private final String vinculo;
    private final Curso curso;

    public Leccion(long id, String titulo, String contenido, int duracion, int orden, String vinculo, Curso curso) {
        Guardia.esIdentificadorValido(id, "El id es inválido");
        Guardia.esCadenaNoVacia(titulo, "El título es inválido");
        Guardia.esCadenaNoVacia(contenido, "El contenido es inválido");
        Guardia.esCeroPositivo(duracion, "La duración es inválida");
        Guardia.esObjetoValido(curso, "El curso es inválido");

        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.duracion = duracion;
        this.orden = orden;
        this.vinculo = vinculo;
        this.curso = curso;
    }

    public long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getContenido() { return contenido; }
    public int getDuracion() { return duracion; }
    public int getOrden() { return orden; }
    public String getVinculo() { return vinculo; }
    public Curso getCurso() { return curso; }
}