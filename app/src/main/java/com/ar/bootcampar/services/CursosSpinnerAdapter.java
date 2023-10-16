package com.ar.bootcampar.services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curso;

import java.util.List;

public class CursosSpinnerAdapter extends BaseAdapter {
    private List<Curso> cursos;

    public CursosSpinnerAdapter(List<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public int getCount() {
        return this.cursos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.cursos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.cursos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_spinner_item, parent, false);
        }

        Curso curso = (Curso) getItem(position);
        ((TextView) convertView.findViewById(R.id.textSpinnerCourseTitle)).setText(curso.getTitle());
        return convertView;
    }
}