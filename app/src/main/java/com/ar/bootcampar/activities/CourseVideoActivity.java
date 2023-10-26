package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.LESSON_FOR_COURSE;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Leccion;

public class CourseVideoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_video);

        @SuppressWarnings("deprecation")
        Leccion leccion = (Leccion)getIntent().getSerializableExtra(LESSON_FOR_COURSE);
        if (leccion != null) {
            ((TextView) findViewById(R.id.textLessonPlayerTitle)).setText(leccion.getTitulo());
            ((TextView) findViewById(R.id.textLessonPlayerLesson)).setText(String.format(getString(R.string.leccion_numero), leccion.getOrden()));
            ((TextView) findViewById(R.id.textLessonPlayerContents)).setText(leccion.getContenido());

            WebView webView = findViewById(R.id.videoClass);
            String video = String.format("<iframe width=\"100%%\" src=\"%s\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>", leccion.getVinculo());
            webView.loadData(video, "text/html", "utf-8");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
        }
    }
}