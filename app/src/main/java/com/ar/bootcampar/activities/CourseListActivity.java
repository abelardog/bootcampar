package com.ar.bootcampar.activities;

import android.content.Intent;
import android.os.Bundle;

import com.ar.bootcampar.model.Course;
import com.ar.bootcampar.services.CourseAdapter;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.databinding.ActivityCourseListBinding;

import com.ar.bootcampar.R;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCourseListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCourseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("android_logo", "Android Básico desde 0", true));
        courseList.add(new Course("java", "Programación con Java", false));
        courseList.add(new Course("js", "JavaScript para Novatos", true));
        courseList.add(new Course("python", "Master en Python", true));
        courseList.add(new Course("html", "Aprende Html como un Profesional", false));
        courseList.add(new Course("wordpress", "Desarrollo con Wordpress", true));
        courseList.add(new Course("unittest", "Test Unitarios conceptos Avanzados", true));
        courseList.add(new Course("css", "Logra el Mejor Diseño con CSS", false));
        courseList.add(new Course("angular", "Angular de cero a Experto", true));

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CourseAdapter adapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(adapter);


    }

    public void openActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}