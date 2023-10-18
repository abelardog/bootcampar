package com.ar.bootcampar.services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Leccion;

import java.util.List;

public class LeccionAdapter extends RecyclerView.Adapter<LeccionAdapter.ViewHolder> {
    private final List<Leccion> listaLecciones;

    public LeccionAdapter(List<Leccion> listaLecciones) {
        this.listaLecciones = listaLecciones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Leccion leccion = listaLecciones.get(position);

        holder.textViewLessonOrder.setText(String.valueOf(leccion.getOrden()));
        holder.textViewLessonTitle.setText(leccion.getTitulo());
        holder.textViewLessonContents.setText(leccion.getContenido());
    }

    @Override
    public int getItemCount() {
        return listaLecciones.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLessonOrder;
        TextView textViewLessonTitle;
        TextView textViewLessonContents;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLessonOrder = itemView.findViewById(R.id.textVideoListEntryChapter);
            textViewLessonTitle = itemView.findViewById(R.id.textVideoListEntryTitle);
            textViewLessonContents = itemView.findViewById(R.id.textVideoListEntryDescription);
        }
    }
}