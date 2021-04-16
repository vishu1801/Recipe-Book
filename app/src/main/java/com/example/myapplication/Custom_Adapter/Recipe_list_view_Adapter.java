package com.example.myapplication.Custom_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.AllResponse.RecipeResponse;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Recipe_list_view_Adapter extends ArrayAdapter<RecipeResponse> {
    Context mcontext;
    int mresource;
    ArrayList<RecipeResponse> my_list;
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
        ImageView fav1 = (ImageView) convertView.findViewById(R.id.fav1);
        fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fav1.getTag()=="fav1"){
                    fav1.setImageResource(R.drawable.red_heart);
                    fav1.setTag("fav2");
                }else{
                    fav1.setImageResource(R.drawable.favorite);
                    fav1.setTag("fav1");
                }
            }
        });
        Picasso.get().load(image).fit().centerCrop().into(imageView);
        recipe_title.setText(name);
        missed_ingredient.setText(missedIngredient+"Ingredient Missed");
        return convertView;
    }
}
