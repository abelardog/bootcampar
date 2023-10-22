package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.CURRENT_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.utilities.Tupla;
import com.ar.bootcampar.model.Usuario;
import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Credentials cachedCredentials;
    private UserProfile cachedUserProfile;
    private Auth0 account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = new Auth0(getString(R.string.com_auth0_client_id), getString(R.string.com_auth0_domain));
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

    public void onAuthClick(View view) {
        WebAuthProvider.login(account)
                .withScheme(getString(R.string.com_auth0_scheme))
                .withScope("openid profile email")
                .start(this, new Callback<Credentials, AuthenticationException>() {
                    @Override
                    public void onSuccess(Credentials credentials) {
                        cachedCredentials = credentials;

                        LogicServices logicServices = new LogicServices(getApplicationContext());
                        String email = credentials.getUser().getEmail();
                        String clave = credentials.getAccessToken();

                        Usuario usuario = logicServices.buscarUsuario(email);
                        if (usuario == null) {
                            Tupla<Usuario, String> resultado = logicServices.registrarUsuario("Nombre usuario Auth0", "Apellido usuario Auth0", email, clave, clave, Rol.Estudiante, "", "112233");
                            if (resultado.izquierda != null) {
                                usuario = resultado.izquierda;
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Error creando usuario en base de datos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        logicServices.grabarUsuarioActivoEnPreferencias(usuario);
                        logicServices.grabarUsuarioActivoEnPreferencias(usuario);

                        Toast.makeText(getApplicationContext(), R.string.welcomeMessage, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra(CURRENT_USER, usuario);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(@NonNull AuthenticationException e) {
                        showSnackBar(view, "Error: " + e.getCode());
                    }
                });
    }

    private void showSnackBar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }
}