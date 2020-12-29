package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";
    private ArrayList<String> mCuisineText = new ArrayList<>();
    private ArrayList<String> mCuisineImages = new ArrayList<>();

    private ArrayList<String> mRestaurantName = new ArrayList<>();
    private ArrayList<String> mRestaurantImages = new ArrayList<>();

    private ArrayList<String> mRestaurantNameSub = new ArrayList<>();
    private ArrayList<String> mRestaurantImagesSub = new ArrayList<>();

    private ArrayList<String> mRestaurantNameSub2 = new ArrayList<>();
    private ArrayList<String> mRestaurantImagesSub2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView v = (RecyclerView) findViewById(R.id.restaurantlist3);
        v.setOverScrollMode(View.OVER_SCROLL_NEVER);;

        initImageBitmaps();
    }

    //sets the song names, images and artists
    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");

        //Cuisine 1
        mCuisineImages.add("https://i.imgur.com/Ux2XNYA.jpg");
        mCuisineText.add("Burgers");

        //Cuisine 2
        mCuisineImages.add("https://i.imgur.com/isuqMb3.jpg");
        mCuisineText.add("Pizza");

        //Cuisine 3
        mCuisineImages.add("https://imgur.com/uQAk85E.jpg");
        mCuisineText.add("Sushi");

        //Cuisine 4
        mCuisineImages.add("https://imgur.com/uQAk85E.jpg");
        mCuisineText.add("Chinese");

        //Cuisine 5
        mCuisineImages.add("https://imgur.com/uQAk85E.jpg");
        mCuisineText.add("Fast Food");

        //Cuisine 6
        mCuisineImages.add("https://imgur.com/uQAk85E.jpg");
        mCuisineText.add("Italian");


        //Restaurant Local Favs

        mRestaurantImages.add("https://i.imgur.com/Ux2XNYA.jpg");
        mRestaurantName.add("McDonald's");

        mRestaurantImages.add("https://i.imgur.com/isuqMb3.jpg");
        mRestaurantName.add("Domino's");

        mRestaurantImages.add("https://imgur.com/uQAk85E.jpg");
        mRestaurantName.add("Pizza Hut");

        mRestaurantImages.add("https://imgur.com/uQAk85E.jpg");
        mRestaurantName.add("Burger King");

        mRestaurantImages.add("https://imgur.com/uQAk85E.jpg");
        mRestaurantName.add("Cactus Club");

        mRestaurantImages.add("https://imgur.com/uQAk85E.jpg");
        mRestaurantName.add("Earl's");

        mRestaurantImages.add("https://imgur.com/uQAk85E.jpg");
        mRestaurantName.add("Subway");

        mRestaurantImages.add("https://imgur.com/uQAk85E.jpg");
        mRestaurantName.add("Nobu");

        mRestaurantImages.add("https://imgur.com/uQAk85E.jpg");
        mRestaurantName.add("Din Tai Fung");

        mRestaurantImages.add("https://imgur.com/uQAk85E.jpg");
        mRestaurantName.add("Earl's");

        for (int t = 0; t < 5; t++){
            mRestaurantNameSub.add(mRestaurantName.get(t));
            mRestaurantImagesSub.add(mRestaurantImages.get(t));
            System.out.println(mRestaurantNameSub);
        }

        for (int l = 1; l < 9; l+=2){
            mRestaurantNameSub2.add(mRestaurantName.get(l));
            mRestaurantImagesSub2.add(mRestaurantImages.get(l));
            System.out.println(mRestaurantNameSub);
        }


        initRecyclerView();
    }


    //Calls the recyclerviewadapter
    private void initRecyclerView(){
        //Cuisine recycler
        Log.d(TAG, "initRecyclerView: init recyclerview");
        RecyclerView recyclerView = findViewById(R.id.cuisinelist);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mCuisineText, mCuisineImages, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        //Local favorites recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView = findViewById(R.id.restaurantlist);
        RecyclerViewAdapterLocalFavs LocalAdapter = new RecyclerViewAdapterLocalFavs(mRestaurantNameSub, mRestaurantImagesSub, this);
        LocalrecyclerView.setAdapter(LocalAdapter);
        LocalrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        //Top picks recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView2 = findViewById(R.id.restaurantlist2);
        RecyclerViewAdapterTopPicks LocalAdapter2 = new RecyclerViewAdapterTopPicks(mRestaurantNameSub2, mRestaurantImagesSub2, this);
        LocalrecyclerView2.setAdapter(LocalAdapter2);
        LocalrecyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        //Restaurant List
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerViewMain = findViewById(R.id.restaurantlist3);
        RecyclerViewAdapterMain LocalAdapter3 = new RecyclerViewAdapterMain(mRestaurantName, mRestaurantImages, this);
        LocalrecyclerViewMain.setAdapter(LocalAdapter3);
        LocalrecyclerViewMain.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }

}