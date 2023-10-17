package com.ar.bootcampar.services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Leccion;

import java.util.List;

public class LeccionesListAdapter extends BaseAdapter {
    private List<Leccion> lecciones;

    public LeccionesListAdapter(List<Leccion> lecciones) {
        this.lecciones = lecciones;
    }

    @Override
    public int getCount() {
        return this.lecciones.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lecciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.lecciones.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_list_item, parent, false);
        }

        Leccion leccion = (Leccion) getItem(position);
        ((TextView) convertView.findViewById(R.id.textLessonItemOrder)).setText(String.valueOf(leccion.getOrden()));
        ((TextView) convertView.findViewById(R.id.textLessonItemTitle)).setText(leccion.getTitulo());
        ((TextView) convertView.findViewById(R.id.textLessonItemContent)).setText(leccion.getContenido());
        return convertView;
    }

    public void cambiarLecciones(List<Leccion> lecciones) {
        this.lecciones = lecciones;
    }
}
