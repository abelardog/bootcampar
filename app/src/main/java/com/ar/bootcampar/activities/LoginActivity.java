package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.CURRENT_USER;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.model.utilities.Tupla;
import com.ar.bootcampar.model.Usuario;

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
        LogicServices logicServices = new LogicServices(getApplicationContext());
        String email = ((TextView)findViewById(R.id.editEmailAddress)).getText().toString();
        String clave = ((TextView)findViewById(R.id.editTextPassword)).getText().toString();

        Tupla<Usuario, String> resultado = logicServices.ingresarUsuario(email, clave);
        Toast.makeText(getApplicationContext(), resultado.derecha, Toast.LENGTH_SHORT).show();

        if (resultado.izquierda != null) {
            logicServices.grabarUsuarioActivoEnPreferencias(resultado.izquierda);

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(CURRENT_USER, resultado.izquierda);
            startActivity(intent);
        }
    }

    public void goToResetPassword(View view) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }
}