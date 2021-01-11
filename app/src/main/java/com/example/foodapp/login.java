package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class login extends AppCompatActivity {

    private static final String TAG = "";
    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp, textView2;
    ProgressBar progressBar;
    private String currentUsername;
    private boolean justlogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextUsername = findViewById(R.id.username);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);
        textView2 = findViewById(R.id.textView2);

        if(getIntent().getExtras() != null){
            justlogged = getIntent().getExtras().getBoolean("JUSTLOGGED");
        }

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), signup.class);
                startActivity(intent);
                finish();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((textView2.getText()) == "show password"){
                    textInputEditTextPassword.setTransformationMethod(null);
                    textView2.setText("hide password");
                }
                else{
                    textInputEditTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                    textView2.setText("show password");
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                password = String.valueOf(textInputEditTextPassword.getText());
                username = String.valueOf(textInputEditTextUsername.getText());

                if (!username.equals("") && !password.equals("")) {
                    progressBar.setVisibility(View.VISIBLE); //Start ProgressBar first (Set visibility VISIBLE)

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";



                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            currentUsername = data[0];

                            //set the url to http://23.16.93.156:10013//FoodAppLogin/login.php if accessing from a location outside of alis localhost
                            //it might already be set as the ip above, if so just leave it alone
                            PutData putData = new PutData("http://192.168.1.85:10013//FoodAppLogin/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    progressBar.setVisibility(View.GONE); //End ProgressBar (Set visibility to GONE)

                                    if (result.equals("Login Success")){
                                        Toast.makeText(login.this, result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("USERNAME", currentUsername);
                                        justlogged = false;
                                        boolean loggedin = true;
                                        intent.putExtra("LOGGEDIN", loggedin);
                                        Log.d(TAG, "login: home page started");
                                        startActivity(intent);
                                        finish();
                                    }

                                    else{
                                        Toast.makeText(login.this, result, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }

                else{
                    Toast.makeText(login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }

    public void ReturnHome(View view){
        boolean loggedin = false;
        justlogged = false;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("LOGGEDIN", loggedin);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() { //disables the go back button on android when on the login page when accessed by signing out from a page other than main activity (because it breaks the app otherwise)
        if (justlogged == true) {

        } else {
            super.onBackPressed();
        }
    }
}