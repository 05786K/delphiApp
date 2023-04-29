package com.example.delphidisplays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }





    public void goToSignUpActivity(View view){
        Intent intent = new Intent(MainActivity.this, signUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSignInActivity(View view){
        Intent intent = new Intent(MainActivity.this, signInActivity.class);
        startActivity(intent);
        finish();
    }
}