package com.ar.bootcampar.fragments;

import static com.ar.bootcampar.model.utilities.IntentConstants.COURSE_FOR_COURSE_DETAIL;
import static com.ar.bootcampar.model.utilities.IntentConstants.CURRENT_USER;
import static com.ar.bootcampar.model.utilities.IntentConstants.LOGGED_IN_STATUS_FOR_COURSE_DETAIL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseListFragment extends Fragment {

    EditText editTextBuscar;
    private CourseAdapter adapter;
    private Usuario usuario;
    private List<Inscripcion> listaInscripciones;

    public CourseListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_list, container, false);

        Button buttonBuscar = rootView.findViewById(R.id.buttonBuscar);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBuscar(v);
            }
        });

        LogicServices logicServices = new LogicServices(getContext());
        usuario = logicServices.obtenerUsuarioActivoDePreferencias();
        List<Curso> listaCursos = logicServices.listarCursos();
        listaInscripciones = logicServices.buscarInscripciones(usuario);


        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CourseAdapter(listaCursos, logicServices.buscarInscripciones(usuario));
        recyclerView.setAdapter(adapter);

        editTextBuscar = rootView.findViewById(R.id.editTextBuscar);
        buttonBuscar.setOnClickListener(this::onClickBuscar);

        adapter.setOnClickListeners(
                (position, curso) -> {
                    Inscripcion inscripcion = logicServices.buscarInscripcion(usuario, curso);
                    if (inscripcion != null) {
                        Inscripcion inscripcionModificada = logicServices.alternarFavoritismo(inscripcion);
                        listaInscripciones = listaInscripciones.stream().map(i -> i.getCurso().getId() == curso.getId()? inscripcionModificada : i).collect(Collectors.toList());
                        adapter.cambiarCursos(logicServices.listarCursos(usuario), listaInscripciones);

                        Toast.makeText(getContext(), "Curso marcado como " + (inscripcionModificada.getFavorito()? "favorito" : "no favorito"), Toast.LENGTH_SHORT).show();
                    }
                },
                (position, curso) -> {
                    Intent intent2 = new Intent(getContext(), CourseDetailActivity.class);
                    intent2.putExtra(LOGGED_IN_STATUS_FOR_COURSE_DETAIL, true);
                    intent2.putExtra(CURRENT_USER, usuario);
                    intent2.putExtra(COURSE_FOR_COURSE_DETAIL, curso);
                    startActivity(intent2);
                });

        return rootView;
    }

    public void onClickBuscar(View view) {
        LogicServices logicServices = new LogicServices(getContext());
        String query = editTextBuscar.getText().toString();
        List<Curso> resultados = logicServices.buscarCursos(query);
        adapter.cambiarCursos(resultados, listaInscripciones);

        hideSoftKeyboard();
        TextView textViewNoResults = view.getRootView().findViewById(R.id.textViewNoResults);

        if (resultados.isEmpty()) {
            textViewNoResults.setVisibility(View.VISIBLE);
            adapter.cambiarCursos(new ArrayList<>(), listaInscripciones);
        } else {
            textViewNoResults.setVisibility(View.GONE);
            adapter.cambiarCursos(resultados, listaInscripciones);
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
        }
    }

}