package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.RecyclerViews.RecyclerCart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CartPage extends AppCompatActivity {

    private static final String TAG = "";
    private String CurrentRestaurantName = "";
    private String CurrentRestaurantImage = "";
    private String mResult;
    private double finalprice;
    private double subtotalnum = 0;

    //xml
    private View decorView;
    private TextView restaurant_name_text,subtotal,tax,deliveryfee,total;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        // Hide the status bar.
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        restaurant_name_text = findViewById(R.id.restaurant_name);
        subtotal = findViewById(R.id.subtotal);
        tax = findViewById(R.id.tax);
        deliveryfee = findViewById(R.id.deliveryfee);
        total = findViewById(R.id.total);

        restaurant_name_text.setText(food_page.CartClass.restname);
        prepData();
        initRecyclerView();
    }

    public int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }

    //places the order, calls setfinalorder in cartclass and starts the next activity
    public void placeOrder(View view){
        Toast.makeText(this, "Order has been placed under username: " + MainActivity.username, Toast.LENGTH_SHORT).show();
        food_page.CartClass.setFinalOrder(finalprice);
        Intent intent = new Intent(this, order_complete.class);
        startActivity(intent);
        finish();
    }

    //prepares the prices and other info
    public void prepData(){
        subtotalnum = 0;
        for(int i = 0; i < food_page.CartClass.foodprices.size(); i++)
            subtotalnum += food_page.CartClass.foodprices.get(i);

        subtotal.setText(String.valueOf(subtotalnum) + " CAD");
        double taxnum = subtotalnum*0.05;
        BigDecimal bd = new BigDecimal(taxnum).setScale(2, RoundingMode.HALF_UP);

        double deliveryfeenum = subtotalnum * 0.07;
        BigDecimal bds = new BigDecimal(deliveryfeenum).setScale(2, RoundingMode.HALF_UP);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        finalprice = Double.parseDouble(df.format(subtotalnum + bd.doubleValue() + bds.doubleValue()));

        tax.setText(String.valueOf(bd.doubleValue()) + " CAD");
        deliveryfee.setText(String.valueOf(bds.doubleValue()) + " CAD");

        total.setText(String.valueOf(df.format(finalprice)) + " CAD");
    }

    //recycler view to display the cart
    private void initRecyclerView(){
        //Cuisine recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView3 = findViewById(R.id.orderrecycler);
        RecyclerCart LocalAdapter = new RecyclerCart(food_page.CartClass.cart, this, food_page.CartClass.cartid);
        LocalrecyclerView3.setAdapter(LocalAdapter);
        LocalrecyclerView3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }

    public void goback(View view){
        finish();
    }

    public void endActivity(){
        finish();
    }


}