package com.ar.bootcampar.model;

import com.ar.bootcampar.model.utilities.Guardia;

import java.io.Serializable;

public class Grupo implements Serializable {
    private final long id;
    private final String nombre;
    private final String invitacion;

    public Grupo(long id, String nombre, String invitacion) {
        Guardia.esIdentificadorValido(id, "El id es inválido");
        Guardia.esCadenaNoVacia(nombre, "El nombre es inválido");
        Guardia.esCadenaNoVacia(invitacion, "La invitación es inválida");

        this.id = id;
        this.nombre = nombre;
        this.invitacion = invitacion;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getInvitacion() { return invitacion; }
}

