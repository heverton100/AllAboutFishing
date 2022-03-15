package com.pucpr.heverton.allaboutfishing.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Places {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url_image")
    @Expose
    private String urlImage;
    @SerializedName("services")
    @Expose
    private String services;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("place_fishes")
    @Expose
    private String placeFishes;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("state_name")
    @Expose
    private String stateName;

    @SerializedName("response")
    @Expose
    private String Response;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlaceFishes() {
        return placeFishes;
    }

    public void setPlaceFishes(String placeFishes) {
        this.placeFishes = placeFishes;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
