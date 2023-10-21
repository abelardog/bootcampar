package com.ar.bootcampar.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.services.SharedPreferencesManager;

public class EditProfile extends Fragment {
    public static EditProfile newInstance() {
        return new EditProfile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Usuario usuario = new SharedPreferencesManager(getActivity().getApplicationContext()).cargarUsuario();
        if (usuario != null) {
            TextView textView = view.findViewById(R.id.editProfileFirstName);
            textView.setText(usuario.getNombre());
            ((TextView)view.findViewById(R.id.editProfileLastName)).setText(usuario.getApellido());
        }
    }
}