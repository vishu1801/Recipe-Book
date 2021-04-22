package com.example.myapplication.detailsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class Steps extends Fragment {

    private String recipe_id;
    public Steps (String id){
        this.recipe_id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent_view = inflater.inflate(R.layout.fragment_steps, container, false);
        View root_view = inflater.inflate(R.layout.fragment_steps,null);
        return root_view;
    }
}