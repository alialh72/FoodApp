package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.foodapp.RecyclerViews.recyclerorder2;
import com.example.foodapp.RecyclerViews.uploadOrder;

public class order_complete extends AppCompatActivity {

    private static final String TAG = "";
    uploadOrder upload= new uploadOrder();
    TextView ordernumber, restname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        ordernumber = findViewById(R.id.ordernum);
        restname = findViewById(R.id.restname);

        ordernumber.setText("Order #"+String.valueOf(food_page.CartClass.ordernumber));
        restname.setText(food_page.CartClass.restname);


        //Local favorites recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView = findViewById(R.id.recyclerViewPopup);
        recyclerorder2 LocalAdapter = new recyclerorder2(food_page.CartClass.cart,this, food_page.CartClass.descriptions, food_page.CartClass.foodprices);
        LocalrecyclerView.setAdapter(LocalAdapter);
        LocalrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        upload.mUploadOrder();
    }

    public void returnhome(View view){
        finish();
    }
}