package com.ar.bootcampar.model.utilities;

import com.ar.bootcampar.model.Course;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.Usuario;

public class Guardia {
    public static void esCadenaNoVacia(String cadena, String mensajeFallo) {
        if (cadena == null || cadena.trim().isEmpty()) throw new RuntimeException(mensajeFallo);
    }

    // TODO: El mensaje es siempre el mismo, refactorear para que el mensaje esté acá adentro y no como argumento
    public static void esIdentificadorValido(long id, String mensajeFallo) {
        if (id <= 0) throw new RuntimeException(mensajeFallo);
    }

    public static void esCeroPositivo(int duracion, String mensajeFallo) {
        if (duracion < 0) throw new RuntimeException(mensajeFallo);
    }

    public static void esObjetoValido(Object objeto, String mensajeFallo) {
        if (objeto == null) throw new RuntimeException(mensajeFallo);
    }
}