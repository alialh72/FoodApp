package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapter;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterLocalFavs;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterMain;
import com.example.foodapp.RecyclerViews.RecyclerViewMenu;
import com.example.foodapp.RecyclerViews.RecyclerViewRest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class RestaurantPage extends AppCompatActivity {

    private static final String TAG = "";

    private ListMultimap<String, String> CuisineTags = ArrayListMultimap.create();
    private ListMultimap<String, String> MenuList = ArrayListMultimap.create();

    private ListMultimap<String, Integer> FoodCategory = ArrayListMultimap.create();


    private ListMultimap<String, Integer> RestFood = ArrayListMultimap.create();

    private HashMap<Integer, String> FoodId = new HashMap<Integer, String>();
    private HashMap<Integer, String> FoodDescriptions = new HashMap<Integer, String>();
    private HashMap<Integer, String> FoodImgs = new HashMap<Integer, String>();
    private HashMap<Integer, Double> FoodPrice = new HashMap<Integer, Double>();

    private String restaurantname, restaurantimage;

    private String currentUsername, currentFullname, currentEmail;
    private boolean loggedin = false;
    private double ratings;
    private int price;
    private MainActivity mainActivity = new MainActivity();

    private TextView restaurant_title, cuisine_text, rating_text, price_text, food_category_title, textty;
    private ImageView restaurant_image;

    //----------------XML-------------------------
    private SearchView searchBar;
    private DrawerLayout drawerLayout;
    private TextView emailtext, usernametext, logintext;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

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

        Log.d(TAG, "onCreate: new page entered");

        restaurant_title = findViewById(R.id.restaurant_title);
        restaurant_image = findViewById(R.id.restaurant_image);
        cuisine_text = findViewById(R.id.CuisineType);
        rating_text = findViewById(R.id.Rating);
        price_text = findViewById(R.id.PriceRange);
        price_text = findViewById(R.id.PriceRange);
        food_category_title = findViewById(R.id.titlecategory);

        //searchBar = findViewById(R.id.RestaurantSearch);
        drawerLayout = findViewById(R.id.drawerLayout);
        emailtext = findViewById(R.id.emailfield);
        usernametext = findViewById(R.id.textView4);
        logintext = findViewById(R.id.login);



        restaurantname = getIntent().getStringExtra("RESTAURANT_NAME");
        restaurantimage = getIntent().getStringExtra("RESTAURANT_IMAGE");

        CuisineTags = (ListMultimap) getIntent().getSerializableExtra("CUISINETAGS");
        MenuList = (ListMultimap) getIntent().getSerializableExtra("MENULIST");
        ratings = getIntent().getExtras().getDouble("RATINGS");
        price = getIntent().getExtras().getInt("PRICERANGE");

        getHashMapFromTextFile();
        setText();

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                if(loggedin == true){
                    if (emailtext.getText() != currentEmail && usernametext.getText() != currentFullname){
                        emailtext.setVisibility(View.VISIBLE);
                        emailtext.setText(currentEmail);
                        usernametext.setText(currentFullname);
                    }
                    logintext.setText("Sign Out");
                }
                else{
                    emailtext.setText("");
                    emailtext.setVisibility(View.GONE);
                    usernametext.setText("Guest");
                    logintext.setText("Log In / Sign Up");
                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) { }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) { }

            @Override
            public void onDrawerStateChanged(int newState) { }
        });

    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //nav drawer onclicks

    public void Home(View view){
        Log.d(TAG, "Home button clicked");
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("LOGGEDIN", loggedin);

        if (currentUsername != null){
            intent.putExtra("USERNAME", currentUsername);
        }
        startActivity(intent);

    }

    public void Search(View view){
        Log.d(TAG, "Search: Search button clicked");
    }

    public void Orders(View view){
        Log.d(TAG, "Orders: orders button clicked");
    }

    public void LogIn(View view){
        Log.d(TAG, "Log In button clicked");
        if (loggedin == true){
            mainActivity.setLoggedin(false);
            drawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), login.class);
            intent.putExtra("JUSTLOGGED", true);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(getBaseContext(), login.class);
            startActivity(intent);
            Log.d(TAG, "login button: new intent has been started");
        }

    }

    public void Click(View view){
        Log.d(TAG, "Clicked");
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

        switcheroo(MenuBar.get(0));

    }

    private void initRecyclerView(ArrayList<String> mMenuBar){
        //Local favorites recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView = findViewById(R.id.Menu);
        RecyclerViewMenu LocalAdapter = new RecyclerViewMenu(mMenuBar, this);
        LocalrecyclerView.setAdapter(LocalAdapter);
        LocalrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    }

    private void MenuRecycler(ArrayList<String> RestaurantList, ArrayList<Double> mPrices, ArrayList<String> FoodImg, ArrayList<String> Descriptions){
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView Menu = findViewById(R.id.Starters);
        RecyclerViewRest LocalAdapter = new RecyclerViewRest(RestaurantList,mPrices,FoodImg,Descriptions, this);
        Menu.setAdapter(LocalAdapter);
        Menu.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    public void switcheroo(String menu){
        ArrayList<Integer> FoodCat = new ArrayList<>(); //array list contains all foodids that are in the same category
        ArrayList<Integer> mRestaurant = new ArrayList<>(); //contains foodids
        ArrayList<String> RestaurantList = new ArrayList<>(); //contains food names
        ArrayList<String> FoodImg = new ArrayList<>(); //contains food imgs
        ArrayList<Double> Prices = new ArrayList<>(); //contains foodids
        ArrayList<String> Descriptions = new ArrayList<>(); //contains foodids

        FoodCat.addAll(FoodCategory.get(menu));
        mRestaurant.addAll(RestFood.get(restaurantname));
        mRestaurant.retainAll(FoodCat);
        Log.d(TAG, "switcheroo: mRestaurant: "+mRestaurant);

        int i =0;
        while (RestaurantList.size() < mRestaurant.size() && Prices.size() < mRestaurant.size() && FoodImg.size() < mRestaurant.size()){
            int fooditem = mRestaurant.get(i);
            Log.d(TAG, "switcheroo: fooditem: "+fooditem);
            RestaurantList.add(FoodId.get(fooditem));
            Prices.add(FoodPrice.get(fooditem));
            FoodImg.add(FoodImgs.get(fooditem));
            Descriptions.add(FoodDescriptions.get(fooditem));
            i += 1;
        }

        Log.d(TAG, "switcheroo: restlist: " + RestaurantList);

        MenuRecycler(RestaurantList, Prices, FoodImg, Descriptions);
        food_category_title.setText(menu);
    }


    public void getHashMapFromTextFile(){

        getFood GetFood = new getFood();
        Log.d(TAG, "getHashMapFromTextFile: inside hashmapstart");
        try {
            String parts[] = GetFood.mGet("pee");
            Log.d(TAG, "getHashMapFromTextFile: parts: "+parts);
            String parts2[] = new String[0];
            String line;
            int i;
            String x;
            for (i = 0; i < parts.length; i++) {
                // accessing each element of array
                x = parts[i];
                Log.d(TAG, "calld: x: " + x);
                parts2 = x.split(";", 7);
                int mFoodId = Integer.parseInt(parts2[0]);
                String Dish = parts2[1];
                String Restaurant = parts2[2];
                String Category = parts2[3];
                double Price = Double.parseDouble(parts2[4]);
                String images = parts2[5];
                String description = parts2[6];
                FoodId.put(mFoodId, Dish);
                FoodCategory.put(Category, mFoodId);
                RestFood.put(Restaurant, mFoodId);
                FoodPrice.put(mFoodId,Price);
                FoodImgs.put(mFoodId, images);
                FoodDescriptions.put(mFoodId, description);
                Log.d(TAG, "call: parts2: " + parts2.toString());
            }
            Log.d(TAG, "onCreate: resukt: "+ Arrays.toString(GetFood.result));

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: foodid: "+FoodId);


    }
}