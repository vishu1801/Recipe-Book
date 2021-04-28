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

import com.example.myapplication.AllResponse.Temp;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Fav_Adapter extends RecyclerView.Adapter<Fav_Adapter.ViewHolder>{
    Context mcontext;
    Activity mactivity;
    ArrayList<Temp> recipes_list;
    private Onfav_Listener onfav_listener;

    public Fav_Adapter(@NonNull Context context, Activity activity, ArrayList<Temp> data, Onfav_Listener onfav_listener) {
        this.mcontext=context;
        this.mactivity=activity;
        this.recipes_list=data;
        this.onfav_listener=onfav_listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.list_view_item_for_recipe,parent,false);

        return new ViewHolder(view,onfav_listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get()
                .load(recipes_list.get(position).getImage())
                .centerCrop()
                .fit()
                .into(holder.recipe_image);
        holder.recipe_title.setText(recipes_list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if(recipes_list.size()>0)
            return recipes_list.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView recipe_image;
        TextView recipe_title;
        Onfav_Listener onfav_listener;

        public ViewHolder(@NonNull View itemView, Onfav_Listener onfav_listener) {
            super(itemView);
            recipe_image=itemView.findViewById(R.id.image_view_for_recipe);
            recipe_title=itemView.findViewById(R.id.recipe_title);
            this.onfav_listener=onfav_listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onfav_listener.Onfavclick(getAdapterPosition());
        }
    }

    public interface Onfav_Listener{
        void Onfavclick(int position);
    }

}
