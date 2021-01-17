package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.RecyclerViews.orderrecycler;
import com.example.foodapp.RecyclerViews.recyclerorder2;
import com.example.foodapp.nonactivityclasses.getOrders;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class prevorders extends AppCompatActivity {


    private static final String TAG = "";
    private ArrayList<Integer> ordernumbers = new ArrayList<Integer>();
    private ListMultimap<Integer, Integer> orders = ArrayListMultimap.create();
    private ListMultimap<Integer, ArrayList<String>> OrderModis = ArrayListMultimap.create();
    private HashMap<Integer, Double> OrderTotal = new HashMap<Integer, Double>();
    private HashMap<Integer, String> OrderRest = new HashMap<Integer, String>();

    private ArrayList<String> restnames = new ArrayList<String>();
    private ArrayList<Double> totals = new ArrayList<Double>();

    private ArrayList<String> restimages = new ArrayList<String>();

    private View decorView;
    private Dialog mDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevorders);
        getHashMapFromTextFile();

        decorView = getWindow().getDecorView();
        decorView = getWindow().getDecorView();

        mDialog = new Dialog(this);

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        initRecyclerView();

    }

    public int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }


    public void getHashMapFromTextFile(){
        getOrders mGetOrders = new getOrders();
        Log.d(TAG, "getHashMapFromTextFile: inside hashmapstart");
        try {

                String parts[] = mGetOrders.mGet(MainActivity.username);
                Log.d(TAG, "getHashMapFromTextFile: parts: "+parts);
                String parts2[] = new String[0];
                int i;
                String x;
                for (i = 0; i < parts.length; i++) {
                    // accessing each element of array
                    x = parts[i];
                    if(!x.equals("")){
                        Log.d(TAG, "calld: x: " + x);
                        parts2 = x.split("\\}", 5);
                        int ordernumber = Integer.parseInt(parts2[0]);
                        ordernumbers.add(ordernumber);
                        ArrayList<Integer> foodids = new ArrayList<Integer>();
                        ArrayList<String> modifications = new ArrayList<String>();

                        if (parts2[1].contains("^")){
                            Log.d(TAG, "getHashMapFromTextFile: inside contains");
                            String Dish[] = parts2[1].split("\\^",15);
                            for (String li : Dish){
                                int foodid = Integer.parseInt(li);
                                foodids.add(foodid);
                            }
                        }
                        else{
                            foodids.add(Integer.parseInt(parts2[1]));
                        }

                        if (parts2[2].contains("^")){
                            String modi[] = parts2[2].split("\\^",15);
                            for (String j : modi){
                                Log.d(TAG, "getHashMapFromTextFile: j: "+j);
                                String modif = j;
                                modifications.add(modif);
                            }
                        }
                        else{
                            modifications.add(parts2[2]);
                        }

                        double totalprice = Double.parseDouble(parts2[3]);
                        String restaurantname = parts2[4];


                        OrderTotal.put(ordernumber, totalprice);
                        OrderRest.put(ordernumber,restaurantname);

                        for (int u =0;u < foodids.size();u++){
                            int id = foodids.get(u);

                            orders.put(ordernumber,id);
                        }

                        OrderModis.put(ordernumber, modifications);

                        Log.d(TAG, "getHashMapFromTextFile: ordertotal: "+OrderTotal);
                        Log.d(TAG, "getHashMapFromTextFile: orderrest: "+OrderRest);
                        Log.d(TAG, "getHashMapFromTextFile: orders: "+orders);
                        Log.d(TAG, "getHashMapFromTextFile: OrderModis: "+OrderModis);
                    }

                    else{
                        Toast.makeText(this, "You have no orders!", Toast.LENGTH_SHORT).show();
                    }

                }





        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    private void addToArray(){


        for (int i =0;i<ordernumbers.size();i++){
            String restname = OrderRest.get(ordernumbers.get(i));
            Double mtotalprice = OrderTotal.get(ordernumbers.get(i));
            restnames.add(restname);
            totals.add(mtotalprice);
        }

        for(int j = 0; j < restnames.size();j++){
            String image = MainActivity.RestaurantImagesMap.get(restnames.get(j));
            restimages.add(image);
        }





    }


    private void initRecyclerView(){
        addToArray();
        //Local favorites recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView = findViewById(R.id.ordersrecycler);
        orderrecycler LocalAdapter = new orderrecycler(restnames,ordernumbers,totals,restimages, this);
        LocalrecyclerView.setAdapter(LocalAdapter);
        LocalrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }

    public void goback(View view){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void openDialog(int position, int ordernumber){
        mDialog.setContentView(R.layout.layout_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

        TextView ordernum;
        ordernum = (TextView) mDialog.findViewById(R.id.ordernum);
        TextView restname;
        restname = (TextView) mDialog.findViewById(R.id.restname);

        ArrayList<String> finaldescs = new ArrayList<String>();
        ordernum.setText("Order #"+String.valueOf(ordernumber));
        restname.setText(OrderRest.get(ordernumber));

        List<ArrayList<String>> desc = OrderModis.get(ordernumber);
        Log.d(TAG, "openDialog: desc: "+desc);

        for (int i =0;i < desc.size(); i++){
            ArrayList<String> m = desc.get(i);
            Log.d(TAG, "openDialog: m: "+m.get(0));
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j<m.size();j++)
            {
                String s = m.get(j);
                Log.d(TAG, "openDialog: s: "+s);
                finaldescs.add(s);

            }



        }

        Log.d(TAG, "openDialog: final descs: "+finaldescs);

        List<Integer> ids = orders.get(ordernumber);
        ArrayList<String> food = new ArrayList<String>();
        for (int i = 0; i <ids.size();i++){
            food.add(MainActivity.FoodId.get(ids.get(i))) ;
        }

        ArrayList<Double> foodprices = new ArrayList<Double>();
        for (int v = 0; v <ids.size();v++){
            foodprices.add(MainActivity.FoodPrice.get(ids.get(v)));
        }




        //Local favorites recycler
        Log.d(TAG, "initRecyclerView: init recyclerview locals");
        RecyclerView LocalrecyclerView = (RecyclerView) mDialog.findViewById(R.id.recyclerViewPopup);
        recyclerorder2 LocalAdapter = new recyclerorder2(food,this, finaldescs, foodprices);
        LocalrecyclerView.setAdapter(LocalAdapter);
        LocalrecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }


}