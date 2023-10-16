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

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curricula;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.services.CurriculasListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditCurriculumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditCurriculumsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CurriculasListAdapter adapter;

    public EditCurriculumsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditCurriculumsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditCurriculumsFragment newInstance(String param1, String param2) {
        EditCurriculumsFragment fragment = new EditCurriculumsFragment();
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
        return inflater.inflate(R.layout.fragment_edit_curriculums, container, false);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.curriculumListView) {
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
                Curricula curricula = (Curricula) adapter.getItem(info.position);
                LogicServices logicServices = new LogicServices(getActivity());
                logicServices.borrarCurricula(curricula);
                adapter.cambiarCurriculas(logicServices.listarCurriculas());
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