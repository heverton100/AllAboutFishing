package com.pucpr.heverton.allaboutfishing;

import android.content.Context;


public class CustomVolleyRequest {

    private static CustomVolleyRequest customVolleyRequest;
    private static Context context;


    private CustomVolleyRequest(Context context){

        this.context = context;


    }

    public static synchronized CustomVolleyRequest getInstance(Context context){

        if(customVolleyRequest == null){
            customVolleyRequest = new CustomVolleyRequest(context);
        }
        return customVolleyRequest;
    }




}
