package com.example.myapplication.Custom_Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AllResponse.Ingredients_response;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AutoComplete_ingredient_adapter extends RecyclerView.Adapter<AutoComplete_ingredient_adapter.ViewHolder> {
    Context mcontext;
    Activity mactivity;
    ArrayList<Ingredients_response> autoComplete_ingredient_list;

    public AutoComplete_ingredient_adapter(@NonNull Context context, Activity activity, ArrayList<Ingredients_response> data) {
        this.mcontext=context;
        this.mactivity=activity;
        this.autoComplete_ingredient_list=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.ingredient_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ingredient_title.setText(autoComplete_ingredient_list.get(position).getName());
        Picasso.get()
                .load("https://spoonacular.com/cdn/ingredients_100x100/"+autoComplete_ingredient_list.get(position).getImage())
                .centerInside()
                .fit()
                .into(holder.ingredient_image);

        holder.add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_ingredient(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(autoComplete_ingredient_list.size()>0)
            return autoComplete_ingredient_list.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ingredient_title;
        Button add_ingredient;
        ImageView ingredient_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient_image=itemView.findViewById(R.id.ingredient_image);
            add_ingredient=itemView.findViewById(R.id.add_ingredient);
            ingredient_title=itemView.findViewById(R.id.ingredient_name);

        }
    }

    private void add_ingredient(int position){
        Ingredients_response autoCompleteingredients = new Ingredients_response();
        autoCompleteingredients.setName(autoComplete_ingredient_list.get(position).getName());
        autoCompleteingredients.setImage(autoComplete_ingredient_list.get(position).getImage());
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Ingredients").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.push().setValue(autoCompleteingredients);
        Toast.makeText(mcontext, "Ingredient Added Successfully.", Toast.LENGTH_SHORT).show();
    }
}
