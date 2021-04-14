package com.example.myapplication.API;

public interface Recipes_url {
    public String Base_Url = "https://api.spoonacular.com";
    public String apiKey = "4174c86c3bbd43b4a0962ce6cca21a32";

    public String get_Ingredients = Base_Url+"/food/ingredients/autocomplete";
    public String get_Recipe_by_Ingredients = Base_Url + "/recipes/findByIngredients";
    public String get_recipe_search=Base_Url + "/recipes/autocomplete";

}
