package com.example.delphidisplays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
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
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class userProfile extends AppCompatActivity {

    TextView first_name, last_name, email, userId;
    Button signOut_btn;

    Button advertise_btn;
    TextView resultBox;

    BluetoothManager myBluetoothManager;
    BluetoothAdapter myBluetoothAdapter;

    private static final String SERVICE_UUID = "00001111-0000-1000-8000-00805F9B34FB";
    private static final String CHARACTERISTIC_UUID = "00002222-0000-1000-8000-00805F9B34FB";
    BluetoothGattServer gattServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //wire up text view and button objects with backend
        first_name = findViewById(R.id.first_name_tv);
        email = findViewById(R.id.email_tv);
        userId = findViewById(R.id.user_tv);

        //Get Values
        String first_name_value = getIntent().getStringExtra("first_name");
        String last_name_value = getIntent().getStringExtra("last_name");
        String email_value = getIntent().getStringExtra("email");
        String userId_value = getIntent().getStringExtra("userId");

        //set text view profile values
        first_name.setText(first_name_value + " " + last_name_value);
        email.setText(email_value);
        userId.setText(userId_value);
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

    @SuppressLint("MissingPermission")
    public void signUserOut() {
        //reset fields in the profile to null
        first_name.setText(null);
        email.setText(null);
        userId.setText(null);

        if(gattServer != null)
            gattServer.close();

        //return back to home pack
        Intent goToHome = new Intent(userProfile.this, MainActivity.class);
        startActivity(goToHome);
        finish();


    }

    @SuppressLint("MissingPermission")
    private void initializeGattServer() {

        gattServer = myBluetoothManager.openGattServer(this, gattServerCallback);
        BluetoothGattService service = new BluetoothGattService(
                UUID.fromString(SERVICE_UUID),
                BluetoothGattService.SERVICE_TYPE_PRIMARY
        );

        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(
                UUID.fromString(CHARACTERISTIC_UUID),
                BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_READ
        );


        service.addCharacteristic(characteristic);
        gattServer.addService(service);
    }

    private final BluetoothGattServerCallback gattServerCallback = new BluetoothGattServerCallback() {
        @SuppressLint("MissingPermission")
        @Override
        public void onCharacteristicReadRequest(
                android.bluetooth.BluetoothDevice device,
                int requestId,
                int offset,
                BluetoothGattCharacteristic characteristic
        ) {
            if (offset != 0) {
                //gattServer.sendResponse(device, requestId, BluetoothGattCharacteristic.RESULT_INVALID_OFFSET, offset, null);
                return;
            }

            //get userID value
            String userId_value = getIntent().getStringExtra("userId");

            String userInfo = "User ID # " + userId_value;
            byte[] valueBytes = userInfo.getBytes();
            characteristic.setValue(valueBytes);
            gattServer.notifyCharacteristicChanged(device, characteristic, false);

        }
    };
    @SuppressLint("MissingPermission")
    public void advertise() {

        myBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        myBluetoothAdapter = myBluetoothManager.getAdapter();


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
                //.addServiceUuid(pUuid)
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

       advertiser.startAdvertising(settings, data, advertisingCallback);
       //initiate gatt service
       initializeGattServer();
    }



}