package com.ar.bootcampar.activities.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ar.bootcampar.R;
import com.ar.bootcampar.activities.CourseListActivity;
import com.ar.bootcampar.activities.EditProfileActivity;
import com.ar.bootcampar.databinding.FragmentProfileBinding;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.services.SharedPreferencesManager;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

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

        Usuario usuario = null;
        usuario = new SharedPreferencesManager(getActivity()).cargarUsuario();
        if (usuario != null) {
            ((TextView)root.findViewById(R.id.textProfileUserName)).setText(usuario.getNombre());
            ((TextView)root.findViewById(R.id.textProfileUserLastName)).setText(usuario.getApellido());
            ((TextView)root.findViewById(R.id.textProfileUserEmail)).setText(usuario.getEmail());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}