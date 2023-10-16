package com.ar.bootcampar.model;

import java.io.Serializable;

public class Categorizacion implements Serializable {
    private final long id;
    private final Curso curso;
    private final Categoria categoria;

    public Categorizacion(long id, Curso curso, Categoria categoria) {
        this.id = id;
        this.curso = curso;
        this.categoria = categoria;
    }

    public long getId(){ return  id; }

    public Categoria getCategoria() {
        return categoria;
    }

    public Curso getCurso() {
        return curso;
    }
}