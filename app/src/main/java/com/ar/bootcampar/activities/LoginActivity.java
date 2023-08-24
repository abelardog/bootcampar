package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ar.bootcampar.R;
import android.widget.TextView;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tvError = (TextView) findViewById(R.id.tvError);
        tvError.setText("Error");
    }

    public void onClickRegister(){
        // Crea el intento de la nueva actividad
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        // Abre la nueva actividad
        startActivity(intent);

    }
}