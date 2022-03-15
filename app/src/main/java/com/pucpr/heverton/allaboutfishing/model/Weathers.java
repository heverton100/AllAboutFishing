package com.pucpr.heverton.allaboutfishing.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weathers {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("temperature")
    @Expose
    private Integer temperature;
    @SerializedName("humidity")
    @Expose
    private Double humidity;
    @SerializedName("condition")
    @Expose
    private String condition;

    @SerializedName("response")
    @Expose
    private String Response;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
