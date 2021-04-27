package com.example.myapplication.Custom_Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AllResponse.AutoComplete_recipes;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AutoComplete_recipe_Adapter extends RecyclerView.Adapter<AutoComplete_recipe_Adapter.ViewHolder> {

    Context mcontext;
    Activity mactivity;
    ArrayList<AutoComplete_recipes> autoComplete_recipes;

    public AutoComplete_recipe_Adapter(@NonNull Context context, Activity activity, ArrayList<AutoComplete_recipes> data) {
        this.mcontext=context;
        this.mactivity=activity;
        this.autoComplete_recipes=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.autocomplete_ingredient,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.recipe_title.setText(autoComplete_recipes.get(position).getTitle());
        Picasso.get()
                .load("https://spoonacular.com/recipeImages/"+autoComplete_recipes.get(position).getId()+"-90x90."+autoComplete_recipes.get(position).getImageType())
                .centerInside()
                .fit()
                .into(holder.recipe_image);

    }

    @Override
    public int getItemCount() {
        if(autoComplete_recipes.size()>0)
            return autoComplete_recipes.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView recipe_image;
        TextView recipe_title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_image = itemView.findViewById(R.id.autocomplete_recipe_image);
            recipe_title = itemView.findViewById(R.id.autocomplete_recipe_title);
        }
    }
}
