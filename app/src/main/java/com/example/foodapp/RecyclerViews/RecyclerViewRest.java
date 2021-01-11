package com.example.foodapp.RecyclerViews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.MainActivity;
import com.example.foodapp.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class RecyclerViewRest extends RecyclerView.Adapter<RecyclerViewRest.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> FoodItemName = new ArrayList<>();
    private ArrayList<String> FoodImage = new ArrayList<>();
    private ArrayList<String> FoodPrice = new ArrayList<>();
    private ArrayList<String> FoodIngredients = new ArrayList<>();
    private ArrayList<String> FoodDescriptions = new ArrayList<>();
    private ArrayList<Double> Prices = new ArrayList<>();
    private Context mContext;


    public RecyclerViewRest(ArrayList<String> lfooditem,ArrayList<Double> lPrices,ArrayList<String> FoodImg,ArrayList<String> Descriptions, Context context){
        FoodDescriptions = Descriptions;
        FoodItemName = lfooditem;
        Prices = lPrices;
        mContext = context;
        FoodImage = FoodImg;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fooditem_box, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called.");
        ArrayList<String> AbrvRestList = new ArrayList<>(); //contains foodids


        Glide.with(mContext)
                .asBitmap()
                .load(FoodImage.get(position))
                .placeholder(R.drawable.ic_loading_foreground)
                .into(holder.foodimage);

        String abbrev = StringUtils.abbreviate(FoodDescriptions.get(position), 20);
        holder.foodingredients.setText(abbrev);


        int size = FoodItemName.size();

        for (int x = 0; x < size; x++){
            String name = FoodItemName.get(x);
            Log.d(TAG, "onBindViewHolder: Fooditem name: " + FoodItemName.get(x));
            name = StringUtils.abbreviate(name, 20);
            AbrvRestList.add(x, name);
        }

        holder.foodname.setText(AbrvRestList.get(position));
        Log.d(TAG, "onBindViewHolder: foodname: " + (AbrvRestList.get(position)));
        holder.foodprice.setText("CA$ "+Prices.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + FoodItemName.get(position));
                Log.d(TAG, "onClick: clicked on description: " + FoodDescriptions.get(position));
            }
        });

    }



    @Override
    public int getItemCount() {
        return FoodItemName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView foodname, foodingredients, foodprice;
        ImageView foodimage;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodname = itemView.findViewById(R.id.fooditem);
            foodingredients = itemView.findViewById(R.id.ingredients);
            foodprice = itemView.findViewById(R.id.price);
            foodimage = itemView.findViewById(R.id.foodimage);

            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
