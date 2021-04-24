package com.example.myapplication.detailsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.AllResponse.Recipe_Step_Response.Recipe_steps;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Steps_listView_Adapter extends ArrayAdapter<Recipe_steps> {

    Context mcontext;
    int mresource;
    ArrayList<Recipe_steps> data;

    public Steps_listView_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Recipe_steps> objects) {
        super(context, resource, objects);
        this.data=objects;
        this.mresource=resource;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView = layoutInflater.inflate(mresource,parent,false);

        String step_number = getItem(position).getSteps().get(position).getNumber().toString();
        String step_theory = getItem(position).getSteps().get(position).getStep();

        TextView step_number_text = convertView.findViewById(R.id.step_number_textview);
        TextView step_theory_text = convertView.findViewById(R.id.step_thory_textview);

        step_theory_text.setText(step_theory);
        step_number_text.setText(step_number);

        return convertView;
    }
}
