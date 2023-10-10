package com.ar.bootcampar.model;

import java.io.Serializable;

public class Currículas implements Serializable {

    private final long id;

    private final Course course;

    private final  Grupo grupo;


    public Currículas(long id, Course course, Grupo grupo) {
        this.id = id;
        this.course = course;
        this.grupo = grupo;
    }
    
    public long getId() {return id;}
    public Course getCourse(){return course;}
    public Grupo getGrupo(){return grupo;}
}

