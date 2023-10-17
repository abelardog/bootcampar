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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.IDatabase;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.services.CursosListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditCoursesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CursosListAdapter adapter;

    public EditCoursesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditCoursesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditCoursesFragment newInstance(String param1, String param2) {
        EditCoursesFragment fragment = new EditCoursesFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_courses, container, false);

        IDatabase database = Database.CreateWith(getActivity());
        ListView listView = (ListView)view.findViewById(R.id.courseListView);
        registerForContextMenu(listView);
        adapter = new CursosListAdapter(database.listarCursos());
        listView.setAdapter(adapter);

        Button button = (Button)view.findViewById(R.id.buttonSaveCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = ((EditText)getView().findViewById(R.id.editCourseTitle)).getText().toString();
                String descripcion = ((EditText)getView().findViewById(R.id.editCourseDescription)).getText().toString();
                String imagen = ((EditText)getView().findViewById(R.id.editCourseImage)).getText().toString();

                // TODO: Mover esto a LogicServices.grabarGrupo y ajustar metodos
                if (!titulo.isEmpty() && !descripcion.isEmpty() && !imagen.isEmpty()) {
                    Curso curso = database.buscarCursoONada(titulo);
                    if (curso == null) {
                        curso = database.crearCurso(titulo, descripcion,imagen, 1);
                        if (curso != null) {
                            adapter.cambiarCursos(database.listarCursos());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Curso creado", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "No se pudo crear el curso", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "El curso ya existe", Toast.LENGTH_SHORT).show();
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
        if (v.getId() == R.id.courseListView) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.crud_item_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if (info != null) {
            if (item.getItemId() == R.id.menu_item_edit) {
                return true;
            } else if (item.getItemId() == R.id.menu_delete_item) {
                Curso curso = (Curso)adapter.getItem(info.position);
                LogicServices logicServices = new LogicServices(getActivity());
                logicServices.borrarCurso(curso);
                adapter.cambiarCursos(logicServices.listarCursos());
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