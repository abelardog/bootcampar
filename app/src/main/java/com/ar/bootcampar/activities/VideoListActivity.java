package com.ar.bootcampar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.bootcampar.R;

public class VideoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
    }

    public void onChapterClick(View view) {
        Intent intent = new Intent(this, CourseVideoActivity.class);
        startActivity(intent);
    }
}


