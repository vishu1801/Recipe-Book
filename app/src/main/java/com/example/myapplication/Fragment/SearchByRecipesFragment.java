package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.AllResponse.AutoComplete_recipes;
import com.example.myapplication.Custom_Adapter.AutoComplete_recipe_Adapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class SearchByRecipesFragment extends Fragment {

    String ingredient;
    SearchView recipe_search;
    RecyclerView autocomplete_recipe;
    ArrayList<AutoComplete_recipes> autoComplete_recipes_list;
    AutoComplete_recipe_Adapter autoComplete_recipe_adapter;
    TextView textview;
    View line;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent_view=inflater.inflate(R.layout.recipes,container,false);
        View root_view= inflater.inflate(R.layout.recipes,null);

        ingredient=getArguments().getString("ingredient");
        init(root_view);

        if(ingredient == "abc"){
            recipe_search.setVisibility(View.VISIBLE);
            autocomplete_recipe.setVisibility(View.VISIBLE);
            recipe_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(newText.length()>0){
                        textview.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        get_autocomplete_recipe(newText);
                    }else{
                        textview.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
            });

        }
        else {
        }

        return root_view;
    }

    private void init(View root){
        recipe_search = root.findViewById(R.id.recipe_searchview);
        autocomplete_recipe=root.findViewById(R.id.recipe_search);
        textview=root.findViewById(R.id.text_view);
        line=root.findViewById(R.id.line);
        autoComplete_recipes_list=new ArrayList<>();
    }

    private void get_autocomplete_recipe(String newText){
        Call<List<AutoComplete_recipes>> autocomplete_recipe_call = ApiClient.getUserService().auto_complete_recipes(apiKey,newText,20);
        autocomplete_recipe_call.enqueue(new Callback<List<AutoComplete_recipes>>() {
            @Override
            public void onResponse(Call<List<AutoComplete_recipes>> call, Response<List<AutoComplete_recipes>> response) {
                if(response.isSuccessful()){
                    if (response.body().isEmpty()){
                        Toasty.error(getContext(),"No Recipe Found",Toasty.LENGTH_LONG,true).show();
                    }else{
                        autoComplete_recipes_list.clear();
                        autoComplete_recipes_list.addAll(response.body());
                        autoComplete_recipe_adapter=new AutoComplete_recipe_Adapter(getContext(),getActivity(),autoComplete_recipes_list);
                        autocomplete_recipe.setLayoutManager(new LinearLayoutManager(getContext()));
                        autocomplete_recipe.setAdapter(autoComplete_recipe_adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AutoComplete_recipes>> call, Throwable t) {

            }
        });
    }
}
