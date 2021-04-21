package com.example.myapplication.detailsFragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.AllResponse.Recipe_details_response.Recipe_details_Response;
import com.example.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class Fragment_overview extends Fragment {
    View view,root_view;
    TextView description_text;
    public Fragment_overview() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_overview,container,false);
        root_view = inflater.inflate(R.layout.fragment_overview,null);
        String id=getArguments().getString("id");
        //init
        description_text=root_view.findViewById(R.id.description_text);

        get_details(id);
        return root_view;
    }
    private void get_details(String id){
        Call<Recipe_details_Response> recipe_details_responseCall = ApiClient.getUserService().recipe_detail_response(Integer.parseInt(id),apiKey);
        recipe_details_responseCall.enqueue(new Callback<Recipe_details_Response>() {
            @Override
            public void onResponse(Call<Recipe_details_Response> call, Response<Recipe_details_Response> response) {
                description_text.setText(Html.fromHtml(response.body().getSummary()));
            }

            @Override
            public void onFailure(Call<Recipe_details_Response> call, Throwable t) {

            }
        });
    }
}
