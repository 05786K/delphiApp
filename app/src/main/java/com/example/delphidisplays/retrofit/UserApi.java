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
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("calories") String calories,
            @Field("total_fat") String total_fat,
            @Field("saturated_fat") String saturated_fat,
            @Field("sodium") String sodium,
            @Field("carbohydrates") String carbohydrates,
            @Field("sugars") String sugars,
            @Field("protein") String protein

    ); //userObject is send to REST

}
