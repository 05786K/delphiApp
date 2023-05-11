package com.example.delphidisplays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.delphidisplays.model.User;
import com.example.delphidisplays.retrofit.RetrofitService;
import com.example.delphidisplays.retrofit.UserApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class signInActivity extends AppCompatActivity {

    Button _login_btn;
    EditText _login_email;
    EditText _login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //wire up front UI with backend fields
        _login_email = findViewById(R.id.email_input);
        _login_password = findViewById(R.id.password_input);
        _login_btn = findViewById(R.id.login_submit_btn);

        //process form
        _login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processLogin();
            }
        });

    }

    public void processLogin(){
        //check password and confirm password are same
        String email = _login_email.getText().toString();
        String password = _login_password.getText().toString();



        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);


        userApi.loginUser(email, password)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                        if(response.isSuccessful()){
                            User user = response.body();

                            //wire up data to user profile
                            Intent goToProfile = new Intent(signInActivity.this, userProfile.class);

                            goToProfile.putExtra("first_name", user.getFirst_name());
                            goToProfile.putExtra("user_id", user.getUser_id());
                            goToProfile.putExtra("email", user.getEmail());

                            //start activity
                            startActivity(goToProfile);
                            finish();
                        }else{
                            Toast.makeText(signInActivity.this, "User Login Failed!", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(signInActivity.this, "User Login Failed!", Toast.LENGTH_LONG).show();

                    }

                });


    }

    //when goToHome btn is clicked, the below function is invoked.
    public void goToHome(View view){
        Intent intent = new Intent(signInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void goToSignUpActivity(View view){
        Intent intent = new Intent(signInActivity.this, signUpActivity.class);
        startActivity(intent);
        finish();

    }


    //validate password and email here
}