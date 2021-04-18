package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DecideFragment extends AppCompatActivity {
    String ingredients="abc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide_fragment);
        BottomNavigationView bottomnav = findViewById(R.id.bottom_nav);
        bottomnav.setOnNavigationItemSelectedListener(navlistner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_nav,new SearchByIngredientsFragment()).commit();
    }
    BottomNavigationView.OnNavigationItemSelectedListener navlistner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedfragment=null;
                switch (item.getItemId()) {
                    case R.id.nav_search_by_ingredient:
                        selectedfragment = new SearchByIngredientsFragment();
                        break;
                    case R.id.nav_search_by_recipes:
                        selectedfragment = new SearchByRecipesFragment();
                        Bundle args = new Bundle();
                        args.putString("ingredient", ingredients);
                        selectedfragment.setArguments(args);
                        break;
                    case R.id.nav_fav:
                        selectedfragment = new FavoriteFragment();
                        break;
                }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_nav,selectedfragment).commit();
            return true;
        }
    };
}