package com.example.myapplication.API;

import com.example.myapplication.AllResponse.RecipeResponse;
import com.example.myapplication.AllResponse.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.myapplication.API.Recipes_url.get_Ingredients;
import static com.example.myapplication.API.Recipes_url.get_Recipe_by_Ingredients;

public interface UserService {

    @GET(get_Ingredients)
    Call<List<UserResponse>> get_ingredients(@Query("apiKey") String apiKey,
                                             @Query("query") String query,
                                             @Query("number") int number);

    @GET(get_Recipe_by_Ingredients)
    Call<List<RecipeResponse>> get_recipe_by_Ingredients(@Query("apiKey") String apiKey,
                                                         @Query("ingredients") String ingredients,
                                                         @Query("number") int number);


}
