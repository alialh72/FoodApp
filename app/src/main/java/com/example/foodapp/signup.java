package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class signup extends AppCompatActivity {

    EditText textInputEditTextFullname,textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin, textView2;
    ProgressBar progressBar;
    private String currentUsername, currentFullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextFullname = findViewById(R.id.fullname);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextUsername = findViewById(R.id.username);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, username, password, email;
                fullname = String.valueOf(textInputEditTextFullname.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                username = String.valueOf(textInputEditTextUsername.getText());

                if (!fullname.equals("") && !username.equals("") && !email.equals("") && !password.equals("") && !username.equals("Guest")) {
                    progressBar.setVisibility(View.VISIBLE); //Start ProgressBar first (Set visibility VISIBLE)

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";

                            currentFullname = field[0]; //assigns the full name to a variable
                            currentUsername = field[1]; //assigns the username to a variable


                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;

                            //set the url to http://23.16.93.156:10013//FoodAppLogin/signup.php if accessing from a location outside of alis localhost
                            //it might already be set as the ip above, if so just leave it alone
                            PutData putData = new PutData("http://192.168.1.78:10019//FoodAppLogin/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    progressBar.setVisibility(View.GONE); //End ProgressBar (Set visibility to GONE)

                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(signup.this, result, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(signup.this, "Please Log in again", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    else{
                                        Toast.makeText(signup.this, result, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }

                else{
                    Toast.makeText(signup.this, "Please make sure all fields are filled", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }

    public void ReturnHome(View view){
        finish();
    }

}