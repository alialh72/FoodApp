package com.example.foodapp.RecyclerViews;

import android.content.Context;
import android.graphics.Color;
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
import com.example.foodapp.RestaurantPage;
import com.example.foodapp.food_page;

import java.util.ArrayList;
import java.util.List;

public class RecyclerCart extends RecyclerView.Adapter<RecyclerCart.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mRestaurantName = new ArrayList<>();
    private ArrayList<Integer> FoodIds = new ArrayList<>();
    private ArrayList<String> mRestaurantImage = new ArrayList<>();
    private Context mContext;


    public RecyclerCart(ArrayList<String> lrestauranttext, Context context, ArrayList<Integer> FoodId){
        mRestaurantName = lrestauranttext;
        FoodIds = FoodId;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called.");

        holder.foodnameview.setText(mRestaurantName.get(position));
        holder.additionalinfo.setText(food_page.CartClass.descriptions.get(position));
        holder.price.setText(food_page.CartClass.foodprices.get(position).toString() + " CAD");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mRestaurantName.get(position));
                Log.d(TAG, "onClick: position: " + position);
                //((RestaurantPage)mContext).switcheroo(mRestaurantName.get(position));
                //holder.restauranttext.setTextColor(Color.parseColor("#F2A007"));
            }
        });

    }



    @Override
    public int getItemCount() {
        return mRestaurantName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView foodnameview, price, additionalinfo, numberoftimes;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodnameview = itemView.findViewById(R.id.nameoffood);
            price = itemView.findViewById(R.id.price);
            additionalinfo = itemView.findViewById(R.id.additionalinfo);
            numberoftimes = itemView.findViewById(R.id.numberoftimes);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
