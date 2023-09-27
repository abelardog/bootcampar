package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ar.bootcampar.R;

public class CourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        String selectedItemTitle = getIntent().getStringExtra("selectedItem");
        //Por ahora solo trae la única data que da el items que es el Título

        TextView detailTextView = findViewById(R.id.detailTextView);
        detailTextView.setText(selectedItemTitle);
    }
}