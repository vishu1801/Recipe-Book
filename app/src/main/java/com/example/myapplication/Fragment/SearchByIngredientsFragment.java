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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.AllResponse.Ingredients_response;
import com.example.myapplication.Custom_Adapter.AutoComplete_ingredient_adapter;
import com.example.myapplication.Custom_Adapter.Your_ingredient_Adapter;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;
import static com.example.myapplication.R.layout;

public class SearchByIngredientsFragment extends Fragment {
    SearchView ingredient_search;
    Button view_recipe;
    String ingredients="";
    TextView your_ingredient_text;
    ArrayList<Ingredients_response> ingredients_from_database,ingredient_from_response;
    Your_ingredient_Adapter your_ingredient_adapter;
    AutoComplete_ingredient_adapter autoComplete_ingredient_adapter;
    RecyclerView recyclerView_for_ingredient_from_database,recyclerView_for_ingredient_response;
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

        ingredient_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0){
                    your_ingredient_text.setVisibility(View.GONE);
                    recyclerView_for_ingredient_from_database.setVisibility(View.GONE);
                    recyclerView_for_ingredient_response.setVisibility(View.VISIBLE);
                    auto_complete_ingredient(newText);
                }else{
                    your_ingredient_text.setVisibility(View.VISIBLE);
                    recyclerView_for_ingredient_from_database.setVisibility(View.VISIBLE);
                    recyclerView_for_ingredient_response.setVisibility(View.GONE);
                }
                return true;
            }
        });


        return root;
    }

    private void init(View root_v){
        your_ingredient_text = root_v.findViewById(R.id.your_ingredient_text);
        view_recipe = (Button) root_v.findViewById(R.id.view_recipe);
        ingredients_from_database = new ArrayList<Ingredients_response>();
        your_ingredient_adapter = new Your_ingredient_Adapter(getContext(),getActivity(),ingredients_from_database);
        recyclerView_for_ingredient_from_database = root_v.findViewById(R.id.your_ingredient);
        ingredient_search = root_v.findViewById(R.id.search_view_ingredient);
        ingredient_from_response = new ArrayList<>();
        recyclerView_for_ingredient_response = root_v.findViewById(R.id.ingredient_response);
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

    private void auto_complete_ingredient(String newText){
        Call<List<Ingredients_response>> ingredient_call = ApiClient.getUserService().get_ingredients(apiKey,newText,20);
        ingredient_call.enqueue(new Callback<List<Ingredients_response>>() {
            @Override
            public void onResponse(Call<List<Ingredients_response>> call, Response<List<Ingredients_response>> response) {
                if(response.isSuccessful()){
                    ingredient_from_response.clear();
                    ingredient_from_response.addAll(response.body());
                    autoComplete_ingredient_adapter=new AutoComplete_ingredient_adapter(getContext(),getActivity(),ingredient_from_response);
                    recyclerView_for_ingredient_response.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView_for_ingredient_response.setAdapter(autoComplete_ingredient_adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Ingredients_response>> call, Throwable t) {

            }
        });
    }


}
