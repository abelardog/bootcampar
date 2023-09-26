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
import com.ar.bootcampar.model.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<Course> courseList;

    public CourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_course_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courseList.get(position);

        // aqui se va a asignar los valores a los elementos de la vista que mostraran el curso, para la imagen, el titulo y el icono de favorito
        holder.imageViewCourse.setImageResource(getImageResourceByName(course.getImageName(), holder.itemView.getContext()));
        holder.textViewCourseTitle.setText(course.getTitle());

        if (course.isFavorite()) {
            holder.imageViewFavorite.setImageResource(R.drawable.ic_filled_heart);
        } else {
            holder.imageViewFavorite.setImageResource(R.drawable.ic_empty_heart);
        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
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
