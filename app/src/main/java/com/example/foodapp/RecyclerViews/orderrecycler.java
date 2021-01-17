package com.example.foodapp.RecyclerViews;

import android.content.Context;
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
import com.example.foodapp.R;
import com.example.foodapp.Search;
import com.example.foodapp.prevorders;

import java.util.ArrayList;

public class orderrecycler extends RecyclerView.Adapter<orderrecycler.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mRestaurantName = new ArrayList<>();
    private ArrayList<String> mRestaurantImage = new ArrayList<>();
    private ArrayList<Integer> ordernumber = new ArrayList<>();
    private ArrayList<Double> totalprice = new ArrayList<>();
    private Context mContext;


    public orderrecycler(ArrayList<String> lrestauranttext,ArrayList<Integer> ordernumber,ArrayList<Double> totalprice, ArrayList<String> lrestaurantimage , Context context){
        mRestaurantName = lrestauranttext;
        mRestaurantImage = lrestaurantimage;
        this.ordernumber = ordernumber;
        this.totalprice = totalprice;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prevorderlayout, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mRestaurantImage.get(position))
                .into(holder.restaurantimage);

        holder.ordernumberview.setText("Order #"+String.valueOf(ordernumber.get(position)));
        holder.totalpriceview.setText("Total $"+String.valueOf(totalprice.get(position)));


        holder.restauranttext.setText(mRestaurantName.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mRestaurantName.get(position));
                ((prevorders)mContext).openDialog(position, ordernumber.get(position));
            }
        });

    }



    @Override
    public int getItemCount() {
        return mRestaurantName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView restauranttext, totalpriceview, ordernumberview;
        ImageView restaurantimage;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restauranttext = itemView.findViewById(R.id.restaurantname);
            restaurantimage = itemView.findViewById(R.id.restaurantimage);
            totalpriceview = itemView.findViewById(R.id.totalprice);
            ordernumberview = itemView.findViewById(R.id.ordernumber);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
