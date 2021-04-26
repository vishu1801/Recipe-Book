package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AllResponse.Ingredients_response;
import com.example.myapplication.Custom_Adapter.Your_ingredient_Adapter;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.myapplication.R.layout;

public class SearchByIngredientsFragment extends Fragment {
    Button view_recipe;
    String ingredients="";
    TextView your_ingredient;
    ArrayList<Ingredients_response> ingredients_from_database;
    Your_ingredient_Adapter your_ingredient_adapter;
    RecyclerView recyclerView_for_ingredient_from_database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root_view = inflater.inflate(layout.fragment_search,container,false);
        View root=inflater.inflate(R.layout.fragment_search,null);

        init(root);
        your_ingredient_list();

        recyclerView_for_ingredient_from_database.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView_for_ingredient_from_database.setAdapter(your_ingredient_adapter);

        view_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_related_recipe();
            }
        });

        return root;
    }

    private void init(View root_v){
        your_ingredient = root_v.findViewById(R.id.your_ingredient_text);
        view_recipe = (Button) root_v.findViewById(R.id.view_recipe);
        ingredients_from_database = new ArrayList<Ingredients_response>();
        your_ingredient_adapter = new Your_ingredient_Adapter(getContext(),getActivity(),ingredients_from_database);
        recyclerView_for_ingredient_from_database = root_v.findViewById(R.id.your_ingredient);
    }

    private void your_ingredient_list(){
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Ingredients").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null){
                    ingredients_from_database.clear();
                    for(DataSnapshot snap : snapshot.getChildren()){

                        Ingredients_response ingredients_response = new Ingredients_response();
                        ingredients_response.setName(snap.child("name").getValue().toString());
                        ingredients_response.setImage(snap.child("image").getValue().toString());

                        ingredients_from_database.add(ingredients_response);
                    }
                    your_ingredient_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void  view_related_recipe(){

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Ingredients").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ingredients=ingredients+dataSnapshot.child("name").getValue().toString()+",";
                }
                transfer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void transfer(){
        if(ingredients==""){
            Toast.makeText(getContext(), "sorry", Toast.LENGTH_SHORT).show();
        }else {
            Bundle args = new Bundle();
            args.putString("ingredient", ingredients);
            SearchByRecipesFragment fragment = new SearchByRecipesFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragment.setArguments(args);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_nav, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


}
