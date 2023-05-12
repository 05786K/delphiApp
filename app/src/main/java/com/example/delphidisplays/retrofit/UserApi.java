package com.example.delphidisplays.retrofit;

import com.example.delphidisplays.model.LoginInfo;
import com.example.delphidisplays.model.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {
    /*
    @FormUrlEncoded
    @POST("/delphi/users/login")
    Call<User> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );*/

    @POST("delphi/users/login")
    Call<User> loginUser(@Body LoginInfo login);


    //register user endpoint
    /*
    OLD REGISTER ENDPOINT

    @FormUrlEncoded
    @POST("/delphi/users/register")
    Call<ResponseBody> registerUser(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("calories") int calories,
            @Field("total_fat") int total_fat,
            @Field("saturated_fat") int saturated_fat,
            @Field("sodium") int sodium,
            @Field("carbohydrates") int carbohydrates,
            @Field("sugars") int sugars,
            @Field("protein") int protein,
            @Field("filters") ArrayList<String> filters

            ); //userObject is send to REST  @FormUrlEncoded */


    @POST("/delphi/users/register")
    Call<ResponseBody> registerUser(@Body User user);

}
