package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.services.SharedPreferencesManager;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);

        Usuario usuario = null;
        usuario = new SharedPreferencesManager(getApplicationContext()).cargarUsuario();
        if (usuario != null) {
            ((TextView)findViewById(R.id.editProfileFirstName)).setText(usuario.getNombre());
            ((TextView)findViewById(R.id.editProfileLastName)).setText(usuario.getApellido());
        }

        Button button = (Button) findViewById(R.id.buttonProfileUpdate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = ((EditText)findViewById(R.id.editProfileFirstName)).getText().toString();
                if (! firstName.isEmpty()) {
                    String lastName = ((EditText)findViewById(R.id.editProfileLastName)).getText().toString();
                    if (! lastName.isEmpty()) {
                        Toast.makeText(EditProfileActivity.this, R.string.profile_updated_successfully, Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }

                Toast.makeText(EditProfileActivity.this, R.string.complete_profile_data_please, Toast.LENGTH_SHORT).show();
            }
        });
    }
}