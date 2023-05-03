package com.example.delphidisplays.retrofit;

import com.example.delphidisplays.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @FormUrlEncoded
    @POST("/delphi/login")
    Call<User> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );


    //register user endpoint
    @FormUrlEncoded
    @POST("/delphi/register")
    Call<ResponseBody> registerUser(
            @Field("fullName") String fullName,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("password") String password,
            @Field("beef") String beef,
            @Field("chicken") String chicken,
            @Field("spicy") String spicy

    ); //userObject is send to REST

}
