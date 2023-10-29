package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.COURSE_FOR_COURSE_DETAIL;
import static com.ar.bootcampar.model.utilities.IntentConstants.CURRENT_USER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.services.CourseAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class FavoriteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        Intent intent = getIntent();

        @SuppressWarnings("deprecation")
        Usuario usuario = (Usuario)intent.getSerializableExtra(CURRENT_USER);

        LogicServices logicServices = new LogicServices(getApplicationContext());
        List<Inscripcion> listaInscripciones = logicServices.listarInscripcionesFavoritas(usuario);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CourseAdapter adapter = new CourseAdapter(obtenerCursosDe(listaInscripciones), listaInscripciones);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListeners(
                (position, curso) -> {
                    Inscripcion inscripcion = logicServices.buscarInscripcion(usuario, curso);
                    if (inscripcion != null) {
                        logicServices.alternarFavoritismo(inscripcion);
                        List<Inscripcion> nuevaListaFavoritos = logicServices.listarInscripcionesFavoritas(usuario);
                        adapter.cambiarCursos(obtenerCursosDe(nuevaListaFavoritos), nuevaListaFavoritos);
                        adapter.notifyItemChanged(position);
                    }
                },
                (position, curso) -> {
                    Intent intent2 = new Intent(getApplicationContext(), CourseDetailActivity.class);
                    intent2.putExtra(CURRENT_USER, usuario);
                    intent2.putExtra(COURSE_FOR_COURSE_DETAIL, curso);
                    startActivity(intent2);
                });
    }

    private List<Curso> obtenerCursosDe(List<Inscripcion> listaInscripciones) {
        return listaInscripciones.stream().map(p -> p.getCurso()).collect(Collectors.toList());
    }

    public void openActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}