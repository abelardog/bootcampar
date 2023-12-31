package com.ar.bootcampar.model;

import com.ar.bootcampar.model.utilities.Guardia;

import java.io.Serializable;

public class Curricula implements Serializable {
    private final long id;
    private final Curso curso;
    private final Grupo grupo;

    public Curricula(long id, Curso curso, Grupo grupo) {
        Guardia.esIdentificadorValido(id);
        Guardia.esObjetoValido(curso, "El curso es inválido");
        Guardia.esObjetoValido(grupo, "El grupo es inválido");

        this.id = id;
        this.curso = curso;
        this.grupo = grupo;
    }

    public long getId() { return id; }
    public Curso getCurso() { return curso; }
    public Grupo getGrupo() { return grupo; }
}