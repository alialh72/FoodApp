package com.example.foodapp.nonactivityclasses;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class CartClass {
    public static ArrayList<String> cart = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();
    public static ArrayList<Integer> cartid = new ArrayList<Integer>();
    public static ArrayList<Double> foodprices = new ArrayList<Double>();
    public static String restname = "&&";
    public static int ordernumber;

    public static String finalorder;

    public Context context;

    public void outputname(){
        Log.d(TAG, "outputname: restname: " + restname);
        Log.d(TAG, "outputname: foodname: " + cart);
    }

    public void setRestaurant(String restaurant){
        restname = restaurant;
    }

    public void addToCart(String fooditem, String restaurant, Context mcontext, int foodid, int numberoftimes, HashMap<String, String> foodchoices, double foodprice){
        Log.d(TAG, "addToCart: foodchoices: " + foodchoices);

        context = mcontext;
        if(restname.equals("&&")){
            Log.d(TAG, "addToCart: new restaurant");
            Toast.makeText(mcontext, "New Restaurant", Toast.LENGTH_SHORT).show();
        }
        else if(!restname.equals(restaurant)){
            Log.d(TAG, "setRestaurant: cart cleared");
            Toast.makeText(context, "Cart from previous restaurant has been cleared", Toast.LENGTH_SHORT).show();
            clearCart();
        }
        else{
            Log.d(TAG, "setRestaurant: restaurant same");
        }
        setRestaurant(restaurant);

        for (int i= 0; i < numberoftimes;i++){
            cart.add(fooditem);
            cartid.add(foodid);

            if(foodchoices.size() > 0){
                ArrayList<String> choices = new ArrayList<>(foodchoices.values());
                StringBuilder sb = new StringBuilder();

                if (choices.size() > 1){
                    for (int k = 0; k < choices.size(); k++)
                    {
                        String m = choices.get(k);
                        Log.d(TAG, "addToCart: k: "+k);
                        Log.d(TAG, "addToCart: choisessize: "+choices.size());

                        if (m.equals("%%$")){
                            sb.append("No Modifications");
                        }
                        else{
                            sb.append(m);
                        }

                        if(k+1 == choices.size()){

                        }
                        else{
                            sb.append(", ");
                        }



                    }
                }
                else if(choices.size() == 1){
                    if (foodchoices.get("Extras").equals("%%$")){
                        sb.append("No Modifications");
                    }
                    else{
                        for (String s : choices)
                        {
                            sb.append(s);
                        }
                    }

                }
                descriptions.add(sb.toString());
                Log.d(TAG, "addToCart: descriptinos: " + descriptions);
            }

            foodprices.add(foodprice);
        }


        Toast.makeText(mcontext, "Food Item has been added to cart", Toast.LENGTH_SHORT).show();

    }

    public void clearCart(){
        cart.clear();
        cartid.clear();
        descriptions.clear();
        foodprices.clear();
        restname="&&";
        finalorder="";
    }

    public void setFinalOrder(double totalprice){
        Random rand = new Random(); //instance of random class
        int upperbound = 10000;
        //generate random values from 0-10000
        String int_random = String.valueOf(rand.nextInt(upperbound));
        ordernumber = Integer.parseInt(int_random);

        StringBuilder sbb = new StringBuilder();
        StringBuilder sdd = new StringBuilder();
        String foodorderid;
        String descriptionorder;
        String total = String.valueOf(totalprice);
        String ordernumber = String.valueOf(int_random);

        //it adds turns the arrays into strings and adds special characters between so it can be stored in the sql database easily
        for (int l = 0; l < cartid.size(); l++)
        {
            sbb.append(cartid.get(l));
            Log.d(TAG, "setFinalOrder: cartidsize: "+cartid.size());
            Log.d(TAG, "setFinalOrder: l: "+l);
            if (cartid.size() != (l+1)){
                sbb.append("^");
            }


        }

        for (int i = 0; i < descriptions.size(); i++)
        {
            sdd.append(descriptions.get(i));

            if(descriptions.size() != (i+1)){
                sdd.append("^");
            }

        }
        descriptionorder = sdd.toString();
        foodorderid = sbb.toString();

        finalorder = int_random+"}"+foodorderid+"}"+descriptionorder+"}"+total+"}"+restname;


    }

    public void removeItem(int position){
        cart.remove(position);
        cartid.remove(position);
        descriptions.remove(position);
        foodprices.remove(position);
    }


}
