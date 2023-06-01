package com.example.delphidisplays.retrofit;

import com.example.delphidisplays.model.LoginInfo;
import com.example.delphidisplays.model.Recommendation;
import com.example.delphidisplays.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @POST("delphi/users/login")
    Call<User> loginUser(@Body LoginInfo login);


    @POST("/delphi/users/register")
    Call<ResponseBody> registerUser(@Body User user);

    @GET("/delphi/orders/recommend/{userId}/{restaurantId}")
    Call<Map<String, List<Recommendation>>> getRecommendations
            (@Path("userId") String userId,
             @Path("restaurantId") String restaurantId);

}
