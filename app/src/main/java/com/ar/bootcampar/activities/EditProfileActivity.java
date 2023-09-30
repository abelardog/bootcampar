package com.ar.bootcampar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ar.bootcampar.R;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);

        Button button = (Button) findViewById(R.id.buttonProfileUpdate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = ((EditText)findViewById(R.id.editFirstName)).getText().toString();
                if (! firstName.isEmpty()) {
                    String lastName = ((EditText)findViewById(R.id.editLastName)).getText().toString();
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