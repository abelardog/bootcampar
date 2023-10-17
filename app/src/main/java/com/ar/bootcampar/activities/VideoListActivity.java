package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.INSCRIPTION_FOR_VIDEO_LIST;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Inscripcion;

public class VideoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        Inscripcion inscripcion = (Inscripcion)getIntent().getSerializableExtra(INSCRIPTION_FOR_VIDEO_LIST);
    }

    public void onChapterClick(View view) {
        Intent intent = new Intent(this, CourseVideoActivity.class);
        startActivity(intent);
    }
}