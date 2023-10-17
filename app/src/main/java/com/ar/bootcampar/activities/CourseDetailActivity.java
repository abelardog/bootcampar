package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curso;

public class CourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Intent intent = getIntent();
        Curso curso = (Curso)intent.getSerializableExtra("curso");

        TextView textView = findViewById(R.id.detailTextView);
        textView.setText(curso.getTitulo());

        textView = findViewById(R.id.detailDescription);
        textView.setText(curso.getDescripcion());

        boolean loggedIn = intent.getBooleanExtra("loggedIn", false);

        Button enroll = (Button) findViewById(R.id.detailEnrollBtn);
        if (loggedIn) {
            enroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CourseDetailActivity.this, R.string.enrollment_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CourseDetailActivity.this, VideoListActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            enroll.setVisibility(View.INVISIBLE);
        }
    }
}