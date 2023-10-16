package com.ar.bootcampar.services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Categoria;

import java.util.List;

public class CategoriasListAdapter extends BaseAdapter {
    private List<Categoria> categorias;

    public CategoriasListAdapter(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @Override
    public int getCount() {
        return this.categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return this.categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.categorias.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        }

        Categoria categoria = (Categoria)getItem(position);
        ((TextView)convertView.findViewById(R.id.textCategoryItemName)).setText(categoria.getNombre());
        ((TextView)convertView.findViewById(R.id.textCategoryItemDescription)).setText(categoria.getDescripcion());
        return convertView;
    }

    public void cambiarCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
