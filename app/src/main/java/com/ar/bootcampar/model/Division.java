package com.ar.bootcampar.model;

import java.io.Serializable;

public class Division implements Serializable {
    private long id;
    private Usuario usuario;
    private Grupo grupo;

    public Division(long id, Usuario usuario, Grupo grupo) {
        this.id = id;
        this.usuario = usuario;
        this.grupo = grupo;
    }

    public long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Grupo getGrupo() { return grupo; }
}