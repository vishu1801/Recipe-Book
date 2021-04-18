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
import com.example.myapplication.AllResponse.AutoComplete_ingredients;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Ingredient_lis_for_pantry_adapter extends ArrayAdapter<AutoComplete_ingredients> {
    Context mContext;
    int mResource;
    ArrayList<AutoComplete_ingredients> list_for_pantry;
    Button remove;
    public Ingredient_lis_for_pantry_adapter(@NonNull Context context, int resource, @NonNull ArrayList<AutoComplete_ingredients> objects) {
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

        remove=(Button) convertView.findViewById(R.id.remove_ingredient);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteingre(list_for_pantry.get(position).getName());
            }
        });
        return convertView;

    }

    private void deleteingre(String name){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        Query reff=databaseReference.child("Ingredients").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderByChild("name").equalTo(name);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    snapshot1.getRef().removeValue();
                }
                Toast.makeText(mContext, "Ingredient deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
