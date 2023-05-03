package com.example.delphidisplays;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.delphidisplays.model.User;
import com.example.delphidisplays.retrofit.RetrofitService;
import com.example.delphidisplays.retrofit.UserApi;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signUpActivity extends AppCompatActivity {

    EditText _fullName, _phone, _email, _password;
    Button _signup_btn;
    SeekBar _beefSeekBar, _chkSeekBar, _spicySeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        //wire up the the text fields with the above variables

        _fullName = findViewById(R.id.fullName_input);
        _phone = findViewById(R.id.phone_input);
        _email = findViewById(R.id.email_input);
        _password = findViewById(R.id.password_input);


        _beefSeekBar = findViewById(R.id.beef_seekBar_input);
        _chkSeekBar = findViewById(R.id.chicken_seekBar_input);
        _spicySeekBar = findViewById(R.id.spicy_seekBar_input);


        _signup_btn = findViewById(R.id.submit_btn);
        _signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processForm();
            }
        });
    }
    //when goToHome btn is clicked, the below function is invoked.
    public void goToHome(View view) {
        Intent intent = new Intent(signUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void goToSignIn(View view) {
        Intent intent = new Intent(signUpActivity.this, signInActivity.class);
        startActivity(intent);
        finish();
    }


    //validate input fields here
    public boolean validateFullName() {
        String fName = _fullName.getText().toString();
        if (fName.isEmpty()) {
            _fullName.setError("fullName cannot be empty");
            return false;
        } else {
            _fullName.setError(null);
            return true;
        }
    }


    //process fields and send data to rest api
    public void processForm() {
        if (!validateFullName()) {
            return;
        }

        System.out.println("kos called!");

        //pass the required variables to registerUser method in userApi
        String fullName, phone, email, password, chicken, beef, spicy;
        fullName = _fullName.getText().toString();
        phone = _phone.getText().toString();
        email = _email.getText().toString();
        password = _password.getText().toString();
        beef = String.valueOf(_beefSeekBar.getProgress());
        chicken = String.valueOf(_chkSeekBar.getProgress());
        spicy = String.valueOf(_spicySeekBar.getProgress());



        //create a retrofit service, better keep these above user object
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.registerUser(fullName, phone, email, password, beef, chicken, spicy)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(signUpActivity.this, "User Registration Successful!", Toast.LENGTH_LONG).show();
                            _fullName.setText(null);
                            _phone.setText(null);
                            _email.setText(null);
                            _password.setText(null);
                            _beefSeekBar.setProgress(0);
                            _chkSeekBar.setProgress(0);
                            _spicySeekBar.setProgress(0);

                        }else{
                            Toast.makeText(signUpActivity.this, "User Registration Failed!", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(signUpActivity.this, "User Registration Failed!", Toast.LENGTH_LONG).show();
                        Logger.getLogger(signUpActivity.class.getName()).log(Level.SEVERE, "Error at", t);
                    }
                });


    }

}