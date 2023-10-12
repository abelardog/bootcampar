package com.ar.bootcampar.model;

import java.io.Serializable;

public class Categorizaciones implements Serializable {
    private final long id;
    private final Course course;

    private final Categoria categoria;


    public Categorizaciones (long id, Course course, Categoria categoria) {
        this.id = id;
        this.course = course;
        this.categoria = categoria;
    }
    public long getId(){return  id;}

    public Categoria getCategoria() {
        return categoria;

    }

    public Course getCourse() {
        return course;
    }


















}

