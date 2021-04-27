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

import com.example.myapplication.AllResponse.Recipe_By_ingredient_Response.RecipeResponse;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Recipes_Adapter extends RecyclerView.Adapter<Recipes_Adapter.ViewHolder> {

    Context mcontext;
    Activity mactivity;
    ArrayList<RecipeResponse> recipes_list;
    private onRecipeListener onRecipeListener;

    public Recipes_Adapter(@NonNull Context context, Activity activity, ArrayList<RecipeResponse> data,onRecipeListener onRecipeListener) {
        this.mcontext=context;
        this.mactivity=activity;
        this.recipes_list=data;
        this.onRecipeListener=onRecipeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.list_view_item_for_recipe,parent,false);

        return new ViewHolder(view,onRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get()
                .load(recipes_list.get(position).getImage())
                .centerCrop()
                .fit()
                .into(holder.recipe_image);
        holder.recipe_title.setText(recipes_list.get(position).getTitle());
        holder.missed_ingr.setText(recipes_list.get(position).getMissedIngredientCount()+" Missed Ingredients");

    }

    @Override
    public int getItemCount() {
        if(recipes_list.size()>0)
            return recipes_list.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView recipe_image,add_fav;
        TextView recipe_title,missed_ingr;
        onRecipeListener onRecipeListener;

        public ViewHolder(@NonNull View itemView, onRecipeListener onRecipeListener) {
            super(itemView);
            recipe_image=itemView.findViewById(R.id.image_view_for_recipe);
            recipe_title=itemView.findViewById(R.id.recipe_title);
            missed_ingr=itemView.findViewById(R.id.missed_ingredient);
            this.onRecipeListener=onRecipeListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }

    public interface onRecipeListener{
        void onRecipeClick(int position);
    }
}
