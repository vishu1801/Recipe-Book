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

import com.example.myapplication.AllResponse.Similar_Recipes_response;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Similar_recipe_Adapter extends RecyclerView.Adapter<Similar_recipe_Adapter.ViewHolder> {

    Context mcontext;
    Activity mactivity;
    ArrayList<Similar_Recipes_response> similar_recipes_list;
    private OnsimilarListener onsimilarListener;

    public Similar_recipe_Adapter(@NonNull Context context, Activity activity, ArrayList<Similar_Recipes_response> data,OnsimilarListener onsimilarListener) {
        this.mcontext=context;
        this.mactivity=activity;
        this.similar_recipes_list=data;
        this.onsimilarListener=onsimilarListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.list_view_item_for_recipe,parent,false);

        return new ViewHolder(view,onsimilarListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.recipe_title.setText(similar_recipes_list.get(position).getTitle());
        Picasso.get()
                .load("https://spoonacular.com/recipeImages/"+similar_recipes_list.get(position).getId()+"-240x150."+similar_recipes_list.get(position).getImageType())
                .fit()
                .centerCrop()
                .into(holder.recipe_image);

    }

    @Override
    public int getItemCount() {
        if(similar_recipes_list.size()>0)
            return similar_recipes_list.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recipe_image;
        TextView recipe_title;
        OnsimilarListener onsimilarListener;

        public ViewHolder(@NonNull View itemView,OnsimilarListener onsimilarListener) {
            super(itemView);
            recipe_image=itemView.findViewById(R.id.image_view_for_recipe);
            recipe_title=itemView.findViewById(R.id.recipe_title);
            this.onsimilarListener=onsimilarListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onsimilarListener.Onsimilarrecipeclick(getAdapterPosition());
        }
    }

    public interface OnsimilarListener{
     void Onsimilarrecipeclick(int position);
    }
}
