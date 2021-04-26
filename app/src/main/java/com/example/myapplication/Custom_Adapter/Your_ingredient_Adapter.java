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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Your_ingredient_Adapter extends RecyclerView.Adapter<Your_ingredient_Adapter.ViewHolder> {

    Context mcontext;
    Activity mactivity;
    ArrayList <Ingredients_response> ingredient_list;

    public Your_ingredient_Adapter(@NonNull Context context, Activity activity, ArrayList<Ingredients_response> data) {
        this.mcontext=context;
        this.mactivity=activity;
        this.ingredient_list=data;
    }

    @NonNull
    @Override
    public Your_ingredient_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.ingredient_item_list_for_pantry,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Your_ingredient_Adapter.ViewHolder holder, int position) {

        holder.ingredient_name.setText(ingredient_list.get(position).getName());
        Picasso.get()
                .load("https://spoonacular.com/cdn/ingredients_100x100/"+ingredient_list.get(position).getImage())
                .fit()
                .centerInside()
                .into(holder.imageView);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteingre(ingredient_list.get(position).getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        if(ingredient_list.size()>0){
            return ingredient_list.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredient_name;
        ImageView imageView;
        Button remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ingredient_image_for_pantry);
            ingredient_name = (TextView) itemView.findViewById(R.id.ingredient_name_for_pantry);
            remove = itemView.findViewById(R.id.remove_ingredient);

        }
    }

    private void deleteingre(String name){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        Query reff=databaseReference.child("Ingredients").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderByChild("name").equalTo(name);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    snapshot1.getRef().removeValue();
                }
                Toast.makeText(mcontext, "Ingredient deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
