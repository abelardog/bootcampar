package com.ar.bootcampar.services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ar.bootcampar.R;

import java.util.List;
import java.util.function.Function;

public class SpinnerAdapter<T> extends BaseAdapter {
    private List<T> items;
    private final Function<T, Long> obtenerId;
    private final Function<T, String> obtenerText;

    public SpinnerAdapter(List<T> items, Function<T, Long> obtenerId, Function<T, String> obtenerTexto) {
        this.items = items;
        this.obtenerId = obtenerId;
        this.obtenerText = obtenerTexto;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return obtenerId.apply(this.items.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        T item = this.items.get(position);
        ((TextView) convertView.findViewById(R.id.textSpinnerTitle)).setText(obtenerText.apply(item));
        return convertView;
    }
}