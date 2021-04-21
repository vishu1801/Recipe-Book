package com.example.myapplication.AllResponse.Recipe_details_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExtendedIngredient {
    @SerializedName("aisle")
    @Expose
    private String aisle;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("consitency")
    @Expose
    private String consitency;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("measures")
    @Expose
    private Measures measures;
    @SerializedName("meta")
    @Expose
    private List<String> meta = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("originalName")
    @Expose
    private String originalName;
    @SerializedName("unit")
    @Expose
    private String unit;

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getConsitency() {
        return consitency;
    }

    public void setConsitency(String consitency) {
        this.consitency = consitency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }

    public List<String> getMeta() {
        return meta;
    }

    public void setMeta(List<String> meta) {
        this.meta = meta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
