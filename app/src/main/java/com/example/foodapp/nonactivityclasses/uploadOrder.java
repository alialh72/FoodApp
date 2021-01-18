package com.example.foodapp.nonactivityclasses;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.MainActivity;
import com.example.foodapp.food_page;
import com.example.foodapp.login;
import com.example.foodapp.signup;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static android.content.ContentValues.TAG;

public class uploadOrder {

    private String currentUsername, currentOrder;
    private static final String TAG = "";

    public void mUploadOrder(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "username";
                field[1] = "order";


                currentUsername = field[0]; //assigns the full name to a variable
                currentOrder = field[1]; //assigns the username to a variable


                //Creating array for data
                String[] data = new String[2];
                data[0] = MainActivity.username;
                data[1] = food_page.CartClass.finalorder;

                Log.d(TAG, "run: data[0]: "+ data[0]);
                Log.d(TAG, "run: data[1]: " + data[1]);

                //set the url to http://23.16.93.156:10018//FoodAppLogin/uploadorder.php if accessing from a location outside of alis localhost
                //it might already be set as the ip above, if so just leave it alone
                PutData putData = new PutData("http://23.16.93.156:10018//FoodAppLogin/orderupload.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();


                        if (result.equals("Order Uploaded")){
                            Log.d(TAG, "run: Big success!");
                            food_page.CartClass.clearCart();
                        }

                        else if (result.equals("Upload Failed!")){
                            Log.d(TAG, "run: big oopsies");
                        }

                    }
                }
                //End Write and Read data with URL
            }
        });
    }
}
