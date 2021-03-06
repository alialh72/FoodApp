package com.example.foodapp.RecyclerViews;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.CartPage;
import com.example.foodapp.MainActivity;
import com.example.foodapp.R;
import com.example.foodapp.RestaurantPage;
import com.example.foodapp.food_page;

import org.apache.commons.lang3.StringUtils;

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
        ArrayList<String> abrvlist = new ArrayList<String>();

        Log.d(TAG, "onBindViewHolder: called.");
        for (int x = 0; x < food_page.CartClass.cart.size(); x++){
            String name = food_page.CartClass.cart.get(x);
            name = StringUtils.abbreviate(name, 20);
            abrvlist.add(x, name);
        }


        ArrayList<String> abrvdesc = new ArrayList<String>();

        Log.d(TAG, "onBindViewHolder: called.");
        for (int x = 0; x < food_page.CartClass.descriptions.size(); x++){
            String namem = food_page.CartClass.descriptions.get(x);
            namem = StringUtils.abbreviate(namem, 30);
            abrvdesc.add(x, namem);
        }

        holder.foodnameview.setText(abrvlist.get(position));
        holder.additionalinfo.setText(abrvdesc.get(position));
        holder.price.setText(food_page.CartClass.foodprices.get(position).toString() + " CAD");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + food_page.CartClass.cart.get(position));
                Log.d(TAG, "onClick: position: " + position);
                //((RestaurantPage)mContext).switcheroo(mRestaurantName.get(position));
                //holder.restauranttext.setTextColor(Color.parseColor("#F2A007"));
            }
        });

        holder.bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food_page.CartClass.removeItem(position);
                ((CartPage)mContext).prepData();
                notifyDataSetChanged();

                if(food_page.CartClass.cart.size() == 0){
                    Toast.makeText(mContext, "Cart is empty", Toast.LENGTH_SHORT).show();
                    ((CartPage)mContext).endActivity();
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        return mRestaurantName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView foodnameview, price, additionalinfo;
        ImageView bin;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodnameview = itemView.findViewById(R.id.nameoffood);
            price = itemView.findViewById(R.id.price);
            additionalinfo = itemView.findViewById(R.id.additionalinfo);
            bin = itemView.findViewById(R.id.bin);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
