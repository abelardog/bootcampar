package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.CURRENT_USER;
import static com.ar.bootcampar.model.utilities.IntentConstants.UPDATE_NAME_BROADCAST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.model.utilities.Tupla;
import com.ar.bootcampar.services.SharedPreferencesManager;

public class EditProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);

        Usuario usuario = new SharedPreferencesManager(getApplicationContext()).cargarUsuario();
        if (usuario != null) {
            ((TextView)findViewById(R.id.editProfileFirstName)).setText(usuario.getNombre());
            ((TextView)findViewById(R.id.editProfileLastName)).setText(usuario.getApellido());
        }

        Button button = (Button) findViewById(R.id.buttonProfileUpdate);
        button.setOnClickListener(v -> {
            String firstName = ((EditText)findViewById(R.id.editProfileFirstName)).getText().toString();
            String lastName = ((EditText)findViewById(R.id.editProfileLastName)).getText().toString();
            String oldPassword = ((EditText)findViewById(R.id.editProfileOldPassword)).getText().toString();
            String newPassword = ((EditText)findViewById(R.id.editProfileNewPassword1)).getText().toString();
            String confirmPassword = ((EditText)findViewById(R.id.editProfileNewPassword2)).getText().toString();

            LogicServices logicServices = new LogicServices(getApplicationContext());
            Tupla<Usuario, String> resultado = logicServices.modificarUsuario(usuario, firstName, lastName, oldPassword, newPassword, confirmPassword);
            Toast.makeText(EditProfileActivity.this, resultado.derecha, Toast.LENGTH_SHORT).show();

            if (resultado.izquierda != null) {
                new SharedPreferencesManager(getApplicationContext()).grabarUsuario(resultado.izquierda);
                Intent intent = new Intent(UPDATE_NAME_BROADCAST);
                intent.putExtra(CURRENT_USER, resultado.izquierda);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                finish();
            }
        });
    }
}