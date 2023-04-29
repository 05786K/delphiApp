package com.example.delphidisplays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

            }
        });

    }

    //authenticate login
    public void authenticateUser(){
        //check password and confirm password are same



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