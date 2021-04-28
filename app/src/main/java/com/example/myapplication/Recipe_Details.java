package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.AllResponse.Recipe_details_response.Recipe_details_Response;
import com.example.myapplication.AllResponse.Temp;
import com.example.myapplication.detailsFragment.Overview;
import com.example.myapplication.detailsFragment.Steps;
import com.example.myapplication.detailsFragment.ViewPagerAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

public class Recipe_Details extends AppCompatActivity {

    String id;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView recipe_image,add_fav;
    TabLayout tabLayout;
    ViewPager viewPager;
    Recipe_details_Response recipe_details_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__details);

        id = getIntent().getExtras().get("id").toString();

        // init
        collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);
        recipe_image = findViewById(R.id.recipe_image);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        add_fav = findViewById(R.id.add_fav);


        //Initialise Adapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.Add_fragment(new Overview(id),"overview");
        viewPagerAdapter.Add_fragment(new Steps(id),"steps");

        //set Adapter
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        //image onclick
        add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add_fav.getTag().equals("fav1")){
                    add_fav.setTag("fav2");
                    add_fav.setImageResource(R.drawable.red_heart);
                    add_favourite(true);
                    Toasty.success(getApplicationContext(),"Added to favorite",Toasty.LENGTH_SHORT,true).show();
                }else{
                    add_fav.setTag("fav1");
                    add_fav.setImageResource(R.drawable.favorite);
                    remove_favourite(true);
                    Toasty.success(getApplicationContext(),"Removed from favorite",Toasty.LENGTH_SHORT,true).show();
                }
            }
        });

        //check that already added to favourite or not
        check(id);

        //function call to get details of that recipe
        get_details(id);
    }

    private void add_favourite(Boolean t) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Favourite").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Temp temp = new Temp();
        temp.setId(Integer.parseInt(id));
        temp.setImage(recipe_details_response.getImage());
        temp.setTitle(recipe_details_response.getTitle());
        reference.push().setValue(temp);
    }

    private void remove_favourite(Boolean x){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        Query reff=databaseReference.child("Favourite").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderByChild("id").equalTo(Integer.parseInt(id));
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    snapshot1.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void check(String id){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query reff = ref.child("Favourite").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByChild("id").equalTo(Integer.parseInt(id));
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    add_fav.setTag("fav2");
                    add_fav.setImageResource(R.drawable.red_heart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void get_details(String id){
        Call<Recipe_details_Response> recipe_details_responseCall = ApiClient.getUserService().recipe_detail_response(Integer.parseInt(id),apiKey);
        recipe_details_responseCall.enqueue(new Callback<Recipe_details_Response>() {
            @Override
            public void onResponse(Call<Recipe_details_Response> call, Response<Recipe_details_Response> response) {
                collapsingToolbarLayout.setTitle(response.body().getTitle());
                Picasso.get().load(response.body().getImage()).fit().centerInside().into(recipe_image);
                recipe_details_response=response.body();
            }

            @Override
            public void onFailure(Call<Recipe_details_Response> call, Throwable t) {

            }
        });
    }
}