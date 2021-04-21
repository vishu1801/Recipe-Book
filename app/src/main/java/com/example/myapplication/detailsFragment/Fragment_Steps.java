package com.example.myapplication.detailsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class Fragment_Steps extends Fragment {
    View view,rootview;
    public Fragment_Steps() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_steps,container,false);
        rootview = inflater.inflate(R.layout.fragment_steps,null);
        String id=getArguments().getString("id");
        return rootview;
    }
}
