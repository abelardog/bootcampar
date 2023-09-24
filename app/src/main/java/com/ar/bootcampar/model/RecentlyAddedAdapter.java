package com.ar.bootcampar.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.R;

import java.util.List;

public class RecentlyAddedAdapter extends RecyclerView.Adapter<RecentlyAddedAdapter.RecentlyAddedView> {
    private List<String> list;

    public class RecentlyAddedView extends RecyclerView.ViewHolder {
        TextView textView;

        public RecentlyAddedView(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.recently_added_item_title);
        }
    }

    public RecentlyAddedAdapter(List<String> elements) {
        this.list = elements;
    }

    @NonNull
    @Override
    public RecentlyAddedView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recently_added_course, parent, false);

        return new RecentlyAddedView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyAddedView holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
