package com.ar.bootcampar.activities.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.databinding.ActivityHomeBinding;
import com.ar.bootcampar.databinding.FragmentHomeBinding;
import com.ar.bootcampar.model.RecentlyAddedAdapter;
import com.ar.bootcampar.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    ArrayList<String> source;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecentlyAddedAdapter adapter;
    LinearLayoutManager HorizontalLayout;
    View ChildView;
    int RecylcerViewItemPosition;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textJustAdded;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        recyclerView = (RecyclerView)root.findViewById(R.id.recently_added_courses);
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToRecyclerViewArrayList();
        adapter = new RecentlyAddedAdapter(source);
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(adapter);
        return root;
    }

    public void AddItemsToRecyclerViewArrayList() {
        source = new ArrayList<>();
        source.add("Android Studio");
        source.add("Python Inicial");
        source.add("Python Intermedio");
        source.add("Python Avanzado");
        source.add("JavaScript");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}