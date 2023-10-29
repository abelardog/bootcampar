package com.ar.bootcampar.model;

import com.ar.bootcampar.model.utilities.Guardia;

import java.io.Serializable;

public class Inscripcion implements Serializable {
    private final long id;
    private final Usuario usuario;
    private final Curso curso;
    private final int puntuacion;
    private final boolean favorito;
    private final int ultimaLeccion;

    public Inscripcion(long id, Usuario usuario, Curso curso, int puntuacion, boolean favorito, int ultimaLeccion) {
        Guardia.esIdentificadorValido(id);
        Guardia.esObjetoValido(usuario, "El usuario es inválido");
        Guardia.esObjetoValido(curso, "El curso es inválido");
        Guardia.esCeroPositivo(puntuacion, "La puntuación es inválida");
        Guardia.esCeroPositivo(ultimaLeccion, "La última lección es inválida");

        this.id = id;
        this.usuario = usuario;
        this.curso = curso;
        this.puntuacion = puntuacion;
        this.favorito = favorito;
        this.ultimaLeccion = ultimaLeccion;
    }

    public long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Curso getCurso() { return curso; }
    public int getPuntuacion() { return puntuacion; }
    public boolean getFavorito() { return favorito; }
    public int getUltimaLeccion() { return ultimaLeccion; }
}