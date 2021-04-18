package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.AllResponse.Recipe_By_ingredient_Response.RecipeResponse;
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
    TextView textView;
    SearchView searchView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent_view=inflater.inflate(R.layout.recipes,container,false);
        View root_view= inflater.inflate(R.layout.recipes,null);

        String ingredient=getArguments().getString("ingredient");
        textView = root_view.findViewById(R.id.text_view);
        if(ingredient == "abc"){
            searchView = root_view.findViewById(R.id.search_view_recipe);
            searchView.setVisibility(View.VISIBLE);
            textView.setText("Some Random Recipes");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    get_recipes(newText);

                    return false;
                }
            });
            Toast.makeText(getActivity(), "Get Random recipe", Toast.LENGTH_SHORT).show();
        }else {
            textView.setText("Recipes By Your Ingredients");
            listView_for_ingredient = (ListView) root_view.findViewById(R.id.ingredient_related_recipe);
            mylist = new ArrayList<RecipeResponse>();
            recipe_list_view_adapter = new Recipe_list_view_Adapter(getActivity(), R.layout.list_view_item_for_recipe, mylist);
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
        }

        return root_view;
    }

    private void get_recipes(String newText){

    }

}
