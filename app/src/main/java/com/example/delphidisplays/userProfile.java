package com.example.delphidisplays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class userProfile extends AppCompatActivity {

    TextView first_name, email, user_id;
    Button signOut_btn;

    Button advertise_btn;
    TextView resultBox;

    BluetoothLeScanner mBluetoothLeScanner;
    Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //wire up text view and button objects with backend
        first_name = findViewById(R.id.first_name_tv);
        email = findViewById(R.id.email_tv);
        user_id = findViewById(R.id.user_tv);

        //Get Values
        String first_name_value = getIntent().getStringExtra("first_name");
        String email_value = getIntent().getStringExtra("email");
        String user_id_value = getIntent().getStringExtra("user_id");

        System.out.println("user id value: " + user_id_value);
        //set text view profile values
        first_name.setText(first_name_value);
        email.setText(email_value);
        user_id.setText(user_id_value);
        signOut_btn = findViewById(R.id.logout_btn);

        //BLE stuff
        advertise_btn = findViewById(R.id.advertise_btn);


        signOut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUserOut();
            }

        });

        advertise_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call advertise function
                advertise();
            }
        });


        //advertise();
    }

    public void signUserOut() {
        //reset fields in the profile to null
        first_name.setText(null);
        email.setText(null);
        user_id.setText(null);

        //return back to home pack
        Intent goToHome = new Intent(userProfile.this, MainActivity.class);
        startActivity(goToHome);
        finish();


    }

    public void advertise() {

        BluetoothManager myBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter myBluetoothAdapter = myBluetoothManager.getAdapter();


        // check if device supports bluetooth
        if (myBluetoothAdapter == null || !myBluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth Is Not Supported OR not enabled!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Bluetooth Is Not Supported OR not enabled!", Toast.LENGTH_LONG).show();
            return;
        }

        // check if multiple advertisements supported
        if (!myBluetoothAdapter.isMultipleAdvertisementSupported()){
            Toast.makeText( this, "Multiple advertisement not supported", Toast.LENGTH_SHORT ).show();
            return;
        }


        //create advertiser and config settings
        BluetoothLeAdvertiser advertiser = myBluetoothAdapter.getBluetoothLeAdvertiser();
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setConnectable(true)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .build();

        //new UUID
        ParcelUuid pUuid = new ParcelUuid(UUID.fromString(getString(R.string.ble_uuid)));

        //create data packet?
        AdvertiseData data = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                //.addServiceUuid(new ParcelUuid(bleUUID))
                .build();

        //A callback to record success/failure
        AdvertiseCallback advertisingCallback = new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                super.onStartSuccess(settingsInEffect);
                Log.e("BLE", "Advertising successfully started!");
            }

            @Override
            public void onStartFailure(int errorCode) {
                Log.e("BLE", "Advertising onStartFailure: " + errorCode);
                super.onStartFailure(errorCode);
            }
        };

        System.out.println("testing line--->");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        advertiser.startAdvertising(settings, data, advertisingCallback);
    }

}