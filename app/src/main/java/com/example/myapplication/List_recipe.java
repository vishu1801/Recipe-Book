package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List_recipe {
    boolean isclicked;

    public boolean isIsclicked() {
        return isclicked;
    }

    public void setIsclicked(boolean isclicked) {
        this.isclicked = isclicked;
    }

    public List_recipe(boolean isclicked, Integer id) {
        this.isclicked=isclicked;
        this.id = id;
    }

    public List_recipe(Integer id) {
        this.isclicked=false;
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private Integer id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



}
