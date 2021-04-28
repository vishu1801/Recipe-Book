package com.example.myapplication.Fragment;

import android.content.Intent;
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
import com.example.myapplication.AllResponse.Random_recipe.Random_Recipe_response;
import com.example.myapplication.AllResponse.Recipe_By_ingredient_Response.RecipeResponse;
import com.example.myapplication.Custom_Adapter.AutoComplete_recipe_Adapter;
import com.example.myapplication.Custom_Adapter.Random_recipe_Adapter;
import com.example.myapplication.Custom_Adapter.Recipes_Adapter;
import com.example.myapplication.R;
import com.example.myapplication.Recipe_Details;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class SearchByRecipesFragment extends Fragment implements Recipes_Adapter.onRecipeListener,Random_recipe_Adapter.OnRandomRecipeListner,AutoComplete_recipe_Adapter.OnAutoCompleteListener {

    String ingredient;
    SearchView recipe_search;
    RecyclerView autocomplete_recipe,random_recipes,your_ingredient_recipes;
    ArrayList<AutoComplete_recipes> autoComplete_recipes_list;
    ArrayList<RecipeResponse> recipeResponse;
    ArrayList<Random_Recipe_response> random_recipe_response;
    AutoComplete_recipe_Adapter autoComplete_recipe_adapter;
    Recipes_Adapter recipes_adapter;
    Random_recipe_Adapter random_recipe_adapter;
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
            textview.setText("Some Random Recipes");
            recipe_search.setVisibility(View.VISIBLE);
            random_recipes();
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
                        autocomplete_recipe.setVisibility(View.VISIBLE);
                        get_autocomplete_recipe(newText);
                    }else{
                        textview.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        autocomplete_recipe.setVisibility(View.GONE);
                    }
                    return true;
                }
            });

        }
        else {
            textview.setText("Recipes By Your Ingredients");
            your_ingredient_recipes.setVisibility(View.VISIBLE);
            get_recipes(ingredient);
        }

        return root_view;
    }

    private void init(View root){
        recipe_search = root.findViewById(R.id.recipe_searchview);
        autocomplete_recipe=root.findViewById(R.id.recipe_search);
        textview=root.findViewById(R.id.text_view);
        line=root.findViewById(R.id.line);
        autoComplete_recipes_list=new ArrayList<>();
        your_ingredient_recipes=root.findViewById(R.id.your_ingredient_recipe);
        recipeResponse=new ArrayList<>();
        random_recipes=root.findViewById(R.id.random_recipe_recycler);
        random_recipe_response=new ArrayList<Random_Recipe_response>();


        //setting adapter

        autoComplete_recipe_adapter=new AutoComplete_recipe_Adapter(getContext(),getActivity(),autoComplete_recipes_list,SearchByRecipesFragment.this::OnAutoCompleteclick);
        autocomplete_recipe.setLayoutManager(new LinearLayoutManager(getContext()));
        autocomplete_recipe.setAdapter(autoComplete_recipe_adapter);

        recipes_adapter=new Recipes_Adapter(getContext(),getActivity(),recipeResponse,SearchByRecipesFragment.this::onRecipeClick);
        your_ingredient_recipes.setLayoutManager(new LinearLayoutManager(getContext()));
        your_ingredient_recipes.setAdapter(recipes_adapter);

        random_recipe_adapter=new Random_recipe_Adapter(getContext(),getActivity(),random_recipe_response,SearchByRecipesFragment.this::OnRandomRecipeclick);
        random_recipes.setLayoutManager(new LinearLayoutManager(getContext()));
        random_recipes.setAdapter(random_recipe_adapter);


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
                        autoComplete_recipe_adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AutoComplete_recipes>> call, Throwable t) {

            }
        });
    }

    private void get_recipes(String ingredients){
        Call<List<RecipeResponse>> recipe_response_call = ApiClient.getUserService().get_recipe_by_Ingredients(apiKey,ingredients,20);
        recipe_response_call.enqueue(new Callback<List<RecipeResponse>>() {
            @Override
            public void onResponse(Call<List<RecipeResponse>> call, Response<List<RecipeResponse>> response) {
                if(response.isSuccessful()){
                    if(response.body().size()>0){
                        recipeResponse.clear();
                        recipeResponse.addAll(response.body());
                        recipes_adapter.notifyDataSetChanged();
                    }else{
                        Toasty.info(getContext(),"Sorry! No recipe Found by your ingredient",Toasty.LENGTH_LONG,true).show();
                    }
                }else{
                    Toasty.error(getContext(),"Some Techinal issue Please Try after some time.",Toasty.LENGTH_LONG,true).show();
                }
            }

            @Override
            public void onFailure(Call<List<RecipeResponse>> call, Throwable t) {

            }
        });
    }

    private void random_recipes(){
        Call<Random_Recipe_response> random_recipe_call = ApiClient.getUserService().get_random_recipes(apiKey,"vegetarian,lunch,dinner",20);
        random_recipe_call.enqueue(new Callback<Random_Recipe_response>() {
            @Override
            public void onResponse(Call<Random_Recipe_response> call, Response<Random_Recipe_response> response) {
                if(response.isSuccessful()){
                    if (response.body().equals("")){
                        Toasty.error(getContext(),"No Random recipes Found",Toasty.LENGTH_SHORT,true).show();
                    }else{
                        random_recipe_response.clear();
                        random_recipe_response.add(response.body());
                        random_recipe_adapter.notifyDataSetChanged();
                    }
                }else{
                    Toasty.error(getContext(),"Some Techinal issue Please Try after some time.",Toasty.LENGTH_LONG,true).show();
                }
            }

            @Override
            public void onFailure(Call <Random_Recipe_response> call, Throwable t) {

            }
        });
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(getContext(), Recipe_Details.class);
        intent.putExtra("id",recipeResponse.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void OnRandomRecipeclick(int position) {
        Intent intent = new Intent(getContext(), Recipe_Details.class);
        intent.putExtra("id",random_recipe_response.get(0).getRecipes().get(position).getId());
        startActivity(intent);
    }

    @Override
    public void OnAutoCompleteclick(int position) {
        Intent intent = new Intent(getContext(), Recipe_Details.class);
        intent.putExtra("id",autoComplete_recipes_list.get(position).getId());
        startActivity(intent);
    }
}
