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

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");

        TextView textView = findViewById(R.id.detailTextView);
        textView.setText(title);

        textView = findViewById(R.id.detailDescription);
        textView.setText(description);

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