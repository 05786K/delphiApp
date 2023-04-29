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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

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
            public void onClick(View view) {
                //call processfields functions
                processForm();


            }
        });

    }

    //when goToHome btn is clicked, the below function is invoked.
    public void goToHome(View view){
        Intent intent = new Intent(signUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void goToSignIn(View view){
        Intent intent = new Intent(signUpActivity.this, signInActivity.class);
        startActivity(intent);
        finish();
    }


    //validate input fields here
    public boolean validateFullName(){
        String fName = _fullName.getText().toString();
        if(fName.isEmpty()) {
            _fullName.setError("fullName cannot be empty");
            return false;
        }else{
            _fullName.setError(null);
            return true;
        }
    }


    //process fields
    public void processForm(){
        if(!validateFullName()){
            return;
        }

        //Init Request Queue
        RequestQueue queue = Volley.newRequestQueue(signUpActivity.this);

        //provide the url where the REST api is in. The URL we are posting to
        String URL = "http://192.168.137.1:9082/delphi/register";

        //create request object
        StringRequest stringReq = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")){

                    //after the user is filled the fields, and the registration is successfull
                    //we gotta set the fields empty otherwise it might cause redundancy
                    _fullName.setText(null);
                    _phone.setText(null);
                    _email.setText(null);
                    _password.setText(null);
                    _beefSeekBar.setProgress(0);
                    _chkSeekBar.setProgress(0);
                    _spicySeekBar.setProgress(0);



                    Toast.makeText(signUpActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(signUpActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();

            }
        }){
            //this object is send over to the Rest API
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("fullName", _fullName.getText().toString());
                parameters.put("phone", _phone.getText().toString());
                parameters.put("email", _email.getText().toString());
                parameters.put("password", _password.getText().toString());
                parameters.put("beef", String.valueOf(_beefSeekBar.getProgress()));
                parameters.put("chicken", String.valueOf(_chkSeekBar.getProgress()));
                parameters.put("spicy", String.valueOf(_spicySeekBar.getProgress()));

                for(String str : parameters.values())
                    System.out.println(str);

                return parameters;
            }
        }; //end of request

        //add string request to the queue
        queue.add(stringReq);
    }

}