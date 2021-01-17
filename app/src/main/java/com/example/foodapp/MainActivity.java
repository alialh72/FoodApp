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
import com.example.foodapp.RecyclerViews.RecyclerViewAdapter;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterLocalFavs;
import com.example.foodapp.RecyclerViews.RecyclerViewAdapterMain;
import com.example.foodapp.nonactivityclasses.getFood;
import com.example.foodapp.nonactivityclasses.getInfo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import org.apache.commons.lang3.text.WordUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    //------------vars--------------
    private static final String TAG = "";


    //strings
    private String CurrentRestaurantName = "";
    private String CurrentRestaurantImage = "";
    private String mResult;
    private String currentUsername, currentFullname, currentEmail;

    //Integers and doubles
    private Double currentRating;
    private Integer currentPriceRange, currentPrice;

    private String clickedtag="";
    private static String selectedCuisine;
    //booleans



    //---------------ARRRAYSSSSS AND MAPSSSSSS
    private ArrayList<String> mCuisineText = new ArrayList<>();
    private ArrayList<String> mCuisineImages = new ArrayList<>();

    public static ArrayList<String> mRestaurantName = new ArrayList<>();
    public static ArrayList<String> mRestaurantImages = new ArrayList<>();
    public static HashMap<String, String> RestaurantImagesMap = new HashMap<>();

    private ArrayList<String> mRestaurantNameSub = new ArrayList<>();
    private ArrayList<String> mRestaurantImagesSub = new ArrayList<>();

    private ArrayList<String> mRestaurantNameSub2 = new ArrayList<>();
    private ArrayList<String> mRestaurantImagesSub2 = new ArrayList<>();

    public static HashMap<String, Double> Ratings = new HashMap<String, Double>();
    public static HashMap<String, Integer> PriceRange = new HashMap<String, Integer>();
    public static ListMultimap<String, String> MenuList = ArrayListMultimap.create();
    public static ListMultimap<String, Integer> FoodCategory = ArrayListMultimap.create();
    public static ListMultimap<String, Integer> RestFood = ArrayListMultimap.create();
    public static HashMap<Integer, String> FoodId = new HashMap<Integer, String>();
    public static HashMap<Integer, String> FoodImgs = new HashMap<Integer, String>();
    public static HashMap<Integer, Double> FoodPrice = new HashMap<Integer, Double>();
    public static HashMap<Integer, String> FoodDescriptions = new HashMap<Integer, String>();
    public static HashMap<Integer, ArrayList<String>> FoodChoices = new HashMap<Integer, ArrayList<String>>();
    public static ListMultimap<String, String> CuisineTags = ArrayListMultimap.create();
    public ListMultimap<String, String> ReverseCuisines = ArrayListMultimap.create();
    public ArrayList<String> RandomRestaurant = new ArrayList<>();
    public static String username = "Guest";

    public ArrayList<String> RandomRestaurantImages = new ArrayList<>();

    boolean loggedin = login.loggedin;

    //Random Generator
    private Random generator = new Random();


    //Object
    public getInfo mGetInfo = new getInfo();



    //----------------XML-------------------------
    private SearchView searchBar;
    private DrawerLayout drawerLayout;
    private TextView emailtext, usernametext, logintext, welcometext;
    private ImageView background;
    private View decorView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: FoodId: " + FoodId.size());

        if (FoodId.size() == 0){
            getHashMapFromTextFile();
        }







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



        //assign xml
        //searchBar = findViewById(R.id.RestaurantSearch);
        drawerLayout = findViewById(R.id.drawerLayout);
        emailtext = findViewById(R.id.emailfield);
        usernametext = findViewById(R.id.textView4);
        logintext = findViewById(R.id.login);
        background = findViewById(R.id.imageBackground);
        welcometext = findViewById(R.id.welcometext);

        Glide.with(this)
                .asBitmap()
                .load("https://imgur.com/ODBDNkb.png")
                .into(background);


        Log.d(TAG, "onCreate: logged in: " + login.loggedin);
        if (login.loggedin == true){
            if(!username.equals("Guest")) {
                try {
                    mGetInfo.mGet(username);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onCreate: currentUsername: " + Arrays.toString(mGetInfo.result));
                currentFullname = mGetInfo.result[0];
                currentEmail = mGetInfo.result[1];
                String[] arr = currentFullname.split(" ");
                String cap = arr[0].substring(0, 1).toUpperCase() + arr[0].substring(1);
                welcometext.setText(cap);
                Log.d(TAG, "onCreate: Current Email: "+currentEmail);
                }

        }
        else{
            username = "Guest";
        }



        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                fixDrawer(loggedin);

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) { }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                fixDrawer(loggedin);

            }

            @Override
            public void onDrawerStateChanged(int newState) { }
        });




        initImageBitmaps();
    }

    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    public int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }

    public void fixDrawer(boolean loggedin){
        setLoggedin(loggedin);

        if (login.loggedin == true){
            if (emailtext.getText() != currentEmail && usernametext.getText() != currentFullname){
                Log.d(TAG, "fixDrawer: current email: "+currentEmail);
                usernametext.setText(WordUtils.capitalize(currentFullname));
                emailtext.setVisibility(View.VISIBLE);
                emailtext.setText(currentEmail);
            }
            logintext.setText("Sign Out");
        }
        else if(login.loggedin == false){
            emailtext.setText("");
            emailtext.setVisibility(View.GONE);
            usernametext.setText("Guest");
            logintext.setText("Log In / Sign Up");
            username = "Guest";
        }
    }

    public void setLoggedin(boolean loggedin){
        login.loggedin = loggedin;
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

    public void ReturnHome(View view){
    }

    //nav drawer onclicks

    public void Home(View view){
        Log.d(TAG, "Home button clicked");

    }

    public void Search(View view){
        Log.d(TAG, "Search: Search button clicked");
    }

    public void LogIn(View view){
        Log.d(TAG, "Log In button clicked");
        if (loggedin == true){
            fixDrawer(false);
            drawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), login.class);
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

    //recyclerview
    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");

        Log.d(TAG, "initImageBitmaps: afterobject");

        Log.d(TAG, "initImageBitmaps: aftermethod");
        Log.d(TAG, "onCreate: mapfromfile Foodid: " + FoodId);
        Log.d(TAG, "onCreate: RestFood: " + RestFood);
        Log.d(TAG, "onCreate: FoodCategory: " + FoodCategory);



        //---------------------------List of Cuisines--------------------------
        //Cuisine 1
        mCuisineImages.add("https://cdn.discordapp.com/attachments/713881124668964914/799517785801228338/burger.png");
        mCuisineText.add("American");

        //Cuisine 2
        mCuisineImages.add("https://media.discordapp.net/attachments/713881124668964914/799517794885435402/pizza.png?width=983&height=676");
        mCuisineText.add("Pizza");

        //Cuisine 3
        mCuisineImages.add("https://cdn.discordapp.com/attachments/713881124668964914/799517793116094464/wings.png");
        mCuisineText.add("Chicken");

        //Cuisine 4
        mCuisineImages.add("https://cdn.discordapp.com/attachments/713881124668964914/799517788397371412/china.png");
        mCuisineText.add("Chinese");

        //Cuisine 5
        mCuisineImages.add("https://cdn.discordapp.com/attachments/713881124668964914/799517774551842836/asian.png");
        mCuisineText.add("Asian");

        //Cuisine 6
        mCuisineImages.add("https://cdn.discordapp.com/attachments/713881124668964914/799517786300088380/pasta.png");
        mCuisineText.add("Italian");

        //cuisine 7
        mCuisineImages.add("https://cdn.discordapp.com/attachments/713881124668964914/799517787394539530/tacos.png");
        mCuisineText.add("Mexican");


        //Total number of dishes:
        //Food ids:
        //0-23 : starter dishes
        //24 - 53: main dishes
        //54-64: desserts
        //65-68: sushi
        //69 - 74: pizza

        if(CuisineTags.size() == 0){
            //-------------------------Restaurants---------------------------------------
            mRestaurantImages.add("https://imgur.com/pr1IZEq.jpg");
            RestaurantImagesMap.put("Sushi Counter", "https://imgur.com/pr1IZEq.jpg"); //for accessibility later

            mRestaurantName.add("Sushi Counter");


            //-----------------------------

            mRestaurantImages.add("https://imgur.com/1rcwTbY.jpg");
            RestaurantImagesMap.put("Olivier's Bistro", "https://imgur.com/1rcwTbY.jpg");

            mRestaurantName.add("Olivier's Bistro");


            //-----------------------------

            mRestaurantImages.add("https://imgur.com/Guf58Hz.jpg");
            RestaurantImagesMap.put("Salt Burger", "https://imgur.com/Guf58Hz.jpg");

            mRestaurantName.add("Salt Burger");

            //-----------------------------

            mRestaurantImages.add("https://imgur.com/S55r8kF.jpg");
            RestaurantImagesMap.put("Red Dragon", "https://imgur.com/S55r8kF.jpg");

            mRestaurantName.add("Red Dragon");


            //-----------------------------

            mRestaurantImages.add("https://imgur.com/WdqIPhx.jpg");
            RestaurantImagesMap.put("Basilico", "https://imgur.com/WdqIPhx.jpg");

            mRestaurantName.add("Basilico");


            //-----------------------------

            mRestaurantImages.add("https://imgur.com/IB0U9LQ.jpg");
            RestaurantImagesMap.put("Tolden Wings", "https://imgur.com/IB0U9LQ.jpg");

            mRestaurantName.add("Tolden Wings");


            //-----------------------------

            mRestaurantImages.add("https://imgur.com/a4yuKlm.jpg");
            RestaurantImagesMap.put("Think Noodles", "https://imgur.com/a4yuKlm.jpg");

            mRestaurantName.add("Think Noodles");


            //-----------------------------

            mRestaurantImages.add("https://imgur.com/GnPPlNm.jpg");
            RestaurantImagesMap.put("Hermanos", "https://imgur.com/GnPPlNm.jpg");

            mRestaurantName.add("Hermanos");


            //-----------------------------

            mRestaurantImages.add("https://imgur.com/nSIiI4J.jpg");
            RestaurantImagesMap.put("Mamma Mia!", "https://imgur.com/nSIiI4J.jpg");

            mRestaurantName.add("Mamma Mia!");


            //-----------------------------

            mRestaurantImages.add("https://imgur.com/ogmitVa.jpg");
            RestaurantImagesMap.put("Falafel Beirut", "https://imgur.com/ogmitVa.jpg");

            mRestaurantName.add("Falafel Beirut");


            //-----------------------------

            mRestaurantImages.add("https://media-cdn.tripadvisor.com/media/photo-s/08/da/96/f6/jerusalem-shawarma.jpg");
            RestaurantImagesMap.put("Amo Shawarma", "https://media-cdn.tripadvisor.com/media/photo-s/08/da/96/f6/jerusalem-shawarma.jpg");

            mRestaurantName.add("Amo Shawarma");


            //-----------------------------

            mRestaurantImages.add("https://imgur.com/kkin29T.jpg");
            RestaurantImagesMap.put("CHKN", "https://imgur.com/kkin29T.jpg");

            mRestaurantName.add("CHKN");


            //-----------------------------------

            mRestaurantImages.add("https://3di9nx2pw3s1jibo2g8ef7wo-wpengine.netdna-ssl.com/wp-content/uploads/2019/07/28364828246_933e432a4a_k-1-e1563468625653-1024x796.jpg");
            RestaurantImagesMap.put("Tequila & Tacos", "https://3di9nx2pw3s1jibo2g8ef7wo-wpengine.netdna-ssl.com/wp-content/uploads/2019/07/28364828246_933e432a4a_k-1-e1563468625653-1024x796.jpg");

            mRestaurantName.add("Tequila & Tacos");


            CuisineTags.put("Sushi Counter", "Asian");
            CuisineTags.put("Sushi Counter", "Japanese");

            Ratings.put("Sushi Counter", 4.5);

            PriceRange.put("Sushi Counter", 2);

            MenuList.put("Sushi Counter", "Starters");
            MenuList.put("Sushi Counter", "Mains");
            MenuList.put("Sushi Counter", "Sushi");
            MenuList.put("Sushi Counter", "Dessert");
            //--------------------------------------------------
            CuisineTags.put("Olivier's Bistro", "French");
            CuisineTags.put("Olivier's Bistro", "European");

            Ratings.put("Olivier's Bistro", 4.7);

            PriceRange.put("Olivier's Bistro", 3);

            MenuList.put("Olivier's Bistro", "Starters");
            MenuList.put("Olivier's Bistro", "Mains");
            MenuList.put("Olivier's Bistro", "Desserts");
            //-----------------------------------------------------
            CuisineTags.put("Salt Burger", "American");
            CuisineTags.put("Salt Burger", "Burger");

            Ratings.put("Salt Burger", 3.8);

            PriceRange.put("Salt Burger", 1);

            MenuList.put("Salt Burger", "Burgers");
            MenuList.put("Salt Burger", "Mains");
            //-----------------------------------------------------
            CuisineTags.put("Red Dragon", "Chinese");
            CuisineTags.put("Red Dragon", "Asian");

            Ratings.put("Red Dragon", 3.5);

            PriceRange.put("Red Dragon", 1);

            MenuList.put("Red Dragon", "Starters");
            MenuList.put("Red Dragon", "Mains");
            MenuList.put("Red Dragon", "Desserts");
            //-------------------------------------------------------
            CuisineTags.put("Basilico", "Italian");
            CuisineTags.put("Basilico", "Pizza");

            Ratings.put("Basilico", 3.9);

            PriceRange.put("Basilico", 3);

            MenuList.put("Basilico", "Starters");
            MenuList.put("Basilico", "Mains");
            MenuList.put("Basilico", "Pizza");
            //---------------------------------------------
            CuisineTags.put("Tolden Wings", "Chicken");
            CuisineTags.put("Tolden Wings", "American");

            Ratings.put("Tolden Wings", 4.2);

            PriceRange.put("Tolden Wings", 2);

            MenuList.put("Tolden Wings", "Starters");
            MenuList.put("Tolden Wings", "Wings");
            MenuList.put("Tolden Wings", "Burgers");
            //-----------------------------------------------
            CuisineTags.put("Think Noodles", "Chinese");
            CuisineTags.put("Think Noodles", "Asian");
            CuisineTags.put("Think Noodles", "Japanese");

            Ratings.put("Think Noodles", 4.7);

            PriceRange.put("Think Noodles", 1);

            MenuList.put("Think Noodles", "Tapas");
            MenuList.put("Think Noodles", "Ramen");
            //------------------------------------------------
            CuisineTags.put("Hermanos", "Mexican");
            CuisineTags.put("Hermanos", "Burritos");
            CuisineTags.put("Hermanos", "Tacos");

            Ratings.put("Hermanos", 4.3);

            PriceRange.put("Hermanos", 1);

            MenuList.put("Hermanos", "Starters");
            MenuList.put("Hermanos", "Mains");
            MenuList.put("Hermanos", "Desserts");
            //-------------------------------------------------
            CuisineTags.put("Mamma Mia!", "Italian");
            CuisineTags.put("Mamma Mia!", "Pizza");

            Ratings.put("Mamma Mia!", 4.6);

            PriceRange.put("Mamma Mia!", 2);

            MenuList.put("Mamma Mia!", "Pizza");
            //--------------------------------------------------
            CuisineTags.put("Falafel Beirut", "Arabic");

            Ratings.put("Falafel Beirut", 4.9);

            PriceRange.put("Falafel Beirut", 2);

            MenuList.put("Falafel Beirut", "Starters");
            MenuList.put("Falafel Beirut", "Mains");
            MenuList.put("Falafel Beirut", "Shawarma");
            //-----------------------------------------------------
            CuisineTags.put("Amo Shawarma", "Arabic");

            Ratings.put("Amo Shawarma", 4.0);

            PriceRange.put("Amo Shawarma", 2);

            MenuList.put("Amo Shawarma", "Shawarma");
            MenuList.put("Amo Shawarma", "Sides");
            MenuList.put("Amo Shawarma", "Drinks");
            //------------------------------------------------------
            CuisineTags.put("CHKN", "Chicken");
            CuisineTags.put("CHKN", "American");

            Ratings.put("CHKN", 1.9);

            PriceRange.put("CHKN", 1);

            MenuList.put("CHKN", "Burgers");
            MenuList.put("CHKN", "Sides");
            //-----------------------------------------------------
            CuisineTags.put("Tequila & Tacos", "Mexican");
            CuisineTags.put("Tequila & Tacos", "Tacos");

            Ratings.put("Tequila & Tacos", 4.3);

            PriceRange.put("Tequila & Tacos", 1);

            MenuList.put("Tequila & Tacos", "Starters");
            MenuList.put("Tequila & Tacos", "Mains");
            MenuList.put("Tequila & Tacos", "Desserts");
            //------------------------------------------------------



        }

        Multimaps.invertFrom(CuisineTags, ReverseCuisines); //Creates a reverse multimap for easy access later
        Log.d(TAG, "initImageBitmaps: reversemap " + ReverseCuisines);
        Log.d(TAG, "initImageBitmaps: map "+CuisineTags);




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



        Log.d(TAG, "initImageBitmaps: complete bitmaps");
        initRecyclerView();
    }


    //Calls the recyclerviewadapter
    private void initRecyclerView(){

        if (RandomRestaurant.size()==0){
            randomCuisine();
        }

        //Restaurant List
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerViewMain = findViewById(R.id.cuisinerestaurantlist);
        LocalrecyclerViewMain.setNestedScrollingEnabled(false); //stops the recyclerview from scrolling
        RecyclerViewAdapterMain LocalAdapter3 = new RecyclerViewAdapterMain(RandomRestaurant, RandomRestaurantImages, this);
        LocalrecyclerViewMain.setAdapter(LocalAdapter3);
        LocalrecyclerViewMain.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

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



        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView3 = findViewById(R.id.restaurantlist3);
        LocalAdapter = new RecyclerViewAdapterLocalFavs(mRestaurantName, mRestaurantImages, this, CuisineTags, Ratings);
        LocalrecyclerView3.setAdapter(LocalAdapter);
        LocalrecyclerView3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    }

    public void cuisineRecycler(){

    }

    public void setCurrentRestaurant(String restaurantname, String restaurantimage){
        CurrentRestaurantName = restaurantname;
        CurrentRestaurantImage = restaurantimage;
        Log.d(TAG, "setCurrentRestaurant: current restaurant has been set as " + CurrentRestaurantName);
        openRestaurantPage();
    }

    public void openRestaurantPage(){
        Intent intent = new Intent(getBaseContext(), RestaurantPage.class);
        intent.putExtra("RESTAURANTNAMEARRAY", mRestaurantName);
        intent.putExtra("RESTAURANTIMAGEARRAY", RestaurantImagesMap);
        intent.putExtra("RESTAURANT_NAME", CurrentRestaurantName);
        intent.putExtra("RESTAURANT_IMAGE", CurrentRestaurantImage);
        intent.putExtra("CUISINETAGS",(Serializable) CuisineTags);
        intent.putExtra("MENULIST",(Serializable) MenuList);

        if (loggedin == true){
            intent.putExtra("LOGGEDIN", loggedin);
            intent.putExtra("USERNAME", currentUsername);
        }


        getVars();

        intent.putExtra("RATINGS",currentRating);
        intent.putExtra("PRICERANGE",currentPriceRange);
        startActivity(intent);
        Log.d(TAG, "openRestaurantPage: new intent has been started");
    }


    private void getVars(){
        currentRating = Ratings.get(CurrentRestaurantName);
        currentPriceRange = PriceRange.get(CurrentRestaurantName);
    }

    private void randomCuisine(){
        TextView CuisineTypeText = findViewById(R.id.cuisinetyperandom);
        ImageView CuisineTypeImage = findViewById(R.id.cuisineimagerandom);
        int randomIndex;
        randomIndex = generator.nextInt(mCuisineText.size());

        selectedCuisine = mCuisineText.get(randomIndex);
        Log.d(TAG, "randomCuisine: selectedcuisine: " + selectedCuisine);
        String selectedImage = mCuisineImages.get(randomIndex);



        RandomRestaurant.addAll(ReverseCuisines.get(selectedCuisine));
        Log.d(TAG, "randomCuisine: reversecuisines: " + ReverseCuisines);
        Log.d(TAG, "randomCuisine: random restaurant: " +RandomRestaurant);

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

    @Override
    public void onBackPressed() { } //disables the go back button on android when on the main page (because there is no where back to go)

    public void SearchBar(View view){
        Searching(this);
    }

    public void Searching(Context context){
        Intent intent = new Intent(context, Search.class);
        intent.putExtra("RESTAURANT_NAME", mRestaurantName);
        intent.putExtra("RESTAURANT_IMAGE", RestaurantImagesMap);
        intent.putExtra("CUISINETAGS",(Serializable) CuisineTags);
        intent.putExtra("MENULIST",(Serializable) MenuList);

        if (!clickedtag.equals("")){
            intent.putExtra("TAG", clickedtag);
        }


        if (loggedin == true){
            intent.putExtra("LOGGEDIN", loggedin);
            intent.putExtra("USERNAME", currentUsername);
        }

        intent.putExtra("RATINGS",(Serializable) Ratings);
        intent.putExtra("PRICERANGE",(Serializable) PriceRange);

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
                parts2 = x.split(";", 9);
                int mFoodId = Integer.parseInt(parts2[0]);
                String Dish = parts2[1];
                String Restaurant = parts2[2];
                String Category = parts2[3];
                double Price = Double.parseDouble(parts2[4]);
                String images = parts2[5];
                String description = parts2[6];


                if (parts2[7].trim().length() <= 0){
                    Log.d(TAG, "onCreate: this food item doesnt have any choices" );
                }

                else{
                    String mchoices[] = parts2[7].trim().split(">",5);

                    ArrayList<String> mfoodchoicelist = new ArrayList<>(Arrays.asList(mchoices));

                    FoodChoices.put(mFoodId, mfoodchoicelist);
                    Log.d(TAG, "onCreate: lineee: "+ FoodChoices.get(mFoodId));
                }

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

    public void yougot(View view){
        Toast.makeText(this, "lol u got played this doesnt do anything", Toast.LENGTH_SHORT).show();
    }

    public void cuisineclick(View view){
        setTag(selectedCuisine);
    }

    public void setTag(String tag){
        clickedtag = tag;
        Searching(this);
    }

    public void orderpage(View view){

        if(username.equals("Guest")){
            Toast.makeText(this, "You must sign in to view your orders!", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, prevorders.class);
            startActivity(intent);
            finish();
        }

    }

}