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
import com.example.myapplication.AllResponse.RecipeResponse;
import com.example.myapplication.Custom_Adapter.Recipe_list_view_Adapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class SearchByRecipesFragment extends Fragment {
    ListView listView_for_ingredient;
    Recipe_list_view_Adapter recipe_list_view_adapter;
    ArrayList<RecipeResponse> mylist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent_view=inflater.inflate(R.layout.recipes,container,false);
        View root_view= inflater.inflate(R.layout.recipes,null);

        String ingredient=getArguments().getString("ingredient");
        listView_for_ingredient = (ListView) root_view.findViewById(R.id.ingredient_related_recipe);
        mylist=new ArrayList<RecipeResponse>();
        recipe_list_view_adapter=new Recipe_list_view_Adapter(getActivity(),R.layout.list_view_item_for_recipe,mylist);
        listView_for_ingredient.setAdapter(recipe_list_view_adapter);

        Call<List<RecipeResponse>> recipecall = ApiClient.getUserService().get_recipe_by_Ingredients(apiKey, ingredient, 10);
        recipecall.enqueue(new Callback<List<RecipeResponse>>() {
            @Override
            public void onResponse(Call<List<RecipeResponse>> call, Response<List<RecipeResponse>> response) {
                if (response.isSuccessful()) {
                    mylist.clear();
                    mylist.addAll(response.body());
                }
                recipe_list_view_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RecipeResponse>> call, Throwable t) {

            }
        });

        return root_view;
    }

}
