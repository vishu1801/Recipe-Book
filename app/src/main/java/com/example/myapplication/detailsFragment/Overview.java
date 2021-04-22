package com.example.myapplication.detailsFragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.AllResponse.Recipe_details_response.Recipe_details_Response;
import com.example.myapplication.R;

import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class Overview extends Fragment {

    private String recipe_id;
    private TextView description_text,ready_text,servings_text;
    public Overview (String id){
        this.recipe_id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent_view=inflater.inflate(R.layout.fragment_overview, container, false);
        View root_view = inflater.inflate(R.layout.fragment_overview,null);

        //init
        description_text = root_view.findViewById(R.id.description_text);
        ready_text = root_view.findViewById(R.id.ready_text);
        servings_text = root_view.findViewById(R.id.servings_text);


        get_details(recipe_id);


        return root_view;
    }
    private void get_details(String id){
        retrofit2.Call<Recipe_details_Response> recipe_details_responseCall = ApiClient.getUserService().recipe_detail_response(Integer.parseInt(id),apiKey);
        recipe_details_responseCall.enqueue(new Callback<Recipe_details_Response>() {
            @Override
            public void onResponse(retrofit2.Call<Recipe_details_Response> call, Response<Recipe_details_Response> response) {
                description_text.setText(Html.fromHtml(response.body().getSummary()));
                ready_text.setText("Ready in " + response.body().getReadyInMinutes()+" minutes");
                servings_text.setText("Servings: "+ response.body().getServings());

            }

            @Override
            public void onFailure(retrofit2.Call<Recipe_details_Response> call, Throwable t) {

            }
        });
    }
}