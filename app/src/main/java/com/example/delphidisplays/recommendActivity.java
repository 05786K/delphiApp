package com.example.delphidisplays;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.delphidisplays.model.LoginInfo;
import com.example.delphidisplays.model.Recommendation;
import com.example.delphidisplays.model.User;
import com.example.delphidisplays.retrofit.RetrofitService;
import com.example.delphidisplays.retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recommendActivity extends AppCompatActivity {

    EditText _restaurantId, _userId;
    Button recommend, goToProfile;

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        _restaurantId = findViewById(R.id.restaurant_ID_input);
        _userId = findViewById(R.id.userID_input);
        recommend = findViewById(R.id.recommend_btn);
        goToProfile = findViewById(R.id.back_to_userProfile);

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processRecommend();
            }
        });

    }

    public void processRecommend(){

        String userId = _userId.getText().toString();
        String restaurantId = _restaurantId.getText().toString();

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);


        ArrayList<String> foodItems = new ArrayList();


        //listView
        ListView listView = (ListView) findViewById(R.id.results_listView);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listview_style, foodItems);

        userApi.getRecommendations(userId, restaurantId)
                .enqueue(new Callback<Map<String, List<Recommendation>>>() {
                    @Override
                    public void onResponse(Call<Map<String, List<Recommendation>>> call, Response<Map<String, List<Recommendation>>> response) {
                        if(response.isSuccessful()){
                            Map<String, List<Recommendation>> recommendedFood = response.body();
                            List<Recommendation> resultsArray = new ArrayList<>();
                            resultsArray = recommendedFood.get("Recommendations");

                            if (resultsArray != null) {

                                for(Recommendation recom : resultsArray){
                                    foodItems.add(
                                            recom.getRank() + "      " + recom.getItemName());
                                }
                                listView.setAdapter(adapter);
                            }
                            else
                            {
                                Toast.makeText(recommendActivity.this, "Recommendation Failed!", Toast.LENGTH_LONG).show();
                            }

                        }else{
                            Toast.makeText(recommendActivity.this, "Recommendation Failed!", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, List<Recommendation>>> call, Throwable t) {
                        Toast.makeText(recommendActivity.this, "User Login Failed!", Toast.LENGTH_LONG).show();

                    }

                });


    }
    public void signOut(View view) {
        Intent intent = new Intent(recommendActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }



}
