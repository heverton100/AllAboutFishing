package com.pucpr.heverton.allaboutfishing.remote;

import com.pucpr.heverton.allaboutfishing.model.Users;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface PostService {

    @POST("users/login")
    @FormUrlEncoded
    Call<Users> login(@Field("email") String email,
                      @Field("password") String password);

    @POST("users/new")
    @FormUrlEncoded
    Call<Users> register(@Field("name") String name,
                         @Field("email") String email,
                         @Field("password") String password,
                         @Field("phone") String phone);

    @POST("users/sendresetlink")
    @FormUrlEncoded
    Call<Users> reset_pass(@Field("email") String email);



}
