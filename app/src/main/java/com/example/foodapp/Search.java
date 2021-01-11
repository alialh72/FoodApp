package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.foodapp.RecyclerViews.RecyclerSearch;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapter;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterLocalFavs;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterMain;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Search extends AppCompatActivity {

    private static final String TAG = "";
    private ArrayList<String> restaurantname = new ArrayList<String>();
    private HashMap<String, String> restaurantimage = new HashMap<>();
    private HashMap<String, Double> ratings = new HashMap<>();
    private HashMap<String, Integer> pricerange = new HashMap<>();
    private String currentUsername, currentFullname, currentEmail;
    private boolean loggedin = false;
    private MainActivity mainActivity = new MainActivity();

    private ListMultimap<String, String> CuisineTags = ArrayListMultimap.create();
    private ListMultimap<String, String> MenuList = ArrayListMultimap.create();
    private EditText searchbox;

    private RecyclerSearch LocalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(getIntent().getStringExtra("USERNAME") != null) {
            currentUsername = getIntent().getStringExtra("USERNAME");
            loggedin = getIntent().getExtras().getBoolean("LOGGEDIN");
            Log.d(TAG, "onCreate: logged in: " + loggedin);
            Log.d(TAG, "onCreate: USERNAME: " + currentUsername);
            if (loggedin == true){
                try {
                    mainActivity.mGetInfo.mGet(currentUsername);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "onCreate: currentUsername: " + Arrays.toString(mainActivity.mGetInfo.result));
                currentFullname = mainActivity.mGetInfo.result[0];
                currentEmail = mainActivity.mGetInfo.result[1];
            }
        }

        restaurantname = (ArrayList) getIntent().getSerializableExtra("RESTAURANT_NAME");
        restaurantimage = (HashMap<String, String>) getIntent().getSerializableExtra("RESTAURANT_IMAGE");

        CuisineTags = (ListMultimap) getIntent().getSerializableExtra("CUISINETAGS");
        MenuList = (ListMultimap) getIntent().getSerializableExtra("MENULIST");
        ratings = (HashMap) getIntent().getSerializableExtra("RATINGS");
        pricerange = (HashMap) getIntent().getSerializableExtra("PRICERANGE");

        initRecyclerView();
        searchbox = findViewById(R.id.SearchBox);
        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text){
        ArrayList<String> filteredList = new ArrayList<String>();

        for (String item : restaurantname){
            if(item.toLowerCase().contains(text.toLowerCase())){
                Log.d(TAG, "filter: item: " + item);
                filteredList.add(item);
            }
        }
        LocalAdapter.filterList(filteredList, fixImageArray(filteredList));
    }

    public void setRestaurant(String CurrentRestaurantName){
        String CurrentRestaurantImage = restaurantimage.get(CurrentRestaurantName);
        double currentRating = ratings.get(CurrentRestaurantName);
        int currentPriceRange = pricerange.get(CurrentRestaurantName);

        Intent intent = new Intent(getBaseContext(), RestaurantPage.class);
        intent.putExtra("RESTAURANT_NAME", CurrentRestaurantName);
        intent.putExtra("RESTAURANT_IMAGE", CurrentRestaurantImage);
        intent.putExtra("CUISINETAGS",(Serializable) CuisineTags);
        intent.putExtra("MENULIST",(Serializable) MenuList);

        if (loggedin == true){
            intent.putExtra("LOGGEDIN", loggedin);
            intent.putExtra("USERNAME", currentUsername);
        }

        intent.putExtra("RATINGS",currentRating);
        intent.putExtra("PRICERANGE",currentPriceRange);
        startActivity(intent);
        Log.d(TAG, "openRestaurantPage: new intent has been started");
    }

    //Calls the recyclerviewadapter
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        ArrayList <String> empty = new ArrayList<String>();

        RecyclerView LocalrecyclerView = findViewById(R.id.RecyclerViewSearch);
        LocalAdapter = new RecyclerSearch(empty, fixImageArray(empty),this);
        LocalrecyclerView.setAdapter(LocalAdapter);
        LocalrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }

    private ArrayList<String> fixImageArray(ArrayList<String> Restaurants){
        ArrayList<String> images = new ArrayList<String>();
        int i = 0;
        while (images.size() < Restaurants.size()){
            images.add(restaurantimage.get(Restaurants.get(i)));
            i +=1;
        }
        return images;
    }
}