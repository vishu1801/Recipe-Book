package com.example.myapplication.API;

import com.example.myapplication.AllResponse.Ingredients_response;
import com.example.myapplication.AllResponse.AutoComplete_recipes;
import com.example.myapplication.AllResponse.Recipe_By_ingredient_Response.RecipeResponse;
import com.example.myapplication.AllResponse.Recipe_Step_Response.Recipe_steps;
import com.example.myapplication.AllResponse.Recipe_details_response.Recipe_details_Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.myapplication.API.Recipes_url.autocomplete_recipes;
import static com.example.myapplication.API.Recipes_url.get_Ingredients;
import static com.example.myapplication.API.Recipes_url.get_Recipe_by_Ingredients;
import static com.example.myapplication.API.Recipes_url.recipe_details;
import static com.example.myapplication.API.Recipes_url.recipe_steps;

public interface UserService {

    @GET(get_Ingredients)
    Call<List<Ingredients_response>> get_ingredients(@Query("apiKey") String apiKey,
                                                     @Query("query") String query,
                                                     @Query("number") int number);

    @GET(get_Recipe_by_Ingredients)
    Call<List<RecipeResponse>> get_recipe_by_Ingredients(@Query("apiKey") String apiKey,
                                                         @Query("ingredients") String ingredients,
                                                         @Query("number") int number);

    @GET(autocomplete_recipes)
    Call<List<AutoComplete_recipes>> auto_complete_recipes(@Query("apiKey") String apiKey,
                                                           @Query("query") String query,
                                                           @Query("number") int number);

    @GET(recipe_steps)
    Call<List<Recipe_steps>> recipe_step (@Path("id") int id,
                                          @Query("apiKey") String apkiKey);


    @GET(recipe_details)
    Call<Recipe_details_Response> recipe_detail_response (@Path("id") int id,
                                                          @Query("apiKey") String apkiKey);
}
