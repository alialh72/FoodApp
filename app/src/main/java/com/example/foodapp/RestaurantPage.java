package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantPage extends AppCompatActivity {

    private static final String TAG = "";

    private ListMultimap<String, String> CuisineTags = ArrayListMultimap.create();

    private double ratings;
    private int price;

    private TextView restaurant_title, cuisine_text, rating_text;
    private ImageView restaurant_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

        Log.d(TAG, "onCreate: new page entered");

        restaurant_title = findViewById(R.id.restaurant_title);
        restaurant_image = findViewById(R.id.restaurant_image);
        cuisine_text = findViewById(R.id.CuisineType);
        rating_text = findViewById(R.id.Rating);



        String restaurantname = getIntent().getStringExtra("RESTAURANT_NAME");
        String restaurantimage = getIntent().getStringExtra("RESTAURANT_IMAGE");

        CuisineTags = (ListMultimap) getIntent().getSerializableExtra("CUISINETAGS");
        ratings = getIntent().getExtras().getDouble("RATINGS");

        String mRating = String.valueOf(ratings) + " ☆";

        rating_text.setText(mRating);

        //Loads restaurant title
        restaurant_title.setText(restaurantname);

        //Loads restaurant tags
        ArrayList<String> Tags = new ArrayList<>();
        Tags.addAll(CuisineTags.get(restaurantname)); //turns the collection into an arraylist
        String answer = String.join(", ", Tags); //turns the arraylist into a string because an arraylist cant be set as text
        cuisine_text.setText(answer);

        //Loads restaurant image
        Glide.with(this)
                .asBitmap()
                .load(restaurantimage)
                .into(restaurant_image);


    }
}