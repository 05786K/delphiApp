package com.example.delphidisplays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.delphidisplays.model.Preferences;
import com.example.delphidisplays.model.User;
import com.example.delphidisplays.retrofit.RetrofitService;
import com.example.delphidisplays.retrofit.UserApi;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class signUpActivity extends AppCompatActivity {


    /**
     * @param userId
     * @param first_name
     * @param last_name
     * @param email
     * @param password
     * @param calories
     * @param total_fat
     * @param saturated_fat
     * @param sodium
     * @param carbohydrates
     * @param sugars
     * @param protein
     */


    EditText _first_name, _last_name, _email, _password;
    Button _signup_btn;
    SeekBar calories_bar, total_fat_bar, saturated_fat_bar, sodium_bar, carb_bar, sugars_bar, protein_bar;

    //checkboxes
    CheckBox nuts_checkbox, vegan_checkbox, lactose_checkbox, chicken_checkbox, gluten_checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        //wire up the the text fields with the above variables

        _first_name = findViewById(R.id.first_name_input);
        _last_name = findViewById(R.id.last_name_input);
        _email = findViewById(R.id.email_input);
        _password = findViewById(R.id.password_input);

        calories_bar = findViewById(R.id.calories_seekBar_input);
        total_fat_bar = findViewById(R.id.total_fat_seekBar_input);
        saturated_fat_bar = findViewById(R.id.saturated_fat_seekBar_input);
        sodium_bar = findViewById(R.id.sodium_seekBar_input);
        carb_bar = findViewById(R.id.carbohydrates_seekBar_input);
        sugars_bar = findViewById(R.id.sugar_seekBar_input);
        protein_bar = findViewById(R.id.protein_seekBar_input);


        nuts_checkbox = findViewById(R.id.nuts_checkbox_input);
        vegan_checkbox = findViewById(R.id.vegan_checkbox_input);
        lactose_checkbox = findViewById(R.id.lactose_checkbox_input);
        chicken_checkbox = findViewById(R.id.chicken_checkbox_input);
        gluten_checkbox = findViewById(R.id.gluten_checkbox_input);

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
        String fName = _first_name.getText().toString();
        if (fName.isEmpty()) {
            _first_name.setError("fullName cannot be empty");
            return false;
        } else {
            _first_name.setError(null);
            return true;
        }
    }


    //process fields and send data to rest api
    public void processForm() {
        if (!validateFullName()) {
            return;
        }


        //pass the required variables to registerUser method in userApi
        String first_name, last_name, email, password;
        double calories, total_fat, saturated_fat, sodium, carbohydrates, sugars, protein;

        first_name = _first_name.getText().toString();
        last_name = _last_name.getText().toString();
        email = _email.getText().toString();
        password = _password.getText().toString();
        calories = (double) calories_bar.getProgress();
        total_fat = (double) total_fat_bar.getProgress();
        saturated_fat = (double) total_fat_bar.getProgress();
        sodium = (double) sodium_bar.getProgress();
        carbohydrates = (double) carb_bar.getProgress();
        sugars = (double) sugars_bar.getProgress();
        protein = (double) protein_bar.getProgress();

        ArrayList<String> filters = new ArrayList<>();
        if(nuts_checkbox.isChecked())
            filters.add("nuts");
        if(vegan_checkbox.isChecked())
            filters.add("vegan");
        if(lactose_checkbox.isChecked())
            filters.add("lactose");
        if(chicken_checkbox.isChecked())
            filters.add("chicken");
        if(gluten_checkbox.isChecked())
            filters.add("gluten");



        //create a retrofit service, better keep these above user object
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        Preferences user_preferences = new Preferences(calories,
                total_fat, saturated_fat, sodium, carbohydrates, sugars, protein);

        User user = new User(first_name, last_name, email, password, user_preferences, filters);
        userApi.registerUser(user)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(signUpActivity.this, "User Registration Successful!", Toast.LENGTH_LONG).show();
                            _first_name.setText(null);
                            _last_name.setText(null);
                            _email.setText(null);
                            _password.setText(null);
                            calories_bar.setProgress(0);
                            total_fat_bar.setProgress(0);
                            saturated_fat_bar.setProgress(0);
                            sodium_bar.setProgress(0);
                            carb_bar.setProgress(0);
                            sugars_bar.setProgress(0);
                            protein_bar.setProgress(0);

                            vegan_checkbox.setChecked(false);
                            nuts_checkbox.setChecked(false);
                            lactose_checkbox.setChecked(false);
                            chicken_checkbox.setChecked(false);
                            gluten_checkbox.setChecked(false);

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