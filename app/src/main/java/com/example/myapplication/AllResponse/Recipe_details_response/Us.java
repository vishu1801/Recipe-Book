package com.example.myapplication.AllResponse.Recipe_details_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Us {
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("unitLong")
    @Expose
    private String unitLong;
    @SerializedName("unitShort")
    @Expose
    private String unitShort;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnitLong() {
        return unitLong;
    }

    public void setUnitLong(String unitLong) {
        this.unitLong = unitLong;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }
}
