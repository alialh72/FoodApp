package com.example.foodapp.nonactivityclasses;

import androidx.annotation.Nullable;
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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class getInfo {

    private static final String TAG = "";
    private String mUsername;
    private String mResult;

    private String CurrentRestaurantName = "";
    private String CurrentRestaurantImage = "";

    public String[] result;
    private Integer currentPrice;

    public void mGet(String username) throws ExecutionException, InterruptedException {
        if (!username.equals("")) {
            ExecutorService service = Executors.newFixedThreadPool(10);

            Future<String[]> future = service.submit(new Task(username));

            result = future.get(); //blocking operation
        }
    }

    static class Task implements Callable<String[]>{
        private final String username;

        public Task(String username){
            this.username = username;
        }

        @Override
        public String[] call() throws Exception{
            //Starting Write and Read data with URL
            //Creating array for parameters
            String[] parts = new String[2];

            String[] field = new String[1];
            field[0] = "username";

            //Creating array for data
            String[] data = new String[1];
            data[0] = username;

            //set the url to http://23.16.93.156:10013//FoodAppLogin/getinfo.php if accessing from a location outside of alis localhost
            //it might already be set as the ip above, if so just leave it alone

            PutData putData = new PutData("http://192.168.1.78:10019//FoodAppLogin/getinfo.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();

                    if (result.equals("Login Success")){
                        Log.d(TAG, "run: how tf did this happen");
                        Log.d(TAG, "run: result: "+ result);
                    }

                    else{
                        parts = result.split("-");
                        Log.d(TAG, "call: parts: " + parts);

                    }


                }
            }
            return parts;

        }
    }

}
