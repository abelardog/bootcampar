package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.COURSE_FOR_COURSE_DETAIL;
import static com.ar.bootcampar.model.utilities.IntentConstants.INSCRIPTION_FOR_VIDEO_LIST;
import static com.ar.bootcampar.model.utilities.IntentConstants.LOGGED_IN_STATUS_FOR_COURSE_DETAIL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.model.utilities.Tupla;

public class CourseDetailActivity extends AppCompatActivity {
    private Curso curso;
    private Usuario usuario;
    private double rating;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private Button enroll;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        LogicServices logicServices = new LogicServices(getApplicationContext());
        Intent intent = getIntent();
        curso = (Curso)intent.getSerializableExtra(COURSE_FOR_COURSE_DETAIL);

        TextView textView = findViewById(R.id.detailTextView);
        textView.setText(curso.getTitulo());

        textView = findViewById(R.id.detailDescription);
        textView.setText(curso.getDescripcion());

        rating = logicServices.obtenerRatingDe(curso);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        setearPuntuacion(rating, star1, star2, star3, star4, star5);
        boolean loggedIn = intent.getBooleanExtra(LOGGED_IN_STATUS_FOR_COURSE_DETAIL, false);

        enroll = (Button) findViewById(R.id.detailEnrollBtn);
        if (loggedIn) {
            usuario = logicServices.obtenerUsuarioActivoDePreferencias();
            Inscripcion inscripcion = logicServices.buscarInscripcion(usuario, curso);
            if (inscripcion != null) {
                enroll.setText(R.string.ir_al_curso);
            } else {
                enroll.setText(R.string.inscribirse);
            }

            enroll.setOnClickListener(v -> {
                Inscripcion inscripcionActual = logicServices.buscarInscripcion(usuario, curso);
                if (inscripcionActual == null) {
                    Tupla<Inscripcion, String> resultado = logicServices.inscribirCurso(usuario, curso);
                    Toast.makeText(CourseDetailActivity.this, resultado.derecha, Toast.LENGTH_SHORT).show();
                    inscripcionActual = resultado.izquierda;
                }

                Intent intent1 = new Intent(CourseDetailActivity.this, VideoListActivity.class);
                intent1.putExtra(INSCRIPTION_FOR_VIDEO_LIST, inscripcionActual);
                startActivity(intent1);
            });
        } else {
            enroll.setVisibility(View.INVISIBLE);
        }
    }

    public static void setearPuntuacion(double rating, ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5) {
        star1.setImageResource(rating >= 1 ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        star2.setImageResource(rating >= 2 ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        star3.setImageResource(rating >= 3 ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        star4.setImageResource(rating >= 4 ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        star5.setImageResource(rating >= 5 ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        star1.invalidate();
        star2.invalidate();
        star3.invalidate();
        star4.invalidate();
        star5.invalidate();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        LogicServices logicServices = new LogicServices(getApplicationContext());
        double nuevoRating = logicServices.obtenerRatingDe(curso);
        if (nuevoRating != rating) {
            rating = nuevoRating;
            setearPuntuacion(rating, star1, star2, star3, star4, star5);
        }

        if (usuario != null) {
            Inscripcion inscripcion = logicServices.buscarInscripcion(usuario, curso);
            if (inscripcion != null) {
                enroll.setText(R.string.ir_al_curso);
            } else {
                enroll.setText(R.string.inscribirse);
            }
        }
    }
}