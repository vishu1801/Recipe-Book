package com.example.myapplication.Custom_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.UserResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Ingredient_lis_for_pantry_adapter extends ArrayAdapter<UserResponse> {
    Context mContext;
    int mResource;
    ArrayList<UserResponse> list_for_pantry;
    public Ingredient_lis_for_pantry_adapter(@NonNull Context context, int resource, @NonNull ArrayList<UserResponse> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
        list_for_pantry=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView=layoutInflater.inflate(mResource,parent,false);
        String name = getItem(position).getName().toString();
        String image = getItem(position).getImage().toString();

        TextView ingre_name=(TextView)convertView.findViewById(R.id.ingredient_name_for_pantry);
        ImageView ingre_image=(ImageView)convertView.findViewById(R.id.ingredient_image_for_pantry);
        ingre_name.setText(name);
        Picasso.get()
                .load("https://spoonacular.com/cdn/ingredients_100x100/"+image)
                .fit()
                .centerInside()
                .into(ingre_image);
        return convertView;

    }
}
