package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        Intent intent = getIntent();
        boolean loggedIn = intent.getBooleanExtra("loggedIn", false);

        Button enroll = (Button) findViewById(R.id.detailEnrollBtn);
        if (loggedIn) {
            enroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CourseDetailActivity.this, R.string.enrollment_success, Toast.LENGTH_SHORT).show();
                    // TODO: Levantar actividad con listado de videos del curso
                }
            });
        }
        else {
            enroll.setVisibility(View.INVISIBLE);
        }
    }
}