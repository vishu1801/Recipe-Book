package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.AllResponse.Recipe_details_response.Recipe_details_Response;
import com.example.myapplication.detailsFragment.Overview;
import com.example.myapplication.detailsFragment.Steps;
import com.example.myapplication.detailsFragment.ViewPagerAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class Recipe_Details extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView recipe_image;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__details);

        String id = getIntent().getExtras().get("id").toString();

        // init
        collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);
        recipe_image = findViewById(R.id.recipe_image);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);


        //Initialise Adapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.Add_fragment(new Overview(id),"overview");
        viewPagerAdapter.Add_fragment(new Steps(id),"steps");

        //set Adapter
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        //function call to get details of that recipe
        get_details(id);
    }

    private void get_details(String id){
        Call<Recipe_details_Response> recipe_details_responseCall = ApiClient.getUserService().recipe_detail_response(Integer.parseInt(id),apiKey);
        recipe_details_responseCall.enqueue(new Callback<Recipe_details_Response>() {
            @Override
            public void onResponse(Call<Recipe_details_Response> call, Response<Recipe_details_Response> response) {
                collapsingToolbarLayout.setTitle(response.body().getTitle());
                Picasso.get().load(response.body().getImage()).fit().centerInside().into(recipe_image);
            }

            @Override
            public void onFailure(Call<Recipe_details_Response> call, Throwable t) {

            }
        });
    }
}