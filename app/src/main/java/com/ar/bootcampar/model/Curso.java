package com.ar.bootcampar.model;

import com.ar.bootcampar.model.utilities.Guardia;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private final long id;
    private final String imagen;
    private final String title;
    private final String description;
    private final int nivel;

    public Curso(long id, String titulo, String descripcion, String imagen, int nivel) {
        Guardia.esIdentificadorValido(id, "El id es inválido");
        Guardia.esCadenaNoVacia(titulo, "El título es inválido");
        Guardia.esCadenaNoVacia(descripcion, "La descripción es inválida");
        // TODO: Encontrar como validar link
        Guardia.esCadenaNoVacia(imagen, "El link de imagen es inválida");
        if (nivel <= 0 || nivel > 3) throw new RuntimeException("El nivel es inválido");

        this.id = id;
        this.title = titulo;
        this.description = descripcion;
        this.imagen = imagen;
        this.nivel = nivel;
    }

    public String getImageName() {
        return imagen;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() { return description; }

    public long getId() { return id; }

    public int getNivel() { return nivel; }

    public static List<Curso> getDefaultCourses() {
        List<Curso> listaCursos = new ArrayList<>();
        listaCursos.add(new Curso(1, "Android Básico desde 0", "Con este curso podrá crear su primera aplicación en Android.", "android_logo", 2));
        listaCursos.add(new Curso(2, "Programación con Java", "Aprenda a programar en Java desde cero con este curso único!", "java", 1));
        listaCursos.add(new Curso(3, "JavaScript para Novatos", "Un curso simple para aprender el ABC de JavaScript.", "js", 1));
        listaCursos.add(new Curso(4, "Master en Python", "Conviértase en un experto utilizando nuestro curso de cero a héroe!", "python", 3));
        listaCursos.add(new Curso(5, "Aprende Html como un Profesional", "Este curso le enseñará a crear sitios web con diseño responsivo.", "html", 1));
        listaCursos.add(new Curso(6, "Desarrollo con Wordpress", "Aprenda a utilizar el framework más popular del mundo.",  "wordpress", 1));
        listaCursos.add(new Curso(7, "Test Unitarios conceptos Avanzados", "Con este curso aprenderá a escribir código sólido utilizando pruebas unitarias.", "unittest", 2));
        listaCursos.add(new Curso(8, "Logra el Mejor Diseño con CSS", "Un curso de nivel avanzado para aprender a realizar animaciones en CSS.", "css", 3));
        listaCursos.add(new Curso(9, "Angular de cero a Experto", "El mejor curso en Angular. Aprenda realizando cinco copias de sitios populares.", "angular", 3));

        return listaCursos;
    }
}