package com.ar.bootcampar.model;

public class Course {
    private String imageName;
    private String title;
    private boolean isFavorite;

    public Course(String imageName, String title, boolean isFavorite) {
        this.imageName = imageName;
        this.title = title;
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
}
