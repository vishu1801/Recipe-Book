package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Ingredient_list_adapter extends ArrayAdapter<UserResponse> {

    private Context mcontext;
    int mResource;
    public Ingredient_list_adapter(@NonNull Context context, int resource, @NonNull ArrayList<UserResponse> objects) {
        super(context, resource, objects);
        mcontext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name=getItem(position).getName();
        String image=getItem(position).getImage();
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView = layoutInflater.inflate(mResource,parent,false);
        TextView ingredient_name=(TextView) convertView.findViewById(R.id.ingredient_name);
        ingredient_name.setText(name);
        ImageView ingredient_image = (ImageView) convertView.findViewById(R.id.ingredient_image);
        Picasso.get()
                .load("https://spoonacular.com/cdn/ingredients_100x100/"+image)
                .fit()
                .centerInside()
                .into(ingredient_image);
        return convertView;
    }
}


