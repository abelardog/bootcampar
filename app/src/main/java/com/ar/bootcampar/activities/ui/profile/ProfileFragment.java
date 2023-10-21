package com.ar.bootcampar.activities.ui.profile;

import static com.ar.bootcampar.model.utilities.IntentConstants.CURRENT_USER;
import static com.ar.bootcampar.model.utilities.IntentConstants.UPDATE_NAME_BROADCAST;

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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ar.bootcampar.R;
import com.ar.bootcampar.activities.CourseListActivity;
import com.ar.bootcampar.activities.EditProfileActivity;
import com.ar.bootcampar.activities.FavoriteListActivity;
import com.ar.bootcampar.databinding.FragmentProfileBinding;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.services.SharedPreferencesManager;

public class ProfileFragment extends Fragment {
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            @SuppressWarnings("deprecation")
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

        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(receiver, new IntentFilter(UPDATE_NAME_BROADCAST));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textViewNombre = root.findViewById(R.id.textProfileUserName);
        textViewApellido = root.findViewById(R.id.textProfileUserLastName);
        textViewEmail = root.findViewById(R.id.textProfileUserEmail);

        Usuario usuario = new SharedPreferencesManager(getActivity()).cargarUsuario();
        CargarInformacionDeUsuario(usuario);

        Button button = root.findViewById(R.id.gotoEditProfile);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditProfileActivity.class);
            startActivity(intent);
        });

        button = root.findViewById(R.id.button_favorites);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FavoriteListActivity.class);
            intent.putExtra(CURRENT_USER, usuario);
            startActivity(intent);
        });

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