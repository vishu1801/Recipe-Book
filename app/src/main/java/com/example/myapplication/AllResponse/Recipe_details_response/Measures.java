package com.example.myapplication.AllResponse.Recipe_details_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Measures {
    @SerializedName("metric")
    @Expose
    private Metric metric;
    @SerializedName("us")
    @Expose
    private Us us;

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Us getUs() {
        return us;
    }

    public void setUs(Us us) {
        this.us = us;
    }
}
