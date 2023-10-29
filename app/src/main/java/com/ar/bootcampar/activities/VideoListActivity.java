package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.INSCRIPTION_FOR_VIDEO_LIST;
import static com.ar.bootcampar.model.utilities.IntentConstants.LESSON_FOR_COURSE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.Leccion;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.services.LeccionAdapter;

import java.util.List;

public class VideoListActivity extends AppCompatActivity {
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private Inscripcion inscripcion;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        LogicServices logicServices = new LogicServices(getApplicationContext());
        inscripcion = (Inscripcion)getIntent().getSerializableExtra(INSCRIPTION_FOR_VIDEO_LIST);

        star1 = findViewById(R.id.star1);
        star1.setOnClickListener(v -> {
            inscripcion = logicServices.actualizarPuntuacion(inscripcion, 1);
            refrescarEstrellas();
        });

        star2 = findViewById(R.id.star2);
        star2.setOnClickListener(v -> {
            inscripcion = logicServices.actualizarPuntuacion(inscripcion, 2);
            refrescarEstrellas();
        });

        star3 = findViewById(R.id.star3);
        star3.setOnClickListener(v -> {
            inscripcion = logicServices.actualizarPuntuacion(inscripcion, 3);
            refrescarEstrellas();
        });

        star4 = findViewById(R.id.star4);
        star4.setOnClickListener(v -> {
            inscripcion = logicServices.actualizarPuntuacion(inscripcion, 4);
            refrescarEstrellas();
        });

        star5 = findViewById(R.id.star5);
        star5.setOnClickListener(v -> {
            inscripcion = logicServices.actualizarPuntuacion(inscripcion, 5);
            refrescarEstrellas();
        });

        if (inscripcion != null) {
            List<Leccion> listaLecciones = logicServices.buscarLecciones(inscripcion.getCurso());

            RecyclerView recyclerView = findViewById(R.id.recyclerViewLessons);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            LeccionAdapter adapter = new LeccionAdapter(listaLecciones);
            recyclerView.setAdapter(adapter);

            ((TextView) findViewById(R.id.textVideoListTitle)).setText(inscripcion.getCurso().getTitulo());

            adapter.setOnClickListener(leccion -> {
                Intent intent = new Intent(VideoListActivity.this, CourseVideoActivity.class);
                intent.putExtra(LESSON_FOR_COURSE, leccion);
                startActivity(intent);
            });

            refrescarEstrellas();
        }
    }

    public void refrescarEstrellas() {
        CourseDetailActivity.setearPuntuacion(inscripcion.getPuntuacion(), star1, star2, star3, star4, star5);
    }
}