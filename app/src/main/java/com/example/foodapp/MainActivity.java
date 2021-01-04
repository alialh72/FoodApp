package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapter;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterLocalFavs;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterMain;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import com.example.foodapp.RecyclerViews.RecyclerViewAdapter;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //------------vars--------------

    //strings
    private String CurrentRestaurantName = "";
    private String CurrentRestaurantImage = "";

    private String currentUsername, currentFullname, currentEmail;
    private Double currentRating;
    private Integer currentPrice;
    private String mResult;

    private static final String TAG = "";


    //booleans
    private boolean loggedin;

    //arrays and maps
    private ArrayList<String> mCuisineText = new ArrayList<>();
    private ArrayList<String> mCuisineImages = new ArrayList<>();

    private ArrayList<String> mRestaurantName = new ArrayList<>();
    private ArrayList<String> mRestaurantImages = new ArrayList<>();
    private HashMap<String, String> RestaurantImagesMap = new HashMap<>();

    private ArrayList<String> mRestaurantNameSub = new ArrayList<>();
    private ArrayList<String> mRestaurantImagesSub = new ArrayList<>();

    private ArrayList<String> mRestaurantNameSub2 = new ArrayList<>();
    private ArrayList<String> mRestaurantImagesSub2 = new ArrayList<>();

    private HashMap<String, Double> Ratings = new HashMap<String, Double>();
    private HashMap<String, Integer> PriceRange = new HashMap<String, Integer>();

    private ListMultimap<String, String> CuisineTags = ArrayListMultimap.create();
    private ListMultimap<String, String> ReverseCuisines = ArrayListMultimap.create();

    private ArrayList<String> RandomRestaurant = new ArrayList<>();
    private ArrayList<String> RandomRestaurantImages = new ArrayList<>();


    private Random generator = new Random();



    //----------------XML-------------------------
    private SearchView searchBar;
    private DrawerLayout drawerLayout;
    private TextView emailtext, usernametext, logintext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent().getExtras() != null) {
            currentUsername = getIntent().getStringExtra("USERNAME");
            loggedin = getIntent().getExtras().getBoolean("LOGGEDIN");
            Log.d(TAG, "onCreate: logged in: " + loggedin);
            Log.d(TAG, "onCreate: USERNAME: " + currentUsername);
            if (loggedin == true){
                mGet(currentUsername);
            }
        }


        //assign xml
        searchBar = findViewById(R.id.RestaurantSearch);
        drawerLayout = findViewById(R.id.drawerLayout);
        emailtext = findViewById(R.id.textView6);
        usernametext = findViewById(R.id.textView4);
        logintext = findViewById(R.id.login);

        Log.d(TAG, "onCreate: result " + mResult);

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                if(loggedin == true){
                    if (emailtext.getText() != currentEmail && usernametext.getText() != currentFullname){
                        emailtext.setText(currentEmail);
                        usernametext.setText(currentFullname);
                    }
                    logintext.setText("Sign Out");
                }
                else{
                    emailtext.setText("");
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

        initImageBitmaps();
    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
        Log.d(TAG, "ClickMenu: testing " + mResult);
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
            loggedin = false;
            drawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show();
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

    private void mGet(String username){
        if (!username.equals("")) {

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[1];
                    field[0] = "username";

                    //Creating array for data
                    String[] data = new String[1];
                    data[0] = username;


                    PutData putData = new PutData("http://192.168.1.76/FoodAppLogin/getinfo.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();

                            if (result.equals("Login Success")){
                                Log.d(TAG, "run: how tf did this happen");
                                Log.d(TAG, "run: result: "+ result);
                            }

                            else{
                                mResult = result;
                                String[] parts = mResult.split("-");
                                currentFullname = parts[0];
                                currentEmail = parts[1];
                                Log.d(TAG, "run: mResult: " +mResult);

                            }


                        }
                    }
                }

            });
        }
    }




    //recyclerview
    private void initImageBitmaps(){


        Log.d(TAG, "initImageBitmaps: preparing bitmaps");

        //---------------------------List of Cuisines--------------------------

        //Cuisine 1
        mCuisineImages.add("https://i.imgur.com/Ux2XNYA.jpg");
        mCuisineText.add("American");

        //Cuisine 2
        mCuisineImages.add("https://i.imgur.com/isuqMb3.jpg");
        mCuisineText.add("Pizza");

        //Cuisine 3
        mCuisineImages.add("https://imgur.com/uQAk85E.jpg");
        mCuisineText.add("Chicken");

        //Cuisine 4
        mCuisineImages.add("https://imgur.com/uQAk85E.jpg");
        mCuisineText.add("Chinese");

        //Cuisine 5
        mCuisineImages.add("https://imgur.com/uQAk85E.jpg");
        mCuisineText.add("Asian");

        //Cuisine 6
        mCuisineImages.add("https://imgur.com/uQAk85E.jpg");
        mCuisineText.add("Italian");

        //cuisine 7
        mCuisineImages.add("https://imgur.com/uQAk85E.jpg");
        mCuisineText.add("Mexican");



        //-------------------------Restaurants---------------------------------------

        mRestaurantImages.add("https://imgur.com/pr1IZEq.jpg");
        RestaurantImagesMap.put("Sushi Counter", "https://imgur.com/pr1IZEq.jpg"); //for accessibility later

        mRestaurantName.add("Sushi Counter");
        CuisineTags.put("Sushi Counter", "Asian");
        CuisineTags.put("Sushi Counter", "Japanese");

        Ratings.put("Sushi Counter", 4.5);

        PriceRange.put("Sushi Counter", 2);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/1rcwTbY.jpg");
        RestaurantImagesMap.put("Olivier’s Bistro", "https://imgur.com/1rcwTbY.jpg");

        mRestaurantName.add("Olivier's Bistro");
        CuisineTags.put("Olivier's Bistro", "French");

        Ratings.put("Olivier's Bistro", 4.7);

        PriceRange.put("Olivier's Bistro", 3);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/Guf58Hz.jpg");
        RestaurantImagesMap.put("Salt Burger", "https://imgur.com/Guf58Hz.jpg");

        mRestaurantName.add("Salt Burger");
        CuisineTags.put("Salt Burger", "American");
        CuisineTags.put("Salt Burger", "Burger");

        Ratings.put("Salt Burger", 3.8);

        PriceRange.put("Salt Burger", 1);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/S55r8kF.jpg");
        RestaurantImagesMap.put("Red Dragon", "https://imgur.com/S55r8kF.jpg");

        mRestaurantName.add("Red Dragon");
        CuisineTags.put("Red Dragon", "Chinese");
        CuisineTags.put("Red Dragon", "Asian");

        Ratings.put("Red Dragon", 3.5);

        PriceRange.put("Red Dragon", 1);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/WdqIPhx.jpg");
        RestaurantImagesMap.put("Basilico", "https://imgur.com/WdqIPhx.jpg");

        mRestaurantName.add("Basilico");
        CuisineTags.put("Basilico", "Italian");
        CuisineTags.put("Basilico", "Pizza");

        Ratings.put("Basilico", 3.9);

        PriceRange.put("Basilico", 3);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/IB0U9LQ.jpg");
        RestaurantImagesMap.put("Tolden Wings", "https://imgur.com/IB0U9LQ.jpg");

        mRestaurantName.add("Tolden Wings");
        CuisineTags.put("Tolden Wings", "Chicken");
        CuisineTags.put("Tolden Wings", "American");

        Ratings.put("Tolden Wings", 4.2);

        PriceRange.put("Tolden Wings", 2);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/a4yuKlm.jpg");
        RestaurantImagesMap.put("Think Noodles", "https://imgur.com/a4yuKlm.jpg");

        mRestaurantName.add("Think Noodles");
        CuisineTags.put("Think Noodles", "Chinese");
        CuisineTags.put("Think Noodles", "Asian");
        CuisineTags.put("Think Noodles", "Japanese");

        Ratings.put("Think Noodles", 4.7);

        PriceRange.put("Think Noodles", 1);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/GnPPlNm.jpg");
        RestaurantImagesMap.put("Hermanos", "https://imgur.com/GnPPlNm.jpg");

        mRestaurantName.add("Hermanos");
        CuisineTags.put("Hermanos", "Mexican");

        Ratings.put("Hermanos", 4.3);

        PriceRange.put("Hermanos", 1);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/5di0Vdd.jpg");
        RestaurantImagesMap.put("Tequila & Tacos", "https://imgur.com/5di0Vdd.jpg");

        mRestaurantName.add("Tequila & Tacos");
        CuisineTags.put("Tequila & Tacos", "Mexican");

        Ratings.put("Tequila & Tacos", 4.1);

        PriceRange.put("Tequila & Tacos", 3);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/nSIiI4J.jpg");
        RestaurantImagesMap.put("Mamma Mia!", "https://imgur.com/nSIiI4J.jpg");

        mRestaurantName.add("Mamma Mia!");
        CuisineTags.put("Mamma Mia!", "Italian");
        CuisineTags.put("Mamma Mia!", "Pizza");

        Ratings.put("Mamma Mia!", 4.6);

        PriceRange.put("Mamma Mia!", 2);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/ogmitVa.jpg");
        RestaurantImagesMap.put("Falafel Beirut", "https://imgur.com/ogmitVa.jpg");

        mRestaurantName.add("Falafel Beirut");
        CuisineTags.put("Falafel Beirut", "Arabic");

        Ratings.put("Falafel Beirut", 4.9);

        PriceRange.put("Falafel Beirut", 2);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/ZVsNP0M.jpg");
        RestaurantImagesMap.put("Amo Shawarma", "https://imgur.com/ZVsNP0M.jpg");

        mRestaurantName.add("Amo Shawarma");
        CuisineTags.put("Amo Shawarma", "Arabic");

        Ratings.put("Amo Shawarma", 4.0);

        PriceRange.put("Amo Shawarma", 2);

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/kkin29T.jpg");
        RestaurantImagesMap.put("CHKN", "https://imgur.com/kkin29T.jpg");

        mRestaurantName.add("CHKN");
        CuisineTags.put("CHKN", "Chicken");
        CuisineTags.put("CHKN", "American");

        Ratings.put("CHKN", 1.9);

        PriceRange.put("CHKN", 1);

        //Generates 2 lists for the main menu

        for (int t = 0; t < 8; t++){
            mRestaurantNameSub.add(mRestaurantName.get(t));
            mRestaurantImagesSub.add(mRestaurantImages.get(t));
            System.out.println(mRestaurantNameSub);
        }

        for (int l = 1; l < 9; l+=2){
            mRestaurantNameSub2.add(mRestaurantName.get(l));
            mRestaurantImagesSub2.add(mRestaurantImages.get(l));
            System.out.println(mRestaurantNameSub);
        }

        //Hashmaps

        Multimaps.invertFrom(CuisineTags, ReverseCuisines); //Creates a reverse multimap for easy access later
        Log.d(TAG, "initImageBitmaps: reversemap " + ReverseCuisines);
        Log.d(TAG, "initImageBitmaps: map "+CuisineTags);


        initRecyclerView();

    }


    //Calls the recyclerviewadapter
    private void initRecyclerView(){
        randomCuisine();
        //Cuisine recycler
        Log.d(TAG, "initRecyclerView: init recyclerview");
        RecyclerView recyclerView = findViewById(R.id.cuisinelist);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mCuisineText, mCuisineImages, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        //Local favorites recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView = findViewById(R.id.restaurantlist);
        RecyclerViewAdapterLocalFavs LocalAdapter = new RecyclerViewAdapterLocalFavs(mRestaurantNameSub, mRestaurantImagesSub, this, CuisineTags, Ratings);
        LocalrecyclerView.setAdapter(LocalAdapter);
        LocalrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        //Top picks recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView2 = findViewById(R.id.restaurantlist2);
        LocalAdapter = new RecyclerViewAdapterLocalFavs(mRestaurantNameSub2, mRestaurantImagesSub2, this, CuisineTags, Ratings);
        LocalrecyclerView2.setAdapter(LocalAdapter);
        LocalrecyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        //Restaurant List
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerViewMain = findViewById(R.id.cuisinerestaurantlist);
        LocalrecyclerViewMain.setNestedScrollingEnabled(false); //stops the recyclerview from scrolling
        RecyclerViewAdapterMain LocalAdapter3 = new RecyclerViewAdapterMain(RandomRestaurant, RandomRestaurantImages, this);
        LocalrecyclerViewMain.setAdapter(LocalAdapter3);
        LocalrecyclerViewMain.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


    }

    public void setCurrentRestaurant(String restaurantname, String restaurantimage){
        CurrentRestaurantName = restaurantname;
        CurrentRestaurantImage = restaurantimage;
        Log.d(TAG, "setCurrentRestaurant: current restaurant has been set as " + CurrentRestaurantName);
        openRestaurantPage();
    }

    private void openRestaurantPage(){
        Intent intent = new Intent(getBaseContext(), RestaurantPage.class);
        intent.putExtra("RESTAURANT_NAME", CurrentRestaurantName);
        intent.putExtra("RESTAURANT_IMAGE", CurrentRestaurantImage);
        intent.putExtra("CUISINETAGS",(Serializable) CuisineTags);

        getRatingPrice();

        intent.putExtra("RATINGS",currentRating);
        intent.putExtra("PRICE",currentPrice);
        startActivity(intent);
        Log.d(TAG, "openRestaurantPage: new intent has been started");
    }

    private void getRatingPrice(){
        currentRating = Ratings.get(CurrentRestaurantName);
        currentPrice = PriceRange.get(CurrentRestaurantName);
    }

    private void randomCuisine(){
        TextView CuisineTypeText = findViewById(R.id.cuisinetyperandom);
        ImageView CuisineTypeImage = findViewById(R.id.cuisineimagerandom);
        int randomIndex;
        randomIndex = generator.nextInt(mCuisineText.size());

        String selectedCuisine = mCuisineText.get(randomIndex);
        String selectedImage = mCuisineImages.get(randomIndex);



        RandomRestaurant.addAll(ReverseCuisines.get(selectedCuisine));

        while (RandomRestaurant.size() > 2){
            int index = RandomRestaurant.size() -1;
            RandomRestaurant.remove(index);
        }

        int mindex = 0;
        while(RandomRestaurantImages.size() != RandomRestaurant.size()){
            RandomRestaurantImages.add(RestaurantImagesMap.get(RandomRestaurant.get(mindex)));
            mindex += 1;
        }

        Log.d(TAG, "randomCuisine: cuisine selected: " +selectedCuisine );
        Log.d(TAG, "randomCuisine: restaurant: "+ RandomRestaurant);

        CuisineTypeText.setText(selectedCuisine);
        Glide.with(this)
                .asBitmap()
                .load(selectedImage)
                .into(CuisineTypeImage);

    }

}