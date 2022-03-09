package com.pucpr.heverton.allaboutfishing.remote;

import androidx.annotation.NonNull;

public class ApiUtils {

    public static final String URL_BASE = "https://api2-allaboutfishing.azurewebsites.net/api/";


    @NonNull
    public static PostService getPostService(){
        return RetrofitClient.getClient(URL_BASE).create(PostService.class);
    }
}
