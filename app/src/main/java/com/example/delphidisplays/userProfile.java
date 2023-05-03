package com.example.delphidisplays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class userProfile extends AppCompatActivity {

    TextView fullName_, email_, phone_;
    Button signOut_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        System.out.println("---->Came to userProfile----->");
        //wire up text view and button objects with backend
        fullName_ = findViewById(R.id.fullName);
        email_ = findViewById(R.id.email);
        phone_ = findViewById(R.id.phone);

        //Get Values
        String fullName_value = getIntent().getStringExtra("fullName");
        String email_value = getIntent().getStringExtra("email");
        String phone_value = getIntent().getStringExtra("phone");

        //set text view profile values
        fullName_.setText(fullName_value);
        email_.setText(email_value);
        phone_.setText(phone_value);
        signOut_btn = findViewById(R.id.logout_btn);

        signOut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUserOut();
            }

        });

    }
    public void signUserOut(){
        //reset fields in the profile to null
        fullName_.setText(null);
        email_.setText(null);
        phone_.setText(null);

        //return back to home pack
        Intent goToHome = new Intent(userProfile.this, MainActivity.class);
        startActivity(goToHome);
        finish();


    }
}