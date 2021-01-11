package com.example.foodapp;

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

public class getFood {

    private static final String TAG = "";
    public String[] result;


    public String[] mGet(String username) throws ExecutionException, InterruptedException {
        Log.d(TAG, "mGet: inside method");
        ExecutorService service = Executors.newFixedThreadPool(10);

        Future<String[]> future = service.submit(new Task(username));
        result = future.get(); //blocking operation


        return result;
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
            String[] parts = new String[0];

            String[] field = new String[1];
            field[0] = "username";

            //Creating array for data
            String[] data = new String[1];
            data[0] = username;


            //set the url to http://23.16.93.156:10013//FoodAppLogin/getinfo.php if accessing from a location outside of alis localhost
            //it might already be set as the ip above, if so just leave it alone
            PutData putData = new PutData("http://192.168.1.85:10013//FoodAppLogin/getinfo2.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();

                    if (result.equals("Login Success")){
                        Log.d(TAG, "run: how tf did this happen");
                        Log.d(TAG, "run: result: "+ result);
                    }

                    else{
                        Log.d(TAG, "call: results: " + result.toString());
                        parts = result.split("~");
                        Log.d(TAG, "call: parts: " + (parts[10].toString()));

                    }


                }
            }
            return parts;

        }
    }



}

    /*private void setter(String result){
        mResult = result;
        Log.d(TAG, "setter: result: " + result);
        Log.d(TAG, "setter: mResult " + mResult);
        MainActivity main = new MainActivity();
        main.mSetResult(mResult);
    }*/
