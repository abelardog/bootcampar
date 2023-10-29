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
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Categoria;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.IDatabase;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.services.CategoriasListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditCategoriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CategoriasListAdapter adapter;

    public EditCategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditCategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditCategoriesFragment newInstance(String param1, String param2) {
        EditCategoriesFragment fragment = new EditCategoriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_categories, container, false);

        IDatabase database = Database.CreateWith(getActivity());
        ListView listView = (ListView)view.findViewById(R.id.categoryListView);
        registerForContextMenu(listView);
        adapter = new CategoriasListAdapter(database.listarCategorias());
        listView.setAdapter(adapter);

        Button button = (Button)view.findViewById(R.id.buttonSaveCategory);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = ((TextView)getView().findViewById(R.id.editCategoryName)).getText().toString();
                String descripcion = ((TextView)getView().findViewById(R.id.editCategoryDescription)).getText().toString();

                // TODO: Mover esto a LogicServices.grabarCategoria y ajustar metodos
                if (!nombre.isEmpty() && !descripcion.isEmpty()) {
                    Categoria categoria = database.buscarCategoriaONada(nombre);
                    if (categoria == null) {
                        categoria = database.crearCategoria(nombre, descripcion);
                        if (categoria != null) {
                            adapter.cambiarCategorias(database.listarCategorias());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Categoría creada", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "No se pudo crear la categoría", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "La categoría ya existe", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Por favor complete los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        EditText editCategoryDescription = (EditText)view.findViewById(R.id.editCategoryDescription);
        editCategoryDescription.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
        editCategoryDescription.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setHintText("Descripción de la categoría");
            }
        });

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.categoryListView) {
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
            }
            else if (item.getItemId() == R.id.menu_delete_item) {
                Categoria categoria = (Categoria) adapter.getItem(info.position);
                LogicServices logicServices = new LogicServices(getActivity());
                logicServices.borrarCategoria(categoria);
                adapter.cambiarCategorias(logicServices.listarCategorias());
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