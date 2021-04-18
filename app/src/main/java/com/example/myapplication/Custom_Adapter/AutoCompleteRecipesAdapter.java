package com.example.myapplication.Custom_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.AllResponse.AutoComplete_recipes;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AutoCompleteRecipesAdapter extends ArrayAdapter<AutoComplete_recipes> {

    Context mcontext;
    int mresources;
    ArrayList<AutoComplete_recipes> data;
    Button temp;
    ImageView recipe_image;
    TextView recipe_title;

    public AutoCompleteRecipesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AutoComplete_recipes> objects) {
        super(context, resource, objects);
        mcontext=context;
        mresources=resource;
        data=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView = layoutInflater.inflate(mresources,parent,false);
        temp = convertView.findViewById(R.id.add_ingredient);
        temp.setVisibility(View.GONE);
        String recipe_name = getItem(position).getTitle();
        String image = getItem(position).getImageType();
        Integer id = getItem(position).getId();
        recipe_image = convertView.findViewById(R.id.ingredient_image);
        Picasso.get().load("https://spoonacular.com/recipeImages/"+id+"-90x90."+image).fit().centerCrop().into(recipe_image);
        recipe_title = convertView.findViewById(R.id.ingredient_name);
        recipe_title.setText(recipe_name);

        return convertView;
    }
}
