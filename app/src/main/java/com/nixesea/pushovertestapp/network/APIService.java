package com.nixesea.pushovertestapp.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("1/messages.json/")
    @FormUrlEncoded
    Call<Post> savePost(@Field("token") String token,
                        @Field("user") String userToken,
                        @Field("title") String title,
                        @Field("message") String message);



}
