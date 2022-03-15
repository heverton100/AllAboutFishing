package com.pucpr.heverton.allaboutfishing.remote;

import com.pucpr.heverton.allaboutfishing.model.Places;
import com.pucpr.heverton.allaboutfishing.model.Users;
import com.pucpr.heverton.allaboutfishing.model.Weathers;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


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

    @GET("endpoints/weather/{vCity}/{vState}")
    Call<Weathers> getWeather(@Path(value="vCity", encoded=true) String vCity, @Path(value="vState", encoded=true) String vState);

    @GET("places/all")
    Call<List<Places>> getPlaces();

    @Multipart
    @POST("users/uploadimageprofile")
    Call<Users> uploadImage(@Part("image\"; filename=\"my_file.jpg\" ") RequestBody file,
                            @Part("desc") RequestBody desc,
                            @Part("email") RequestBody email);

    @POST("users/update")
    @FormUrlEncoded
    Call<Users> update(@Field("id") Integer id,
                       @Field("name") String name,
                       @Field("phone") String phone,
                       @Field("url_image") String url_image);

}
