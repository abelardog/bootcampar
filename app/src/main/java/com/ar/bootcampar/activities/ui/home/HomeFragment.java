package com.ar.bootcampar.activities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.ar.bootcampar.model.utilities.IntentConstants.COURSE_FOR_COURSE_DETAIL;
import static com.ar.bootcampar.model.utilities.IntentConstants.LOGGED_IN_STATUS_FOR_COURSE_DETAIL;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.MainActivity;
import com.ar.bootcampar.activities.CourseDetailActivity;
import com.ar.bootcampar.databinding.FragmentHomeBinding;
import com.ar.bootcampar.model.Curso;
import com.ar.bootcampar.model.LogicServices;
import com.ar.bootcampar.model.RecentlyAddedAdapter;
import com.ar.bootcampar.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RecentlyAddedAdapter.OnItemClickListener {

    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    List<Curso> source;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecentlyAddedAdapter adapter;
    LinearLayoutManager HorizontalLayout;
    View ChildView;
    boolean loggedIn;
    int RecyclerViewItemPosition;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = (RecyclerView) root.findViewById(R.id.recently_added_courses);
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        LogicServices logicServices = new LogicServices(getActivity());
        AddItemsToRecyclerViewArrayList(logicServices.listarCursos());
        adapter = new RecentlyAddedAdapter(new ArrayList<>(source), this);
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(adapter);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            loggedIn = !(activity instanceof MainActivity);
        }
        else {
            loggedIn = false;
        }

        return root;
    }

    public void AddItemsToRecyclerViewArrayList(List<Curso> cursos) {
        source = cursos;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {

        Curso selectedItem = source.get(position);

        Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
        intent.putExtra(LOGGED_IN_STATUS_FOR_COURSE_DETAIL, loggedIn);
        intent.putExtra(COURSE_FOR_COURSE_DETAIL, selectedItem);
        startActivity(intent);
    }
}