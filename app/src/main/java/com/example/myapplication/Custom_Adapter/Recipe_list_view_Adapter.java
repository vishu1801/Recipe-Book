package com.example.myapplication.Custom_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.AllResponse.Recipe_By_ingredient_Response.RecipeResponse;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Recipe_list_view_Adapter extends ArrayAdapter<RecipeResponse> {
    Context mcontext;
    int mresource;
    ArrayList<RecipeResponse> my_list;
    ImageView fav1;
    DatabaseReference reference;
    public Recipe_list_view_Adapter(@NonNull Context context, int resource, @NonNull ArrayList<RecipeResponse> objects) {
        super(context, resource, objects);
        my_list=objects;
        mcontext=context;
        mresource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView=layoutInflater.inflate(mresource,parent,false);



        String name=getItem(position).getTitle();
        String image=getItem(position).getImage();
        Integer missedIngredient=getItem(position).getMissedIngredientCount();
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view_for_recipe);
        TextView recipe_title= (TextView) convertView.findViewById(R.id.recipe_title);
        TextView missed_ingredient = (TextView) convertView.findViewById(R.id.missed_ingredient);
        fav1 = (ImageView) convertView.findViewById(R.id.fav1);
        fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fav1.getTag()=="fav1") {
                    fav1.setImageResource(R.drawable.red_heart);
                    add_to_favorite(position);
                }else{
                    fav1.setImageResource(R.drawable.favorite);
                    remove_from_favorite(position);
                }
            }
        });
        Picasso.get().load(image).fit().centerCrop().into(imageView);
        recipe_title.setText(name);
        missed_ingredient.setText(missedIngredient+"Ingredient Missed");
        return convertView;
    }

    private void remove_from_favorite(int pos){
        fav1.setImageResource(R.drawable.favorite);
        fav1.setTag("fav1");
    }

    private void add_to_favorite(int position){
        fav1.setImageResource(R.drawable.red_heart);
        fav1.setTag("fav2");
        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.setId(my_list.get(position).getId());
        recipeResponse.setTitle(my_list.get(position).getTitle());
        recipeResponse.setImage(my_list.get(position).getImage());
        recipeResponse.setImageType(my_list.get(position).getImageType());
        recipeResponse.setImage(my_list.get(position).getImage());
        recipeResponse.setUsedIngredientCount(my_list.get(position).getUsedIngredientCount());
        recipeResponse.setMissedIngredientCount(my_list.get(position).getMissedIngredientCount());
        recipeResponse.setMissedIngredients(my_list.get(position).getMissedIngredients());
        recipeResponse.setUsedIngredients(my_list.get(position).getUsedIngredients());
        recipeResponse.setUnusedIngredients(my_list.get(position).getUnusedIngredients());
        recipeResponse.setLikes(my_list.get(position).getLikes());

        reference = FirebaseDatabase.getInstance().getReference().child("Favorites").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.push().setValue(recipeResponse);

        Toast.makeText(mcontext, "Added To your Favorite", Toast.LENGTH_SHORT).show();

    }

}
