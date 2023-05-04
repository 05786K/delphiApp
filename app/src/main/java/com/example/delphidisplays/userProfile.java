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

    TextView first_name, email, phone;
    Button signOut_btn;

    Button advertise_btn, scanner_btn;
    TextView resultBox;

    BluetoothLeScanner mBluetoothLeScanner;
    Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        System.out.println("---->Came to userProfile----->");
        //wire up text view and button objects with backend
        first_name = findViewById(R.id.first_name_tv);
        email = findViewById(R.id.email_tv);
        phone = findViewById(R.id.phone_tv);

        //Get Values
        String first_name_value = getIntent().getStringExtra("first_name");
        String email_value = getIntent().getStringExtra("email");
        String phone_value = getIntent().getStringExtra("phone");

        //set text view profile values
        first_name.setText(first_name_value);
        email.setText(email_value);
        phone.setText(phone_value);
        signOut_btn = findViewById(R.id.logout_btn);

        //BLE stuff
        advertise_btn = findViewById(R.id.advertise_btn);
        scanner_btn = findViewById(R.id.scanner_btn);
        resultBox = findViewById(R.id.results_textview);


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

        scanner_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });


        //scan call for scanner


    }

    public void signUserOut() {
        //reset fields in the profile to null
        first_name.setText(null);
        email.setText(null);
        phone.setText(null);

        //return back to home pack
        Intent goToHome = new Intent(userProfile.this, MainActivity.class);
        startActivity(goToHome);
        finish();


    }

    public void advertise() {
        Toast.makeText(this, "Advertising Started", Toast.LENGTH_LONG).show();
        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        // check if device supports bluetooth
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth Is Not Supported!", Toast.LENGTH_SHORT).show();
            return;
        }

        // check if multiple advertisements supported
//        if (!bluetoothAdapter.isMultipleAdvertisementSupported()){
//            Toast.makeText( this, "Multiple advertisement not supported", Toast.LENGTH_SHORT ).show();
//            return;
//        }

        //create advertiser and config settings
        BluetoothLeAdvertiser advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .setConnectable(true)
                .build();

        //new UUID
        //String uuID = UUID.randomUUID().toString();
        ParcelUuid pUuid = new ParcelUuid(UUID.fromString(getString(R.string.ble_uuid)));

        //create data packet?
        AdvertiseData data = new AdvertiseData.Builder()
                //.setIncludeDeviceName( true )
                .addServiceUuid(pUuid)
                //.addServiceData( pUuid, "Data".getBytes( Charset.forName( "UTF-8" ) ) )
                .build();

        //A callback is supposed to record success/failure
        AdvertiseCallback advertisingCallback = new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                super.onStartSuccess(settingsInEffect);
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
        System.out.println("advertising started...");
    }

    public void scan() {
        System.out.println("Scan fundtion called!!!");
        mBluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();

//        if( !BluetoothAdapter.getDefaultAdapter().isMultipleAdvertisementSupported() ) {
//            Toast.makeText( this, "Multiple advertisement not supported", Toast.LENGTH_SHORT ).show();
//        }



        List<ScanFilter> filters = new ArrayList<ScanFilter>();

        ScanFilter filter = new ScanFilter.Builder()
                .setServiceUuid(new ParcelUuid(UUID.fromString(getString(R.string.ble_uuid))))
                .build();

        filters.add(filter);

        ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();


        //create scan call back

        ScanCallback mScanCallback = new ScanCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                System.out.println("Entered mScanCallBack");
                super.onScanResult(callbackType, result);

                if (result == null || result.getDevice() == null || TextUtils.isEmpty(result.getDevice().getName())){
                    System.out.println("result is null");
                    return;
                }

                StringBuilder builder = new StringBuilder(result.getDevice().getName());

                builder.append("\n").append(new String(result.getScanRecord().getServiceData(result.getScanRecord().getServiceUuids().get(0)), Charset.forName("UTF-8")));

                resultBox.setText("The result is: " + builder.toString());
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                Log.e("BLE", "Discovery onScanFailed: " + errorCode);
                super.onScanFailed(errorCode);
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(mScanCallback == null || filter == null || settings == null)
            System.out.println("mScancallBack is null");
        else
            System.out.println("mscancallback is fine");

        mBluetoothLeScanner.startScan(filters, settings, mScanCallback);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(userProfile.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mBluetoothLeScanner.stopScan(mScanCallback);
            }
        }, 10000);
    }
}