package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapter;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterLocalFavs;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterMain;
import com.example.foodapp.RecyclerViews.RecyclerViewMenu;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantPage extends AppCompatActivity {

    private static final String TAG = "";

    private ListMultimap<String, String> CuisineTags = ArrayListMultimap.create();
    private ListMultimap<String, String> MenuList = ArrayListMultimap.create();

    private String restaurantname, restaurantimage;
    private double ratings;
    private int price;

    private TextView restaurant_title, cuisine_text, rating_text, price_text;
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
        price_text = findViewById(R.id.PriceRange);



        restaurantname = getIntent().getStringExtra("RESTAURANT_NAME");
        restaurantimage = getIntent().getStringExtra("RESTAURANT_IMAGE");

        CuisineTags = (ListMultimap) getIntent().getSerializableExtra("CUISINETAGS");
        MenuList = (ListMultimap) getIntent().getSerializableExtra("MENULIST");

        ratings = getIntent().getExtras().getDouble("RATINGS");
        price = getIntent().getExtras().getInt("PRICE");

        setText();





    }


    private void setText(){
        //PRICE RANGE
        String mPriceText = (Integer.toString(price)).replace("1","$").replace("2","$$").replace("3","$$$"); //replaces the integer value of the price range with a dollar sign
        price_text.setText(mPriceText); //sets the price range

        //RATINGS
        String mRating = String.valueOf(ratings) + " ☆"; //adds a star to the end of the rating
        rating_text.setText(mRating); //sets the rating

        //Loads restaurant title
        restaurant_title.setText(restaurantname);

        //Loads restaurant tags
        ArrayList<String> Tags = new ArrayList<>();
        Tags.addAll(CuisineTags.get(restaurantname)); //turns the collection into an arraylist
        String answer = String.join(", ", Tags); //turns the arraylist into a string because an arraylist cant be set as text
        cuisine_text.setText(answer);

        //Sets up the menu bar
        ArrayList<String> MenuBar = new ArrayList<>();
        MenuBar.addAll(MenuList.get(restaurantname)); //turns the collection into an arraylist
        initRecyclerView(MenuBar);


        //Loads restaurant image
        Glide.with(this)
                .asBitmap()
                .load(restaurantimage)
                .into(restaurant_image);
    }

    private void initRecyclerView(ArrayList<String> mMenuBar){
        //Local favorites recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView = findViewById(R.id.Menu);
        RecyclerViewMenu LocalAdapter = new RecyclerViewMenu(mMenuBar, this);
        LocalrecyclerView.setAdapter(LocalAdapter);
        LocalrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));



    }
}