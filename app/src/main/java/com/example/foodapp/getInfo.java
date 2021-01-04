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

public class getInfo {

    private static final String TAG = "";
    private String mUsername;
    private String mResult;

    /*public void GetInfo(String username){
        mUsername = username;
        final String[] lresult = {""};

        if (!username.equals("")) {

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[1];
                    field[0] = "username";

                    //Creating array for data
                    String[] data = new String[1];
                    data[0] = username;


                    PutData putData = new PutData("http://192.168.1.76/FoodAppLogin/getinfo.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();

                            if (result.equals("Login Success")){
                                Log.d(TAG, "run: big success");
                                Log.d(TAG, "run: result: "+ result);
                            }

                            else{
                                Log.d(TAG, "run: poopy fail");
                                Log.d(TAG, "run: result: "+ result);
                                mResult = result;
                                lresult[0] = result;
                                Log.d(TAG, "run: lresult: " + lresult[0]);
                                Log.d(TAG, "run: mresult: " +mResult);

                                setter(mResult);
                            }


                        }
                    }
                    //End Write and Read data with URL
                }

            });
        }



    }*/

    /*private void setter(String result){
        mResult = result;
        Log.d(TAG, "setter: result: " + result);
        Log.d(TAG, "setter: mResult " + mResult);
        MainActivity main = new MainActivity();
        main.mSetResult(mResult);
    }*/
}