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
import com.example.delphidisplays.model.Order;
import com.example.delphidisplays.model.Recommendation;
import com.example.delphidisplays.model.User;
import com.example.delphidisplays.retrofit.RetrofitService;
import com.example.delphidisplays.retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recommendActivity extends AppCompatActivity {

    EditText _restaurantId, _userId, _restaurantId2, _userId2, _foodItemNumbers;
    Button recommend, goToProfile, order;

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        _restaurantId = findViewById(R.id.restaurant_ID_input);
        _userId = findViewById(R.id.userID_input);
        recommend = findViewById(R.id.recommend_btn);
        order = findViewById(R.id.order_btn);
        goToProfile = findViewById(R.id.back_to_userProfile);
        _foodItemNumbers = findViewById(R.id.food_item_numbers);
        _userId2 = findViewById(R.id.userID_input2);
        _restaurantId2 = findViewById(R.id.restaurant_ID_input2);

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processRecommend();
            }
        });


    }

    public void processOrder(){
        String userId = _userId.getText().toString();
        String restaurantId = _restaurantId.getText().toString();


        //create object for order
        String foodItemNumbers = _foodItemNumbers.getText().toString();

        if(foodItemNumbers == null){
            _foodItemNumbers.setError("foodItemNumbers cannot be empty!");
        }

        ArrayList<Integer> itemNumbersList = new ArrayList<>();

        //loop through the string and insert tokens in arrayList
        for (String token : foodItemNumbers.split(" +"))
            itemNumbersList.add(Integer.parseInt(token));


        Order order = new Order(Integer.parseInt(restaurantId), userId, itemNumbersList);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.registerOrder(order).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(recommendActivity.this, "Order Update Successful!", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(recommendActivity.this, "Order Update Failed", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(recommendActivity.this, "Order Update Failed!", Toast.LENGTH_LONG).show();
                Logger.getLogger(signUpActivity.class.getName()).log(Level.SEVERE, "Error at", t);
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

                                //fill userID and restaurant id fields for order
                                _userId2.setText(userId);
                                _restaurantId2.setText(restaurantId);

                                //activate order button
                                order.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        processOrder();
                                    }
                                });

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
