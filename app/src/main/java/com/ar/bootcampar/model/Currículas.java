package com.ar.bootcampar.model;

import java.io.Serializable;

public class Currículas implements Serializable {

    private final long id;

    private final Curso curso;

    private final  Grupo grupo;


    public Currículas(long id, Curso curso, Grupo grupo) {
        this.id = id;
        this.curso = curso;
        this.grupo = grupo;
    }

    public long getId() {return id;}
    public Curso getCourse(){return curso;}
    public Grupo getGrupo(){return grupo;}
}

