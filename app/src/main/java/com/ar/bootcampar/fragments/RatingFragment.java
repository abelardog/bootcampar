package com.ar.bootcampar.fragments;

import static com.ar.bootcampar.model.utilities.IntentConstants.COURSE_FOR_COURSE_DETAIL;
import static com.ar.bootcampar.model.utilities.IntentConstants.RATING_FOR_COURSE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ar.bootcampar.R;
import com.ar.bootcampar.model.Curso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Curso curso;
    private float rating;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;

    public RatingFragment() {
        // Required empty public constructor
    }

    public static RatingFragment newInstance(Curso curso) {
        RatingFragment fragment = new RatingFragment();
        Bundle args = new Bundle();
        args.putSerializable(COURSE_FOR_COURSE_DETAIL, curso);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            curso = (Curso)getArguments().getSerializable(COURSE_FOR_COURSE_DETAIL);
            rating = getArguments().getFloat(RATING_FOR_COURSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        star1 = view.findViewById(R.id.star1);
        star2 = view.findViewById(R.id.star2);
        star3 = view.findViewById(R.id.star3);
        star4 = view.findViewById(R.id.star4);
        star5 = view.findViewById(R.id.star5);

        star1.setImageResource(rating >= 1? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        star2.setImageResource(rating >= 2? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        star3.setImageResource(rating >= 3? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        star4.setImageResource(rating >= 4? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        star5.setImageResource(rating >= 5? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        return view;
    }
}