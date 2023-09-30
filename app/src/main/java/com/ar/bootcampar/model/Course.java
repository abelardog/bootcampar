package com.ar.bootcampar.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String imageName;
    private String title;
    private String description;
    private boolean isFavorite;

    public Course(String imageName, String title, String description, boolean isFavorite) {
        this.imageName = imageName;
        this.title = title;
        this.description = description;
        this.isFavorite = isFavorite;
    }

    public String getImageName() {
        return imageName;
    }

    public String getTitle() {
        return title;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getDescription() { return description; }

    public static List<Course> getDefaultCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("android_logo", "Android Básico desde 0", "Con este curso podrá crear su primera aplicación en Android.", true));
        courseList.add(new Course("java", "Programación con Java", "Aprenda a programar en Java desde cero con este curso único!", false));
        courseList.add(new Course("js", "JavaScript para Novatos", "Un curso simple para aprender el ABC de JavaScript.", true));
        courseList.add(new Course("python", "Master en Python", "Conviértase en un experto utilizando nuestro curso de cero a héroe!", true));
        courseList.add(new Course("html", "Aprende Html como un Profesional", "Este curso le enseñará a crear sitios web con diseño responsivo.", false));
        courseList.add(new Course("wordpress", "Desarrollo con Wordpress", "Aprenda a utilizar el framework más popular del mundo.", true));
        courseList.add(new Course("unittest", "Test Unitarios conceptos Avanzados", "Con este curso aprenderá a escribir código sólido utilizando pruebas unitarias.", true));
        courseList.add(new Course("css", "Logra el Mejor Diseño con CSS", "Un curso de nivel avanzado para aprender a realizar animaciones en CSS.", false));
        courseList.add(new Course("angular", "Angular de cero a Experto", "El mejor curso en Angular. Aprenda realizando cinco copias de sitios populares.", true));

        return courseList;
    }
}