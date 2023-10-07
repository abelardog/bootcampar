package com.ar.bootcampar.model.utilities;

public class Guardia {
    public static boolean esNombreValido(String cadena) {
        return !(cadena == null || cadena.trim().isEmpty());
    }

    public static boolean esIdentificadorValido(long id) {
        return id > 0;
    }
}