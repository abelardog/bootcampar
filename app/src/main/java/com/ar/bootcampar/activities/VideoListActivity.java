package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.INSCRIPTION_FOR_VIDEO_LIST;
import static com.ar.bootcampar.model.utilities.IntentConstants.LESSON_FOR_COURSE;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.databinding.ActivityCourseListBinding;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.Leccion;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.services.CourseAdapter;
import com.ar.bootcampar.services.LeccionAdapter;

import java.util.List;

public class VideoListActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityCourseListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        Inscripcion inscripcion = (Inscripcion)getIntent().getSerializableExtra(INSCRIPTION_FOR_VIDEO_LIST);

        LogicServices logicServices = new LogicServices(getApplicationContext());
        List<Leccion> listaLecciones = logicServices.buscarLecciones(inscripcion.getCurso());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewLessons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LeccionAdapter adapter = new LeccionAdapter(listaLecciones);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new LeccionAdapter.OnClickListener() {
            @Override
            public void onClick(Leccion leccion) {
                Intent intent = new Intent(VideoListActivity.this, CourseVideoActivity.class);
                intent.putExtra(LESSON_FOR_COURSE, leccion);
                startActivity(intent);
            }
        });
    }
}