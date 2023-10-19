package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.LESSON_FOR_COURSE;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Leccion;
import com.ar.bootcampar.model.LogicServices;

public class CourseVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_video);

        Leccion leccion = (Leccion)getIntent().getSerializableExtra(LESSON_FOR_COURSE);

        LogicServices logicServices = new LogicServices(getApplicationContext());
        ((TextView)findViewById(R.id.textLessonPlayerTitle)).setText(leccion.getTitulo());
        ((TextView)findViewById(R.id.textLessonPlayerLesson)).setText(String.format("Lección %d", leccion.getOrden()));
        ((TextView)findViewById(R.id.textLessonPlayerContents)).setText(leccion.getContenido());
    }
}