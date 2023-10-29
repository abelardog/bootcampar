package com.ar.bootcampar.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Categoria;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.Grupo;
import com.ar.bootcampar.model.IDatabase;
import com.ar.bootcampar.model.LogicServices;

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
    private GruposListAdapter adapter;
    private TextView textViewName;
    private TextView textViewInvitation;

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

        IDatabase database = Database.CreateWith(getActivity());
        ListView listView = (ListView)view.findViewById(R.id.groupListView);
        registerForContextMenu(listView);
        adapter = new GruposListAdapter(database.listarGrupos());
        listView.setAdapter(adapter);

        textViewName = view.findViewById(R.id.editGroupName);
        textViewInvitation = view.findViewById(R.id.editInviteCode);

        Button button = (Button)view.findViewById(R.id.buttonSaveGroup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = textViewName.getText().toString();
                String invitacion = textViewInvitation.getText().toString();

                // TODO: Mover esto a LogicServices.grabarGrupo y ajustar metodos
                if (!nombre.isEmpty() && !invitacion.isEmpty()) {
                    Grupo grupo = database.buscarGrupoONada(invitacion);
                    if (grupo == null) {
                        grupo = database.crearGrupo(nombre, invitacion);
                        if (grupo != null) {
                            adapter.cambiarGrupos(database.listarGrupos());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Grupo creado", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "No se pudo crear el grupo", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        database.modificarGrupo(grupo, nombre, invitacion);
                        adapter.cambiarGrupos(database.listarGrupos());
                        adapter.notifyDataSetChanged();

                        Toast.makeText(getContext(), "El grupo fue modificado", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Por favor complete los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.groupListView) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.crud_item_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if (info != null) {
            if (item.getItemId() == R.id.menu_item_edit) {
                Grupo grupo = (Grupo) adapter.getItem(info.position);
                textViewName.setText(grupo.getNombre());
                textViewInvitation.setText(grupo.getInvitacion());
                return true;
            } else if (item.getItemId() == R.id.menu_delete_item) {
                Grupo grupo = (Grupo) adapter.getItem(info.position);
                LogicServices logicServices = new LogicServices(getActivity());
                logicServices.borrarGrupo(grupo);
                adapter.cambiarGrupos(logicServices.listarGrupos());
                adapter.notifyDataSetChanged();
                return true;
            }
        }
        else {
            return true;
        }

        return super.onContextItemSelected(item);
    }
}