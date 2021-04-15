package com.example.myapplication.Custom_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.UserResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Ingredient_list_for_search_adapter extends ArrayAdapter<UserResponse> {

    private Context mcontext;
    int mResource;
    ArrayList<UserResponse> list;
    DatabaseReference reference;
    public Ingredient_list_for_search_adapter(@NonNull Context context, int resource, @NonNull ArrayList<UserResponse> objects) {
        super(context, resource, objects);
        mcontext=context;
        mResource=resource;
        list=objects;
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
        Button add_ingredient = convertView.findViewById(R.id.add_ingredient);
        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserResponse userResponse = new UserResponse();
                userResponse.setName(list.get(position).getName());
                userResponse.setImage(list.get(position).getImage());
                reference= FirebaseDatabase.getInstance().getReference().child("Ingredients").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                reference.push().setValue(userResponse);
                Toast.makeText(mcontext, "Ingredient Added Successfully.", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}


