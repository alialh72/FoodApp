package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapter;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterLocalFavs;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterMain;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String CurrentRestaurantName = "";
    private String CurrentRestaurantImage = "";



    private static final String TAG = "";
    private ArrayList<String> mCuisineText = new ArrayList<>();
    private ArrayList<String> mCuisineImages = new ArrayList<>();

    private ArrayList<String> mRestaurantName = new ArrayList<>();
    private ArrayList<String> mRestaurantImages = new ArrayList<>();
    private HashMap<String, String> RestaurantImagesMap = new HashMap<>();

    private ArrayList<String> mRestaurantNameSub = new ArrayList<>();
    private ArrayList<String> mRestaurantImagesSub = new ArrayList<>();

    private ArrayList<String> mRestaurantNameSub2 = new ArrayList<>();
    private ArrayList<String> mRestaurantImagesSub2 = new ArrayList<>();

    private HashMap<String, String> Ratings = new HashMap<String, String>();
    private HashMap<String, String> CuisineType = new HashMap<String, String>();
    private HashMap<String, Integer> PriceRange = new HashMap<String, Integer>();

    private ListMultimap<String, String> CuisineTags = ArrayListMultimap.create();
    private ListMultimap<String, String> ReverseCuisines = ArrayListMultimap.create();

    private ArrayList<String> RandomRestaurant = new ArrayList<>();
    private ArrayList<String> RandomRestaurantImages = new ArrayList<>();


    private Random generator = new Random();


    //XML
    private SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.RestaurantSearch);



        initImageBitmaps();
    }

    //sets the song names, images and artists
    private void initImageBitmaps(){


        Log.d(TAG, "initImageBitmaps: preparing bitmaps");

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

        mCuisineImages.add("https://imgur.com/uQAk85E.jpg");
        mCuisineText.add("Mexican");



        //Restaurants

        mRestaurantImages.add("https://imgur.com/pr1IZEq.jpg");
        RestaurantImagesMap.put("Sushi Counter", "https://imgur.com/pr1IZEq.jpg"); //for accessibility later

        mRestaurantName.add("Sushi Counter");
        CuisineTags.put("Sushi Counter", "Asian");
        CuisineTags.put("Sushi Counter", "Japanese");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/1rcwTbY.jpg");
        RestaurantImagesMap.put("Olivier’s Bistro", "https://imgur.com/1rcwTbY.jpg");

        mRestaurantName.add("Olivier's Bistro");
        CuisineTags.put("Olivier's Bistro", "French");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/Guf58Hz.jpg");
        RestaurantImagesMap.put("Salt Burger", "https://imgur.com/Guf58Hz.jpg");

        mRestaurantName.add("Salt Burger");
        CuisineTags.put("Salt Burger", "American");
        CuisineTags.put("Salt Burger", "Burger");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/S55r8kF.jpg");
        RestaurantImagesMap.put("Red Dragon", "https://imgur.com/S55r8kF.jpg");

        mRestaurantName.add("Red Dragon");
        CuisineTags.put("Red Dragon", "Chinese");
        CuisineTags.put("Red Dragon", "Asian");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/WdqIPhx.jpg");
        RestaurantImagesMap.put("Basilico", "https://imgur.com/WdqIPhx.jpg");

        mRestaurantName.add("Basilico");
        CuisineTags.put("Basilico", "Italian");
        CuisineTags.put("Basilico", "Pizza");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/IB0U9LQ.jpg");
        RestaurantImagesMap.put("Tolden Wings", "https://imgur.com/IB0U9LQ.jpg");

        mRestaurantName.add("Tolden Wings");
        CuisineTags.put("Tolden Wings", "Chicken");
        CuisineTags.put("Tolden Wings", "American");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/a4yuKlm.jpg");
        RestaurantImagesMap.put("Think Noodles", "https://imgur.com/a4yuKlm.jpg");

        mRestaurantName.add("Think Noodles");
        CuisineTags.put("Think Noodles", "Chinese");
        CuisineTags.put("Think Noodles", "Asian");
        CuisineTags.put("Think Noodles", "Japanese");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/GnPPlNm.jpg");
        RestaurantImagesMap.put("Hermanos", "https://imgur.com/GnPPlNm.jpg");

        mRestaurantName.add("Hermanos");
        CuisineTags.put("Hermanos", "Mexican");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/5di0Vdd.jpg");
        RestaurantImagesMap.put("Tequila & Tacos", "https://imgur.com/5di0Vdd.jpg");

        mRestaurantName.add("Tequila & Tacos");
        CuisineTags.put("Tequila & Tacos", "Mexican");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/nSIiI4J.jpg");
        RestaurantImagesMap.put("Mamma Mia!", "https://imgur.com/nSIiI4J.jpg");

        mRestaurantName.add("Mamma Mia!");
        CuisineTags.put("Mamma Mia!", "Italian");
        CuisineTags.put("Mamma Mia!", "Pizza");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/ogmitVa.jpg");
        RestaurantImagesMap.put("Falafel Beirut", "https://imgur.com/ogmitVa.jpg");

        mRestaurantName.add("Falafel Beirut");
        CuisineTags.put("Falafel Beirut", "Arabic");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/ZVsNP0M.jpg");
        RestaurantImagesMap.put("Amo Shawarma", "https://imgur.com/ZVsNP0M.jpg");

        mRestaurantName.add("Amo Shawarma");
        CuisineTags.put("Amo Shawarma", "Arabic");

        //-----------------------------

        mRestaurantImages.add("https://imgur.com/kkin29T.jpg");
        RestaurantImagesMap.put("CHKN", "https://imgur.com/kkin29T.jpg");

        mRestaurantName.add("CHKN");
        CuisineTags.put("CHKN", "Chicken");
        CuisineTags.put("CHKN", "American");

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


        //Ratings.put("England", "London");





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
        RecyclerViewAdapterLocalFavs LocalAdapter = new RecyclerViewAdapterLocalFavs(mRestaurantNameSub, mRestaurantImagesSub, this, CuisineTags);
        LocalrecyclerView.setAdapter(LocalAdapter);
        LocalrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        //Top picks recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView2 = findViewById(R.id.restaurantlist2);
        LocalAdapter = new RecyclerViewAdapterLocalFavs(mRestaurantNameSub2, mRestaurantImagesSub2, this, CuisineTags);
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
        startActivity(intent);
        Log.d(TAG, "openRestaurantPage: new intent has been started");
    }

    //public String getRestaurantInfo(String restaurantname){

    //}

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