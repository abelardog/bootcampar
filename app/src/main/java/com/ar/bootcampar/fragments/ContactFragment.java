package com.ar.bootcampar.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Usuario;
import com.ar.bootcampar.services.SharedPreferencesManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Usuario usuario = null;
        usuario = new SharedPreferencesManager(getActivity().getApplicationContext()).cargarUsuario();
        if (usuario != null) {
            EditText editText = ((EditText)getView().findViewById(R.id.editContactFirstName));
            editText.setText(usuario.getNombre());
            editText.setEnabled(false);

            editText = ((EditText)getView().findViewById(R.id.editContactEmailAddress));
            editText.setText(usuario.getEmail());
            editText.setEnabled(false);

            editText = ((EditText)getView().findViewById(R.id.editContactPhoneNumber));
            editText.setText(usuario.getTelefono());
        }

        Button button = (Button)view.findViewById(R.id.buttonContact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = ((EditText)getView().findViewById(R.id.editContactFirstName)).getText().toString();
                String emailAddress = ((EditText)getView().findViewById(R.id.editContactEmailAddress)).getText().toString();
                String telephone = ((EditText)getView().findViewById(R.id.editContactPhoneNumber)).getText().toString();
                String message = ((EditText)getView().findViewById(R.id.editContactMessage)).getText().toString();

                if (firstName.isEmpty() || emailAddress.isEmpty() || message.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor complete todos los datos", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Gracias por contactarnos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}