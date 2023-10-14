package com.ar.bootcampar.activities;

import android.os.Bundle;
import android.view.View;

import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.services.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.ar.bootcampar.R;
import com.ar.bootcampar.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_help, R.id.navigation_notifications)
                .build();

        Usuario usuario = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            usuario = getIntent().getSerializableExtra("usuarioActivo", Usuario.class);
        }

        if (usuario == null) {
            usuario = new SharedPreferencesManager(getApplicationContext()).cargarUsuario();
        }

        if (usuario != null) {
            if (usuario.getRol() != Rol.Estudiante) {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_user);
                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(binding.navView, navController);

                View view = findViewById(R.id.nav_host_fragment_activity_home_admin);
                view.setVisibility(View.GONE);
            }
            else {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_admin);
                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(binding.navView, navController);

                View view = findViewById(R.id.nav_host_fragment_activity_home_user);
                view.setVisibility(View.GONE);
            }
        }
    }
}