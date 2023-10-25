package com.ar.bootcampar.fragments;

import static com.ar.bootcampar.model.utilities.IntentConstants.COURSE_FOR_COURSE_DETAIL;
import static com.ar.bootcampar.model.utilities.IntentConstants.CURRENT_USER;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.activities.CourseDetailActivity;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Inscripcion;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.services.CourseAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class CourseListFragment extends Fragment {

    public CourseListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_list, container, false);

        LogicServices logicServices = new LogicServices(getContext());
        Usuario usuario = logicServices.obtenerUsuarioActivoDePreferencias();
        List<Curso> listaCursos = logicServices.listarCursos(usuario);
        List<Inscripcion> listaInscripciones = logicServices.buscarInscripciones(usuario);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CourseAdapter adapter = new CourseAdapter(listaCursos, logicServices.buscarInscripciones(usuario));
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListeners(
                (position, curso) -> {
                    Inscripcion inscripcion = logicServices.buscarInscripcion(usuario, curso);
                    if (inscripcion != null) {
                        Inscripcion inscripcionModificada = logicServices.alternarFavoritismo(inscripcion);
                        adapter.cambiarCursos(logicServices.listarCursos(usuario), listaInscripciones.stream().map(i -> i.getCurso().getId() == curso.getId()? inscripcionModificada : i).collect(Collectors.toList()));
                        adapter.notifyItemChanged(position);

                        Toast.makeText(getContext(), "Curso marcado como " + (inscripcion.getFavorito()? "favorito" : "no favorito"), Toast.LENGTH_SHORT).show();
                    }
                },
                (position, curso) -> {
                    Intent intent2 = new Intent(getContext(), CourseDetailActivity.class);
                    intent2.putExtra(CURRENT_USER, usuario);
                    intent2.putExtra(COURSE_FOR_COURSE_DETAIL, curso);
                    startActivity(intent2);
                });

        return rootView;
    }
}