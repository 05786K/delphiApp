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

    @POST("delphi/users/login")
    Call<User> loginUser(@Body LoginInfo login);


    @POST("/delphi/users/register")
    Call<ResponseBody> registerUser(@Body User user);

}
