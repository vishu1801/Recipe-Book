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

import com.example.myapplication.AllResponse.Random_recipe.Random_Recipe_response;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Random_recipe_Adapter extends RecyclerView.Adapter<Random_recipe_Adapter.ViewHolder> {

    Context mcontext;
    Activity mactivity;
    ArrayList<Random_Recipe_response> random_recipes_list;

    public Random_recipe_Adapter(@NonNull Context context, Activity activity, ArrayList<Random_Recipe_response> data) {
        this.mcontext=context;
        this.mactivity=activity;
        this.random_recipes_list=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.list_view_item_for_recipe,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get()
                .load(random_recipes_list.get(0).getRecipes().get(position).getImage())
                .centerCrop()
                .fit()
                .into(holder.recipe_image);
        holder.recipe_title.setText(random_recipes_list.get(0).getRecipes().get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        if(random_recipes_list.size()>0)
            return random_recipes_list.get(0).getRecipes().size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView recipe_image;
        TextView recipe_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_image=itemView.findViewById(R.id.image_view_for_recipe);
            recipe_title=itemView.findViewById(R.id.recipe_title);
        }
    }
}
