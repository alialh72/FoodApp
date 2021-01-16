package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class food_page extends AppCompatActivity {

    private static final String TAG = "";
    private String foodname, image, description, restaurantname;
    private double price;
    private int foodid;

    private ImageView foodimage;
    private TextView title, mdescription, mprice, numberofitemstext;
    private int numberoftimes = 1;
    private EditText extras;
    private View decorView;
    private ArrayList<String> choices;
    private ArrayList<String> titles = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> childchoices = new HashMap<String, ArrayList<String>>();

    private ExpandableListView expandableListView;
    private MainAdapter adapter;

    private HashMap<String, String> foodchoices = new HashMap<String, String>();

    public static CartClass CartClass = new CartClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_page);

        decorView = getWindow().getDecorView();

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        foodimage = findViewById(R.id.food_image);
        title = findViewById(R.id.food_title);
        mprice = findViewById(R.id.price);
        mdescription = findViewById(R.id.description);
        expandableListView = findViewById(R.id.expandable_list);
        extras = findViewById(R.id.extras);
        numberofitemstext = findViewById(R.id.numberofitems);


        numberofitemstext.setText(String.valueOf(numberoftimes));

        extras.setHorizontallyScrolling(true);
        expandableListView.setNestedScrollingEnabled(false);


        foodname = getIntent().getStringExtra("FOODNAME");
        restaurantname = getIntent().getStringExtra("RESTAURANT");
        image = getIntent().getStringExtra("IMAGE");
        description = getIntent().getStringExtra("DESCRIPTION");
        price = getIntent().getExtras().getDouble("PRICE");
        foodid = getIntent().getExtras().getInt("FOODID");

        Log.d(TAG, "onCreate: foodid: " + foodid);
        choices = getIntent().getStringArrayListExtra("CHOICES");
        Log.d(TAG, "onCreate:inside food_page choices: " + choices);


        if (foodname.length() > 23){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) title.getLayoutParams();
            params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            title.setLayoutParams(params);
        }

        if (choices != null){
            expandableListView.setVisibility(View.VISIBLE);

        }


        seperateArrays();
        //initialise adapter
        adapter = new MainAdapter(titles, childchoices);

        // set adapter
        expandableListView.setAdapter(adapter);

        setData();
    }

    public void setData(){
        Glide.with(this)
                .asBitmap()
                .load(image)
                .placeholder(R.drawable.ic_loading_foreground)
                .into(foodimage);

        title.setText(foodname);
        mdescription.setText(description);
        mprice.setText("CA$" + String.valueOf(price));
    }

    public void goback(View view){
        this.finish();
    }

    public int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }

    public void addtoArray(String group, String child){
        if (foodchoices.get(group) != null){
            foodchoices.remove(group);
            foodchoices.put(group, child);
            Log.d(TAG, "addtoArray: choices: " + foodchoices);
        }
        else{
            foodchoices.put(group, child);
            Log.d(TAG, "addtoArray: choices: " + foodchoices);
        }

    }

    //first checks if there is more than one title, if so, splits it into an array
    private void seperateArrays(){
        ArrayList<String> localchoices = choices;

        if (choices != null){
            Log.d(TAG, "seperateArrays: inside seperate, isnt null");
            if(choices.get(0).indexOf("*") >= 1){
                titles = new ArrayList<>(Arrays.asList(choices.get(0).split("\\*", 4)));
                Log.d(TAG, "seperateArrays: titles has more dan one: " + titles);
            }
            else{
                titles.add(choices.get(0));
                Log.d(TAG, "seperateArrays: titles has less dan one: " + titles);

            }


            int i;
            localchoices.remove(0);
            Log.d(TAG, "seperateArrays: titles: " + titles.size());

            for (i = 0; i < titles.size(); i++){
                String[] line = localchoices.get(i).split(",", 4);
                ArrayList<String> child = new ArrayList<String>();
                Collections.addAll(child, line);
                Log.d(TAG, "seperateArrays: child: " + child);
                childchoices.put(titles.get(i), child);
                Log.d(TAG, "seperateArrays: childchoices: " + childchoices);
            }



        }


    }

    public void CartAdd(View view){
        if (!extras.getText().toString().equals("")){
            foodchoices.remove("Extras");
            foodchoices.put("Extras", extras.getText().toString().replaceAll("( +)"," ").trim());
            Log.d(TAG, "CartAdd: Food choices (removed previous):"+foodchoices);
        }
        else {
            foodchoices.put("Extras", "}};:{^^%%$");
        }

        double lprice = MainActivity.FoodPrice.get(foodid);

        Log.d(TAG, "CartAdd: restaurantname: " + restaurantname);
        CartClass.addToCart(foodname, restaurantname, this, foodid, numberoftimes, foodchoices, lprice);
        CartClass.outputname();
    }

    public void addone(View view){
        if (numberoftimes == 15){

        }
        else{
            numberoftimes +=1;
        }
        numberofitemstext.setText(String.valueOf(numberoftimes));
    }

    public void minusone(View view){
        if (numberoftimes == 1){

        }
        else{
            numberoftimes -=1;
        }

        numberofitemstext.setText(String.valueOf(numberoftimes));
    }




}