package com.ar.bootcampar.model;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private final long id;
    private final String imagen;
    private final String title;
    private final String description;
    private final int nivel;

    public Curso(long id, String title, String description, int nivel, String imageName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.nivel = nivel;
        this.imagen = imageName;
    }

    public String getImageName() {
        return imagen;
    }

    public String getTitle() {
        return title;
    }

    // TODO: Borrar
    @Deprecated
    public boolean isFavorite() {
        return false;
    }

    public String getDescription() { return description; }

    public long getId() { return id; }

    public static List<Curso> getDefaultCourses() {
        List<Curso> listaCursos = new ArrayList<>();
        listaCursos.add(new Curso(1, "Android Básico desde 0", "Con este curso podrá crear su primera aplicación en Android.", 1, "android_logo"));
        listaCursos.add(new Curso(2, "Programación con Java", "Aprenda a programar en Java desde cero con este curso único!", 2, "java"));
        listaCursos.add(new Curso(3, "JavaScript para Novatos", "Un curso simple para aprender el ABC de JavaScript.", 1, "js"));
        listaCursos.add(new Curso(4, "Master en Python", "Conviértase en un experto utilizando nuestro curso de cero a héroe!", 2, "python"));
        listaCursos.add(new Curso(5, "Aprende Html como un Profesional", "Este curso le enseñará a crear sitios web con diseño responsivo.", 1, "html"));
        listaCursos.add(new Curso(6, "Desarrollo con Wordpress", "Aprenda a utilizar el framework más popular del mundo.", 1, "wordpress"));
        listaCursos.add(new Curso(7, "Test Unitarios conceptos Avanzados", "Con este curso aprenderá a escribir código sólido utilizando pruebas unitarias.", 1, "unittest"));
        listaCursos.add(new Curso(8, "Logra el Mejor Diseño con CSS", "Un curso de nivel avanzado para aprender a realizar animaciones en CSS.", 2, "css"));
        listaCursos.add(new Curso(9, "Angular de cero a Experto", "El mejor curso en Angular. Aprenda realizando cinco copias de sitios populares.", 1, "angular"));

        return listaCursos;
    }
}