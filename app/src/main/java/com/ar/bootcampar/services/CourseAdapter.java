package com.ar.bootcampar.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Inscripcion;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private CourseAdapter.OnClickListener onFavoriteClickListener;
    private CourseAdapter.OnClickListener onCourseClickListener;
    private List<Curso> listaCursos;
    private List<Inscripcion> listaInscripciones;

    public CourseAdapter(List<Curso> listaCursos, List<Inscripcion> listaInscripciones) {
        this.listaCursos = listaCursos;
        this.listaInscripciones = listaInscripciones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_course_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Curso curso = listaCursos.get(position);

        holder.imageViewCourse.setImageResource(getImageResourceByName(curso.getImagen(), holder.itemView.getContext()));
        holder.imageViewCourse.setOnClickListener(v -> {
            if (onCourseClickListener != null) {
                onCourseClickListener.onClick(position, curso);
            }
        });

        holder.textViewCourseTitle.setText(curso.getTitulo());
        holder.textViewCourseTitle.setOnClickListener(v -> {
            if (onCourseClickListener != null) {
                onCourseClickListener.onClick(position, curso);
            }
        });

        holder.imageViewFavorite.setOnClickListener(v -> {
            if (onFavoriteClickListener != null) {
                onFavoriteClickListener.onClick(position, curso);
            }
        });

        Inscripcion inscripcion = listaInscripciones.stream()
                .filter(p -> p.getCurso().getId() == curso.getId())
                .findFirst()
                .orElse(null);

        if (inscripcion == null) {
            holder.imageViewFavorite.setVisibility(View.INVISIBLE);
        }
        else {
            holder.imageViewFavorite.setVisibility(View.VISIBLE);
            if (inscripcion.getFavorito()) {
                holder.imageViewFavorite.setImageResource(R.drawable.ic_filled_heart);
            }
            else {
                holder.imageViewFavorite.setImageResource(R.drawable.ic_empty_heart);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

    public void cambiarCursos(List<Curso> cursos, List<Inscripcion> inscripciones) {
        listaCursos = cursos;
        listaInscripciones = inscripciones;
    }

    public void setOnClickListeners(CourseAdapter.OnClickListener onFavoriteClickListener, CourseAdapter.OnClickListener onCourseClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener;
        this.onCourseClickListener = onCourseClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Curso curso);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCourse;
        TextView textViewCourseTitle;
        ImageView imageViewFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCourse = itemView.findViewById(R.id.imageViewCourse);
            textViewCourseTitle = itemView.findViewById(R.id.textViewCourseTitle);
            imageViewFavorite = itemView.findViewById(R.id.imageViewFavorite);
        }
    }

    // esto es para obtener el recurso de imagen según el nombre que se le pase
    private int getImageResourceByName(String imageName, Context context) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}
