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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.IDatabase;
import com.ar.bootcampar.model.Leccion;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.services.SpinnerAdapter;
import com.ar.bootcampar.services.LeccionesListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditLessonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditLessonsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LeccionesListAdapter adapter;

    public EditLessonsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditLessonsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditLessonsFragment newInstance(String param1, String param2) {
        EditLessonsFragment fragment = new EditLessonsFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_lessons, container, false);

        IDatabase database = Database.CreateWith(getActivity());
        ListView listView = (ListView)view.findViewById(R.id.lessonListView);
        registerForContextMenu(listView);
        adapter = new LeccionesListAdapter(database.listarLecciones());
        listView.setAdapter(adapter);

        Spinner dropdown = view.findViewById(R.id.spinner_course_lesson);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter<Curso>(database.listarCursos(), Curso::getId, Curso::getTitle);
        dropdown.setAdapter(spinnerAdapter);

        Button button = (Button)view.findViewById(R.id.buttonSaveLesson);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orden = Integer.valueOf(((TextView)getView().findViewById(R.id.editLessonOrder)).getText().toString());
                String titulo = ((TextView)getView().findViewById(R.id.editLessonTitle)).getText().toString();
                String contenido = ((TextView)getView().findViewById(R.id.editLessonContent)).getText().toString();
                Curso curso = (Curso)(((Spinner)getView().findViewById(R.id.spinner_course_lesson)).getSelectedItem());

                // TODO: Mover esto a LogicServices.grabarGrupo y ajustar metodos
                if (!titulo.isEmpty() && !contenido.isEmpty()) {
                    Leccion leccion = database.crearLeccion(titulo, contenido, 0, orden, curso);
                    if (leccion != null) {
                        adapter.cambiarLecciones(database.listarLecciones());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Lección creada", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "No se pudo crear la lección", Toast.LENGTH_SHORT).show();
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
        if (v.getId() == R.id.lessonListView) {
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
                Leccion leccion = (Leccion) adapter.getItem(info.position);
                LogicServices logicServices = new LogicServices(getActivity());
                logicServices.borrarLeccion(leccion);
                adapter.cambiarLecciones(logicServices.listarLecciones());
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