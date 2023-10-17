package com.ar.bootcampar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.services.CourseAdapter;

import java.util.List;


public class CourseListFragment extends Fragment {

    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_list, container, false);

        Database db = (Database) Database.CreateWith(getContext());
        List<Curso> cursos = db.listarCursos();

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CourseAdapter adapter = new CourseAdapter(cursos);
        recyclerView.setAdapter(adapter);

        return rootView;
    }


    public void toggleFavorite(View view, Curso curso) {

        // Accede a la imagen de favorito
        ImageView imageViewFavorite = view.findViewById(R.id.imageViewFavorite);

        // Cambia la imagen según el estado
        if (curso.isFavorito()) {
            imageViewFavorite.setImageResource(R.drawable.ic_empty_heart);
        } else {
            imageViewFavorite.setImageResource(R.drawable.ic_filled_heart);
        }

        // Actualiza el estado en la BD
        Database db = (Database) Database.CreateWith(getContext());
        db.actualizarFavoritoCurso(curso, !curso.isFavorito());

        curso.setFavorito(!curso.isFavorito());

    }
}