package com.example.myapplication.detailsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.AllResponse.Recipe_details_response.Recipe_details_Response;
import com.example.myapplication.AllResponse.Similar_Recipes_response;
import com.example.myapplication.Custom_Adapter.Similar_recipe_Adapter;
import com.example.myapplication.R;
import com.example.myapplication.Recipe_Details;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class Overview extends Fragment implements Similar_recipe_Adapter.OnsimilarListener{

    private String recipe_id;
    private TextView description_text,ready_text,servings_text;
    private RecyclerView similar_recipes_recycler;
    private ArrayList<Similar_Recipes_response> similar_recipes_list;
    private Similar_recipe_Adapter similar_recipe_adapter;

    public Overview (String id){
        this.recipe_id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent_view=inflater.inflate(R.layout.fragment_overview, container, false);
        View root_view = inflater.inflate(R.layout.fragment_overview,null);

        init(root_view);

        get_details(recipe_id);

        get_similar_recipes(recipe_id);

        return root_view;
    }

    private void init(View root){
        description_text = root.findViewById(R.id.description_text);
        ready_text = root.findViewById(R.id.ready_text);
        servings_text = root.findViewById(R.id.servings_text);
        similar_recipes_recycler = root.findViewById(R.id.similar_recipe);
        similar_recipes_list = new ArrayList<>();

        //set Adapter and layout for similar recipes
        similar_recipe_adapter=new Similar_recipe_Adapter(getContext(),getActivity(),similar_recipes_list,Overview.this::Onsimilarrecipeclick);
        similar_recipes_recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        similar_recipes_recycler.setAdapter(similar_recipe_adapter);

    }

    private void get_details(String id){
        Call<Recipe_details_Response> recipe_details_responseCall = ApiClient.getUserService().recipe_detail_response(Integer.parseInt(id),apiKey);
        recipe_details_responseCall.enqueue(new Callback<Recipe_details_Response>() {
            @Override
            public void onResponse(retrofit2.Call<Recipe_details_Response> call, Response<Recipe_details_Response> response) {
                if(response.isSuccessful()) {
                    if(response.body()!=null) {
                        description_text.setText(Html.fromHtml(response.body().getSummary()));
                        ready_text.setText("Ready in " + response.body().getReadyInMinutes() + " minutes");
                        servings_text.setText("Servings: " + response.body().getServings());
                    }else {
                        Toasty.error(getContext(), "No details about this recipe", Toasty.LENGTH_SHORT, true).show();
                    }
                }else{
                    Toasty.error(getContext(),"Some technical issue. Try after some time.",Toasty.LENGTH_SHORT,true).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Recipe_details_Response> call, Throwable t) {

            }
        });
    }

    private void get_similar_recipes(String id){
        Call<List<Similar_Recipes_response>> similar_recipe_call = ApiClient.getUserService().get_similar_recipes(Integer.parseInt(id),apiKey,5);
        similar_recipe_call.enqueue(new Callback<List<Similar_Recipes_response>>() {
            @Override
            public void onResponse(Call<List<Similar_Recipes_response>> call, Response<List<Similar_Recipes_response>> response) {
                if(response.isSuccessful()){
                    if(response.body().isEmpty()){
                        Toasty.success(getContext(),"No Similar recipes found",Toasty.LENGTH_SHORT,true).show();
                    }else{
                        similar_recipes_list.clear();
                        similar_recipes_list.addAll(response.body());
                        similar_recipe_adapter.notifyDataSetChanged();
                    }
                }else{
                    Toasty.error(getContext(),"Some Technical issue. Try after some time.",Toasty.LENGTH_SHORT,true).show();
                }
            }

            @Override
            public void onFailure(Call<List<Similar_Recipes_response>> call, Throwable t) {

            }
        });
    }

    @Override
    public void Onsimilarrecipeclick(int position) {
        Intent intent = new Intent(getContext(), Recipe_Details.class);
        intent.putExtra("id",similar_recipes_list.get(position).getId());
        startActivity(intent);

    }
}