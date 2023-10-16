package com.ar.bootcampar.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.IDatabase;

import java.util.List;

public class GruposListAdapter extends BaseAdapter {
    private List<Grupo> grupos;

    public GruposListAdapter(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    @Override
    public int getCount() {
        return this.grupos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.grupos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.grupos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list_item, parent, false);
        }

        Grupo grupo = (Grupo) getItem(position);
        ((TextView) convertView.findViewById(R.id.textGroupItemName)).setText(grupo.getNombre());
        ((TextView) convertView.findViewById(R.id.textGroupItemInvitation)).setText(grupo.getInvitacion());
        return convertView;
    }

    public void cambiarGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }
}
