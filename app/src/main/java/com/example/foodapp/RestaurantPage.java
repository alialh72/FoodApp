package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.RecyclerViews.RecyclerViewMenu;
import com.example.foodapp.RecyclerViews.RecyclerViewRest;
import com.example.foodapp.nonactivityclasses.CartClass;

import org.apache.commons.lang3.text.WordUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class RestaurantPage extends AppCompatActivity {

    private static final String TAG = "";



    private String restaurantname, restaurantimage;

    private String currentUsername, currentFullname, currentEmail;
    private boolean loggedin = login.loggedin;
    private double ratings;
    private int price;
    private MainActivity mainActivity = new MainActivity();

    private TextView restaurant_title, cuisine_text, rating_text, price_text, food_category_title;
    private ImageView restaurant_image;

    //----------------XML-------------------------
    private SearchView searchBar;
    private DrawerLayout drawerLayout;
    private TextView emailtext, usernametext, logintext;

    private View decorView;

    CartClass mCartClass = food_page.CartClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);



        decorView = getWindow().getDecorView();
        decorView = getWindow().getDecorView();

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        if(login.loggedin == true) {
            if(!MainActivity.username.equals("Guest")){
                currentUsername = MainActivity.username;
                Log.d(TAG, "onCreate: logged in: " + loggedin);
                Log.d(TAG, "onCreate: USERNAME: " + currentUsername);
                if (loggedin == true){
                    try {
                        mainActivity.mGetInfo.mGet(MainActivity.username);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, "onCreate: currentUsername: " + Arrays.toString(mainActivity.mGetInfo.result));
                    currentFullname = mainActivity.mGetInfo.result[0];
                    currentEmail = mainActivity.mGetInfo.result[1];
                    Log.d(TAG, "onCreate: currentfullname: " +currentFullname);
                    Log.d(TAG, "onCreate: currentemail: "+currentEmail);
            }
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
        ratings = getIntent().getExtras().getDouble("RATINGS");
        price = getIntent().getExtras().getInt("PRICERANGE");

        //mCartClass.setRestaurant(restaurantname, this);

        setText();

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                if(login.loggedin == true){
                    if (emailtext.getText() != currentEmail && usernametext.getText() != currentFullname){
                        emailtext.setVisibility(View.VISIBLE);
                        emailtext.setText(currentEmail);
                        usernametext.setText(WordUtils.capitalize(currentFullname));
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
        finish();

    }

    public void yougot(View view){
        Toast.makeText(this, "lol u got played this doesnt do anything", Toast.LENGTH_SHORT).show();
    }

    public void startNextActivity(String foodname, String Description, String Image, double price, int foodids){
        Intent intent = new Intent(getBaseContext(), food_page.class);
        ArrayList<String> lchoice = MainActivity.FoodChoices.get(foodids);
        Log.d(TAG, "startNextActivity: lchoice: "+lchoice);

        intent.putExtra("FOODNAME", foodname);
        intent.putExtra("RESTAURANT", restaurantname);
        intent.putExtra("FOODID", foodids);
        intent.putExtra("DESCRIPTION", Description);
        intent.putExtra("IMAGE",Image);
        intent.putExtra("PRICE",price);
        intent.putExtra("CHOICES",lchoice);
        startActivity(intent);

        Log.d(TAG, "login button: new intent has been started");
    }

    public void Search(View view){
        Log.d(TAG, "Search: Search button clicked");
    }

    public void orderpage(View view){

        if(MainActivity.username.equals("Guest")){
            Toast.makeText(this, "You must sign in to view your orders!", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, prevorders.class);
            startActivity(intent);
            finish();
        }

    }

    public void LogIn(View view){
        Log.d(TAG, "Log In button clicked");
        if (login.loggedin == true){
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
        Tags.addAll(MainActivity.CuisineTags.get(restaurantname)); //turns the collection into an arraylist
        String answer = String.join(", ", Tags); //turns the arraylist into a string because an arraylist cant be set as text
        cuisine_text.setText(answer);

        //Sets up the menu bar
        ArrayList<String> MenuBar = new ArrayList<>();
        MenuBar.addAll(MainActivity.MenuList.get(restaurantname)); //turns the collection into an arraylist
        initRecyclerView(MenuBar);


        //Loads restaurant image
        Glide.with(this)
                .asBitmap()
                .load(restaurantimage)
                .into(restaurant_image);

        switcheroo(MenuBar.get(0));

    }

    public int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }

    private void initRecyclerView(ArrayList<String> mMenuBar){
        //Local favorites recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView = findViewById(R.id.Menu);
        RecyclerViewMenu LocalAdapter = new RecyclerViewMenu(mMenuBar, this);
        LocalrecyclerView.setAdapter(LocalAdapter);
        LocalrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    }

    private void MenuRecycler(List<String> RestaurantList, ArrayList<Double> mPrices, ArrayList<String> FoodImg, ArrayList<String> Descriptions, ArrayList<Integer> mRestaurant){
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView Menu = findViewById(R.id.Starters);
        RecyclerViewRest LocalAdapter = new RecyclerViewRest(RestaurantList,mPrices,FoodImg,Descriptions,mRestaurant, this);
        Menu.setAdapter(LocalAdapter);
        Menu.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    public void switcheroo(String menu){
        ArrayList<Integer> FoodCat = new ArrayList<>(); //array list contains all foodids that are in the same category
        ArrayList<Integer> mRestaurant = new ArrayList<>(); //contains foodids
        List<String> RestaurantList = new ArrayList<String>(); //contains food names
        ArrayList<String> FoodImg = new ArrayList<>(); //contains food imgs
        ArrayList<Double> Prices = new ArrayList<>(); //contains foodids
        ArrayList<String> Descriptions = new ArrayList<>(); //contains foodids

        FoodCat.addAll(MainActivity.FoodCategory.get(menu));
        mRestaurant.addAll(MainActivity.RestFood.get(restaurantname));
        mRestaurant.retainAll(FoodCat);
        Log.d(TAG, "switcheroo: mRestaurant: "+mRestaurant);

        int i =0;
        while (RestaurantList.size() < mRestaurant.size() && Prices.size() < mRestaurant.size() && FoodImg.size() < mRestaurant.size()){
            int fooditem = mRestaurant.get(i);
            Log.d(TAG, "switcheroo: fooditem: "+fooditem);

            RestaurantList.add(MainActivity.FoodId.get(fooditem));
            Prices.add(MainActivity.FoodPrice.get(fooditem));
            FoodImg.add(MainActivity.FoodImgs.get(fooditem));
            Descriptions.add(MainActivity.FoodDescriptions.get(fooditem));
            i += 1;
        }

        Log.d(TAG, "switcheroo: restlist: " + RestaurantList);

        MenuRecycler(RestaurantList, Prices, FoodImg, Descriptions, mRestaurant);
        food_category_title.setText(menu);
    }



    public void SearchBar(View view){
        Searching(this);
    }

    public void Searching(Context context){
        Intent intent = new Intent(context, Search.class);
        intent.putExtra("RESTAURANT_NAME", MainActivity.mRestaurantName);
        intent.putExtra("RESTAURANT_IMAGE", MainActivity.RestaurantImagesMap);
        intent.putExtra("CUISINETAGS",(Serializable) MainActivity.CuisineTags);
        intent.putExtra("MENULIST",(Serializable) MainActivity.MenuList);

        if (loggedin == true){
            intent.putExtra("LOGGEDIN", loggedin);
            intent.putExtra("USERNAME", currentUsername);
        }

        intent.putExtra("RATINGS",(Serializable) MainActivity.Ratings);
        intent.putExtra("PRICERANGE",(Serializable) MainActivity.PriceRange);

        startActivity(intent);

        finish();
    }


    public void CartGo(View view){
        if (food_page.CartClass.cart.size() == 0){
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, CartPage.class);
            startActivity(intent);
            finish();
        }
    }


}