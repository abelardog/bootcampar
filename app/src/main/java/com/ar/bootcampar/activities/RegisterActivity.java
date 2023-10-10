package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;

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
        String invitationCode = ((EditText)findViewById(R.id.editInvitationCode)).getText().toString();

        try {
            LogicServices database = new LogicServices(RegisterActivity.this);
            Pair<Usuario, String> resultado = database.registrarUsuario(firstname, lastname, email, password, confirmPassword, Rol.Estudiante, "", invitationCode);
            if (resultado.first != null) {
                Toast.makeText(this, R.string.registration_success_message, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("usuario", resultado.first);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, resultado.second, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}