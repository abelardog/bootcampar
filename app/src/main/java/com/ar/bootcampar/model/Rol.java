package com.ar.bootcampar.model;

public enum Rol {
    Estudiante(0),
    Administrador(1);

    private final int id;

    private Rol(int id) { this.id = id; }

    public static int asInt(Rol rol) { return rol.id; }

    public static Rol fromInt(int rol) {
        switch (rol) {
            case 0: return Estudiante;
        }

        return null;
    }
}
