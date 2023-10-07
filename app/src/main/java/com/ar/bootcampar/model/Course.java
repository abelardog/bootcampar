package com.ar.bootcampar.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private long id;
    private String imageName;
    private String title;
    private String description;
    @Deprecated
    private boolean isFavorite;

    public Course(long id, String title, String description, boolean isFavorite, String imageName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isFavorite = isFavorite;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public String getTitle() {
        return title;
    }

    @Deprecated
    public boolean isFavorite() {
        return isFavorite;
    }

    public String getDescription() { return description; }

    public long getId() { return id; }

    public static List<Course> getDefaultCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course(1, "Android Básico desde 0", "Con este curso podrá crear su primera aplicación en Android.", true, "android_logo"));
        courseList.add(new Course(2, "Programación con Java", "Aprenda a programar en Java desde cero con este curso único!", false, "java"));
        courseList.add(new Course(3, "JavaScript para Novatos", "Un curso simple para aprender el ABC de JavaScript.", true, "js"));
        courseList.add(new Course(4, "Master en Python", "Conviértase en un experto utilizando nuestro curso de cero a héroe!", true, "python"));
        courseList.add(new Course(5, "Aprende Html como un Profesional", "Este curso le enseñará a crear sitios web con diseño responsivo.", false, "html"));
        courseList.add(new Course(6, "Desarrollo con Wordpress", "Aprenda a utilizar el framework más popular del mundo.", true, "wordpress"));
        courseList.add(new Course(7, "Test Unitarios conceptos Avanzados", "Con este curso aprenderá a escribir código sólido utilizando pruebas unitarias.", true, "unittest"));
        courseList.add(new Course(8, "Logra el Mejor Diseño con CSS", "Un curso de nivel avanzado para aprender a realizar animaciones en CSS.", false, "css"));
        courseList.add(new Course(9, "Angular de cero a Experto", "El mejor curso en Angular. Aprenda realizando cinco copias de sitios populares.", true, "angular"));

        return courseList;
    }
}