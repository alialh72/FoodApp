package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RestaurantPage extends AppCompatActivity {

    private static final String TAG = "";
    private TextView restaurant_title;
    private ImageView restaurant_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

        Log.d(TAG, "onCreate: new page entered");

        restaurant_title = findViewById(R.id.restaurant_title);
        restaurant_image = findViewById(R.id.restaurant_image);



        String restaurantname = getIntent().getStringExtra("RESTAURANT_NAME");
        String restaurantimage = getIntent().getStringExtra("RESTAURANT_IMAGE");

        restaurant_title.setText(restaurantname);

        Glide.with(this)
                .asBitmap()
                .load(restaurantimage)
                .into(restaurant_image);


    }
}