package com.example.myapplication.AllResponse.Random_recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Random_Recipe_response {


    @SerializedName("recipes")
    @Expose
    private List<Recipe> recipes = null;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}
