package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Recipe_Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__details);

        String id = getIntent().getExtras().get("id").toString();
        TextView textView = findViewById(R.id.textView);
        textView.setText(id);

    }
}