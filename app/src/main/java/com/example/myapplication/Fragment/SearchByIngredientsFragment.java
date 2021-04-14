package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.Custom_Adapter.Ingredient_list_for_search_adapter;
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
import static com.example.myapplication.R.layout;

public class SearchByIngredientsFragment extends Fragment {
    SearchView searchView;
    ListView listView_for_search;
    ArrayList<UserResponse> mylist_for_search;
    Ingredient_list_for_search_adapter adapter_for_search;
    Button view_recipe;
    DatabaseReference ref;
    TextView your_ingredient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root_view = inflater.inflate(layout.fragment_search,container,false);
        View root=inflater.inflate(R.layout.fragment_search,null);

        searchView = (SearchView) root.findViewById(R.id.search_view);
        view_recipe = (Button) root.findViewById(R.id.view_recipe);
        listView_for_search = root.findViewById(R.id.list_item_for_search);
        your_ingredient = root.findViewById(R.id.your_ingredient_text);
        mylist_for_search=new ArrayList<UserResponse>();
        adapter_for_search=new Ingredient_list_for_search_adapter(getActivity(), R.layout.ingredient_list_item,mylist_for_search);
        listView_for_search.setAdapter(adapter_for_search);
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
                    mylist_for_search.clear();
                    mylist_for_search.addAll(response.body());
                    if(mylist_for_search.isEmpty()){
                        view_recipe.setVisibility(VISIBLE);
                        your_ingredient.setVisibility(VISIBLE);
                    }else{
                        view_recipe.setVisibility(INVISIBLE);
                        your_ingredient.setVisibility(INVISIBLE);
                    }
                    adapter_for_search.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Check Your Internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
