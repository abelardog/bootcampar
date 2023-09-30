package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

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

    public void onClickRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onLoginClick(View view) {
        TextView usernameTextView = (TextView)findViewById(R.id.editEmailAddress);
        if (usernameTextView.getText().toString().equals("alumno@gmail.com")) {
            TextView passwordTextView = (TextView)findViewById(R.id.editTextPassword);
            if (passwordTextView.getText().toString().equals("123456")) {
                Toast.makeText(getApplicationContext(), R.string.welcomeMessage, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), R.string.invalidLoginMessage, Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.invalidLoginMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void goToResetPassword(View view) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }
}