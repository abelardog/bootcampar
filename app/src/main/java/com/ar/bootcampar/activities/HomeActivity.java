package com.ar.bootcampar.activities;

import static com.ar.bootcampar.model.utilities.IntentConstants.CURRENT_USER;

import android.os.Bundle;
import android.view.View;

import com.ar.bootcampar.model.Rol;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.services.SharedPreferencesManager;
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

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_courses, R.id.navigation_help, R.id.navigation_notifications, R.id.navigation_contact,
                R.id.navigation_admin_courses, R.id.navigation_admin_categories, R.id.navigation_admin_groups, R.id.navigation_admin_curriculums, R.id.navigation_admin_lessons)
                .build();

        Usuario usuario = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            usuario = getIntent().getSerializableExtra(CURRENT_USER, Usuario.class);
        }

        if (usuario == null) {
            usuario = new SharedPreferencesManager(getApplicationContext()).cargarUsuario();
        }

        if (usuario != null) {
            if (usuario.getRol() == Rol.Estudiante) {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_user);
                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(binding.navView, navController);

                View view = findViewById(R.id.nav_view_admin);
                view.setVisibility(View.GONE);
            }
            else {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_user);
                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(binding.navViewAdmin, navController);

                View view = findViewById(R.id.nav_view);
                view.setVisibility(View.GONE);
            }
        }
    }
}