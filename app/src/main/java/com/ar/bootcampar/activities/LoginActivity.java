package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.ar.bootcampar.R;

import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickRegister(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onLoginClick(View view) {
        TextView usernameTextView = (TextView)findViewById(R.id.editEmailAddress);
        if (usernameTextView.getText() == "alumno@gmail.com") {
            TextView passwordTextView = (TextView)findViewById(R.id.editTextPassword);
            if (passwordTextView.getText() == "123456") {
                // TODO: Pasar a la actividad Home
                Toast.makeText(getApplicationContext(), "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}