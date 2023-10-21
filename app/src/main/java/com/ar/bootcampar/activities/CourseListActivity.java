package com.ar.bootcampar.activities;

import android.content.Intent;
import android.os.Bundle;

import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.services.CourseAdapter;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.databinding.ActivityCourseListBinding;

import com.ar.bootcampar.R;

import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.ar.bootcampar.databinding.ActivityCourseListBinding binding = ActivityCourseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LogicServices logicServices = new LogicServices(getApplicationContext());
        List<Curso> listaCursos = logicServices.listarCursos();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CourseAdapter adapter = new CourseAdapter(listaCursos);
        recyclerView.setAdapter(adapter);
    }

    public void openActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}