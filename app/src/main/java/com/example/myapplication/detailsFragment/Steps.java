package com.example.myapplication.detailsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.AllResponse.Recipe_Step_Response.Recipe_steps;
import com.example.myapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class Steps extends Fragment {

    private String recipe_id;
    private RecyclerView recyclerView;
    private Steps_recycler_Adapter recycler_adapter;
    private Recipe_steps data;

    public Steps (String id){
        this.recipe_id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent_view = inflater.inflate(R.layout.fragment_steps, container, false);
        View root_view = inflater.inflate(R.layout.fragment_steps,null);

        Toast.makeText(getContext(), recipe_id, Toast.LENGTH_SHORT).show();
        //init
        recyclerView=root_view.findViewById(R.id.steps_recyclerview);
        data=new Recipe_steps();



        Call<List<Recipe_steps>> recipe_stepsCall = ApiClient.getUserService().recipe_step(Integer.parseInt(recipe_id),apiKey);
        recipe_stepsCall.enqueue(new Callback<List<Recipe_steps>>() {
            @Override
            public void onResponse(Call<List<Recipe_steps>> call, Response<List<Recipe_steps>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "co" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe_steps>> call, Throwable t) {
                Toast.makeText(getContext(), "Some Internet issues"+t, Toast.LENGTH_SHORT).show();
            }
        });

        return root_view;
    }
}