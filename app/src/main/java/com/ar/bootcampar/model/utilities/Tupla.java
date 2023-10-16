package com.ar.bootcampar.model.utilities;

public class Tupla<T, U> {
    public final T izquierda;
    public final U derecha;

    public Tupla(T izquierda, U derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }
}