package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.RecipeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class SearchByRecipesFragment extends Fragment {
    ListView listView_for_ingredient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent_view=inflater.inflate(R.layout.recipes,container,false);
        View root_view= inflater.inflate(R.layout.recipes,null);

        String ingredient=getArguments().getString("ingredient");
        Call<List<RecipeResponse>> recipecall = ApiClient.getUserService().get_recipe_by_Ingredients(apiKey, ingredient, 10);
        recipecall.enqueue(new Callback<List<RecipeResponse>>() {
            @Override
            public void onResponse(Call<List<RecipeResponse>> call, Response<List<RecipeResponse>> response) {
                if (response.isSuccessful()) {
                    
                }
            }

            @Override
            public void onFailure(Call<List<RecipeResponse>> call, Throwable t) {

            }
        });

        return root_view;
    }

}
