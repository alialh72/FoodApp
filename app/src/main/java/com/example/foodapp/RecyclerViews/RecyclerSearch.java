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
import com.example.foodapp.MainActivity;
import com.example.foodapp.R;
import com.example.foodapp.RestaurantPage;
import com.example.foodapp.Search;

import java.util.ArrayList;

public class RecyclerSearch extends RecyclerView.Adapter<RecyclerSearch.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mRestaurantName = new ArrayList<>();
    private ArrayList<String> mRestaurantImage = new ArrayList<>();
    private Context mContext;


    public RecyclerSearch(ArrayList<String> lrestauranttext,ArrayList<String> lrestaurantimage , Context context){
        mRestaurantName = lrestauranttext;
        mRestaurantImage = lrestaurantimage;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_box, parent, false);
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


        holder.restauranttext.setText(mRestaurantName.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mRestaurantName.get(position));
                ((Search)mContext).setRestaurant(mRestaurantName.get(position));
            }
        });

    }



    @Override
    public int getItemCount() {
        return mRestaurantName.size();
    }

    public void filterList(ArrayList<String> filteredList, ArrayList<String> images){
        mRestaurantName = filteredList;
        mRestaurantImage = images;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView restauranttext;
        ImageView restaurantimage;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restauranttext = itemView.findViewById(R.id.restaurant_name);
            restaurantimage = itemView.findViewById(R.id.restaurant_image);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
