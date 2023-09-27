package com.ar.bootcampar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Course;
import com.ar.bootcampar.services.CourseAdapter;

import java.util.ArrayList;
import java.util.List;


public class CourseListFragment extends Fragment {

    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_list, container, false);

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

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CourseAdapter adapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}