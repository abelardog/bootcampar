package com.ar.bootcampar.services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curricula;
import com.ar.bootcampar.model.Curso;

import java.util.List;

public class CurriculasListAdapter extends BaseAdapter {
    private List<Curricula> curriculas;

    public CurriculasListAdapter(List<Curricula> curriculas) {
        this.curriculas = curriculas;
    }

    @Override
    public int getCount() {
        return this.curriculas.size();
    }

    @Override
    public Object getItem(int position) {
        return this.curriculas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.curriculas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.curriculum_list_item, parent, false);
        }

        Curricula curricula = (Curricula) getItem(position);
        ((TextView) convertView.findViewById(R.id.textCourseItemTitle)).setText(curricula.getGrupo().getNombre());
        ((TextView) convertView.findViewById(R.id.textCourseItemDescription)).setText(curricula.getCourse().getTitle());
        return convertView;
    }

    public void cambiarCurriculas(List<Curricula> curriculas) {
        this.curriculas = curriculas;
    }
}
