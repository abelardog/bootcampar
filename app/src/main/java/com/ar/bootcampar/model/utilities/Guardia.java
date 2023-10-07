package com.ar.bootcampar.model.utilities;

import com.ar.bootcampar.model.Course;

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

    public static void esObjetoValido(Course curso, String mensajeFallo) {
        if (curso == null) throw new RuntimeException(mensajeFallo);
    }

    public static void esObjetoValido(Object objeto, String mensajeFallo) {
        if (objeto == null) throw new RuntimeException(mensajeFallo);
    }
}