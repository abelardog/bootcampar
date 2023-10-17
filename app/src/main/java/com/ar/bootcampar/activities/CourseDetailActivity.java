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
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.model.utilities.Tupla;
import com.ar.bootcampar.services.SharedPreferencesManager;

public class CourseDetailActivity extends AppCompatActivity {
    private Curso curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Intent intent = getIntent();
        curso = (Curso)intent.getSerializableExtra("curso");

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
                    LogicServices logicServices = new LogicServices(getApplicationContext());
                    Usuario usuario = logicServices.obtenerUsuarioActivoDePreferencias();
                    Tupla<Inscripcion, String> resultado = logicServices.inscribirCurso(usuario, curso);
                    Toast.makeText(CourseDetailActivity.this, resultado.derecha, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CourseDetailActivity.this, VideoListActivity.class);
                    intent.putExtra("inscripcion", resultado.izquierda);
                    startActivity(intent);
                }
            });
        }
        else {
            enroll.setVisibility(View.INVISIBLE);
        }
    }
}