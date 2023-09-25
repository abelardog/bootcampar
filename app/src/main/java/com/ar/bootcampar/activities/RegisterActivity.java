package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ar.bootcampar.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onRegisterClick(View view){
        String firstname = ((EditText)findViewById(R.id.editFirstName)).getText().toString();
        String lastname = ((EditText)findViewById(R.id.editLastName)).getText().toString();
        String email = ((EditText)findViewById(R.id.editEmailAddress)).getText().toString();
        String password = ((EditText)findViewById(R.id.editPassword)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.editConfirmPassword)).getText().toString();

        if (! firstname.isEmpty() && ! lastname.isEmpty() && ResetPasswordActivity.isValidEmail(email) && !password.isEmpty() && !confirmPassword.isEmpty()) {
            if (password.equals(confirmPassword)) {
                Toast.makeText(this, R.string.registration_success_message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, R.string.password_dont_match_message, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, R.string.please_complete_data_message, Toast.LENGTH_SHORT).show();
        }
    }
}