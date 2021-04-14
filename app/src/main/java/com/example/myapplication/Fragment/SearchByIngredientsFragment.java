package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.Ingredient_list_adapter;
import com.example.myapplication.R;
import com.example.myapplication.UserResponse;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.myapplication.API.Recipes_url.apiKey;
import static com.example.myapplication.R.id;
import static com.example.myapplication.R.layout;

public class SearchByIngredientsFragment extends Fragment {
    SearchView searchView;
    ListView listView;
    ArrayList<UserResponse> mylist;
    Ingredient_list_adapter adapter;
    Button add_ingredient,view_recipe;
    DatabaseReference reference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root_view = inflater.inflate(layout.fragment_search,container,false);
        View root=inflater.inflate(R.layout.fragment_search,null);

        searchView = (SearchView) root.findViewById(id.search_view);
        add_ingredient = (Button) root.findViewById(id.add_ingredients);
        view_recipe = (Button) root.findViewById(id.view_recipe);
        listView = root.findViewById(id.list_item);
        mylist=new ArrayList<UserResponse>();


        adapter=new Ingredient_list_adapter (getActivity(), R.layout.ingredient_list_item,mylist);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                get_Ingredients(newText);
//                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return root;
    }
    public void get_Ingredients(String incomplete_ingredient){
        Call<List<UserResponse>> userResponseCall = ApiClient.getUserService().get_ingredients(apiKey,incomplete_ingredient,10);
        userResponseCall.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {

                if(response.isSuccessful()) {
                    mylist.clear();
                    mylist.addAll(response.body());
                    if(mylist.isEmpty()){
                        add_ingredient.setVisibility(INVISIBLE);
                        view_recipe.setVisibility(VISIBLE);
                    }else{
                        add_ingredient.setVisibility(VISIBLE);
                        view_recipe.setVisibility(INVISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Check Your Internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
