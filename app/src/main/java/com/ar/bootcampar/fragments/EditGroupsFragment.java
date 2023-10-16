package com.ar.bootcampar.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.IDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditGroupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditGroupsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditGroupsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditGroupsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditGroupsFragment newInstance(String param1, String param2) {
        EditGroupsFragment fragment = new EditGroupsFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_groups, container, false);

        Button button = (Button)view.findViewById(R.id.buttonSaveGroup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = ((TextView)getView().findViewById(R.id.editGroupName)).getText().toString();
                String invitacion = ((TextView)getView().findViewById(R.id.editInviteCode)).getText().toString();

                // TODO: Mover esto a LogicServices.grabarGrupo y ajustar metodos
                if (!nombre.isEmpty() && !invitacion.isEmpty()) {
                    IDatabase database = Database.CreateWith(getContext());
                    Grupo grupo = database.buscarGrupoONada(invitacion);
                    if (grupo == null) {
                        grupo = database.crearGrupo(nombre, invitacion);
                        if (grupo != null) {
                            Toast.makeText(getContext(), "Grupo creado", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "No se pudo crear el grupo", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "El código de invitación ya existe", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Por favor complete los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}