package com.ar.bootcampar.model.utilities;

public class Guardia {
    public static void esCadenaNoVacia(String cadena, String mensajeFallo) {
        if (cadena == null || cadena.trim().isEmpty()) throw new RuntimeException(mensajeFallo);
    }

    public static void esIdentificadorValido(long id, String mensajeFallo) {
        if (id <= 0) throw new RuntimeException(mensajeFallo);
    }

    public static void esCeroPositivo(int duracion, String mensajeFallo) {
        if (duracion < 0) throw new RuntimeException(mensajeFallo);
    }
}