package com.ar.bootcampar.model;

import java.io.Serializable;

public class Grupo implements Serializable {
    private final long id;
    private final String nombre;
    private final String invitacion;

    public Grupo(long id, String nombre, String invitacion) {
        this.id = id;
        this.nombre = nombre;
        this.invitacion = invitacion;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getInvitacion() { return invitacion; }
}