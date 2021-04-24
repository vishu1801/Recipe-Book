package com.example.myapplication.detailsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.AllResponse.Recipe_Step_Response.Recipe_steps;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class Steps extends Fragment {

    private String recipe_id;
    private ArrayList<Recipe_steps> data;
    private ListView listView;
    private Steps_listView_Adapter steps_listView_adapter;

    public Steps (String id){
        this.recipe_id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent_view = inflater.inflate(R.layout.fragment_steps, container, false);
        View root_view = inflater.inflate(R.layout.fragment_steps,null);

        //init
        data=new ArrayList<Recipe_steps>();
        listView=root_view.findViewById(R.id.steps_listview);
        steps_listView_adapter=new Steps_listView_Adapter(getContext(),R.layout.steps_recyclerview_item,data);


        //set adapter
        listView.setAdapter(steps_listView_adapter);


        Call<List<Recipe_steps>> recipe_stepsCall = ApiClient.getUserService().recipe_step(Integer.parseInt(recipe_id),apiKey);
        recipe_stepsCall.enqueue(new Callback<List<Recipe_steps>>() {
            @Override
            public void onResponse(Call<List<Recipe_steps>> call, Response<List<Recipe_steps>> response) {
                if(response.isSuccessful()){
                    data.clear();
                    data.addAll(response.body());
                    steps_listView_adapter.notifyDataSetChanged();
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