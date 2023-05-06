package com.example.delphidisplays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.BLUETOOTH_ADVERTISE) !=
                PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.BLUETOOTH_ADVERTISE },
                    1);
        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.BLUETOOTH_CONNECT) !=
                PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.BLUETOOTH_CONNECT },
                    1);
        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.BLUETOOTH_SCAN) !=
                PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.BLUETOOTH_SCAN },
                    1);
        }


        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    1);
        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.BLUETOOTH_ADMIN) !=
                PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.BLUETOOTH_ADMIN },
                    1);
        }


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