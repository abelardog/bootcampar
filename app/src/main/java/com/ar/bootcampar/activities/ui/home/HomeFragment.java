package com.ar.bootcampar.activities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.bootcampar.MainActivity;
import com.ar.bootcampar.activities.CourseDetailActivity;
import com.ar.bootcampar.databinding.ActivityHomeBinding;
import com.ar.bootcampar.databinding.FragmentHomeBinding;
import com.ar.bootcampar.model.Course;
import com.ar.bootcampar.model.RecentlyAddedAdapter;
import com.ar.bootcampar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements RecentlyAddedAdapter.OnItemClickListener {

    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    List<Course> source;
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
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToRecyclerViewArrayList();
        adapter = new RecentlyAddedAdapter(source.stream().filter(p -> p.isFavorite() == false).collect(Collectors.toList()), this);
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

    public void AddItemsToRecyclerViewArrayList() {
        source = Course.getDefaultCourses().stream().filter(c -> c.isFavorite() == false).collect(Collectors.toList());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {

        Course selectedItem = source.get(position);

        Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
        intent.putExtra("loggedIn", loggedIn);
        intent.putExtra("title", selectedItem.getTitle());
        intent.putExtra("description", selectedItem.getDescription());
        startActivity(intent);
    }
}