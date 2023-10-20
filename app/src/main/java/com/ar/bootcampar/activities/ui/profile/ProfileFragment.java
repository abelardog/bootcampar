package com.ar.bootcampar.activities.ui.profile;

import static com.ar.bootcampar.model.utilities.IntentConstants.CURRENT_USER;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ar.bootcampar.R;
import com.ar.bootcampar.activities.CourseListActivity;
import com.ar.bootcampar.activities.EditProfileActivity;
import com.ar.bootcampar.databinding.FragmentProfileBinding;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.services.SharedPreferencesManager;

public class ProfileFragment extends Fragment {
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Usuario usuario = (Usuario)intent.getSerializableExtra(CURRENT_USER);
            CargarInformacionDeUsuario(usuario);
        }
    };

    private FragmentProfileBinding binding;
    private TextView textViewNombre;
    private TextView textViewApellido;
    private TextView textViewEmail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(receiver, new IntentFilter("update-name"));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button button = (Button) root.findViewById(R.id.gotoEditProfile);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
                //Navigation.findNavController(v).navigate(R.id.navigation_profile_edition);
            }
        });
         button = (Button) root.findViewById(R.id.button_favorites);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), CourseListActivity.class);
                startActivity(intent);
            }
        });

        textViewNombre = (TextView)root.findViewById(R.id.textProfileUserName);
        textViewApellido = (TextView)root.findViewById(R.id.textProfileUserLastName);
        textViewEmail = (TextView)root.findViewById(R.id.textProfileUserEmail);

        Usuario usuario = new SharedPreferencesManager(getActivity()).cargarUsuario();
        CargarInformacionDeUsuario(usuario);

        return root;
    }

    private void CargarInformacionDeUsuario(Usuario usuario) {
        if (usuario != null) {
            textViewNombre.setText(usuario.getNombre());
            textViewApellido.setText(usuario.getApellido());
            textViewEmail.setText(usuario.getEmail());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(receiver);
        binding = null;
    }
}