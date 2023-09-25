package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ar.bootcampar.R;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    public void resetPassword(View view) {
        EditText emailEditText  = (EditText)findViewById(R.id.editTextEmailRecovery);
        String email = emailEditText.getText().toString();
        if (isValidEmail(email))
        {
            Toast.makeText(this, R.string.restoration_mail_sent, Toast.LENGTH_LONG).show();
        }
    }

    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void goBack(View view) {
        finish();
    }
}