package com.example.myapplication.AllResponse.Recipe_Step_Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe_steps {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("steps")
    @Expose
    private ArrayList<Step> steps = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

}
