package com.example.myapplication.detailsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class Overview extends Fragment {

    private String recipe_id;
    public Overview (String id){
        this.recipe_id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent_view=inflater.inflate(R.layout.fragment_overview, container, false);
        View root_view = inflater.inflate(R.layout.fragment_overview,null);

        Toast.makeText(getContext(), recipe_id, Toast.LENGTH_SHORT).show();


        return root_view;
    }
}