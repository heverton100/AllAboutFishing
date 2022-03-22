package com.pucpr.heverton.allaboutfishing.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Galleries {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("url_photo")
    @Expose
    private String urlPhoto;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("place_name")
    @Expose
    private String placeName;
    @SerializedName("response")
    @Expose
    private String Response;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
