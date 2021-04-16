package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.AllResponse.MissedIngredient;
import com.example.myapplication.AllResponse.RecipeResponse;
import com.example.myapplication.AllResponse.UnusedIngredient;
import com.example.myapplication.AllResponse.UsedIngredient;
import com.example.myapplication.Custom_Adapter.Recipe_list_view_Adapter;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    ListView listView;
    Recipe_list_view_Adapter adapter;
    ArrayList<RecipeResponse> mylist;
    DatabaseReference reff;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent_view = inflater.inflate(R.layout.fragment_fav,container,false);
        View root_view = inflater.inflate(R.layout.fragment_fav,null);

        mylist=new ArrayList<RecipeResponse>();
        listView= root_view.findViewById(R.id.fav_list_view);
        adapter=new Recipe_list_view_Adapter(getActivity(),R.layout.list_view_item_for_recipe,mylist);
        listView.setAdapter(adapter);
        reff = FirebaseDatabase.getInstance().getReference().child("Favorites").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Integer id = ((Long) snapshot1.child("id").getValue()).intValue();
                        String image = snapshot1.child("image").getValue().toString();
                        String imagetype = snapshot1.child("imageType").getValue().toString();
                        Integer likes = ((Long) snapshot1.child("likes").getValue()).intValue();
                        Integer missedcount = ((Long) snapshot1.child("missedIngredientCount").getValue()).intValue();
                        List<MissedIngredient> missedingredient = (List) snapshot1.child("missedIngredients").getValue();
                        String title = snapshot1.child("title").getValue().toString();
                        List<UnusedIngredient> unused = (List) snapshot1.child("unusedIngredients").getValue();
                        Integer usedcount = ((Long) snapshot1.child("usedIngredientCount").getValue()).intValue();
                        List<UsedIngredient> used = (List) snapshot1.child("usedIngredients").getValue();

                        RecipeResponse recipeResponse = new RecipeResponse();
                        recipeResponse.setId(id);
                        recipeResponse.setImage(image);
                        recipeResponse.setImageType(imagetype);
                        recipeResponse.setLikes(likes);
                        recipeResponse.setMissedIngredientCount(missedcount);
                        recipeResponse.setMissedIngredients(missedingredient);
                        recipeResponse.setTitle(title);
                        recipeResponse.setUnusedIngredients(unused);
                        recipeResponse.setUsedIngredientCount(usedcount);
                        recipeResponse.setUsedIngredients(used);

                        mylist.add(recipeResponse);


                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root_view;
    }
}
