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
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;

public class RecyclerSearch extends RecyclerView.Adapter<RecyclerSearch.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mRestaurantName = new ArrayList<>();
    private ArrayList<String> mRestaurantImage = new ArrayList<>();
    private ArrayList<Double> Ratings = new ArrayList<Double>();
    private ListMultimap<String, String> mCuisineTags = ArrayListMultimap.create();
    private Context mContext;


    public RecyclerSearch(ArrayList<String> lrestauranttext,ArrayList<String> lrestaurantimage , Context context, ListMultimap<String, String> cuisinetags){
        mCuisineTags = cuisinetags;
        mRestaurantName = lrestauranttext;
        mRestaurantImage = lrestaurantimage;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchbox, parent, false);
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

        double currentRating = Ratings.get(position);

        String mRating = String.valueOf(currentRating) + " ☆";

        holder.restaurant_ratings.setText(mRating);

        //gets tags from the listmap
        String selectedCuisine = mRestaurantName.get(position);
        ArrayList<String> Tags = new ArrayList<>(); //array needs to be reset for every restaurant so it is defined here

        Tags.addAll(mCuisineTags.get(selectedCuisine)); //turns the collection into an arraylist

        String answer = String.join(", ", Tags); //turns the arraylist into a string because an arraylist cant be set as text
        holder.restaurant_tags.setText(answer);

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

    public void filterList(ArrayList<String> filteredList, ArrayList<String> images, ArrayList<Double> ratings){
        mRestaurantName = filteredList;
        mRestaurantImage = images;
        Ratings = ratings;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView restauranttext, restaurant_ratings, restaurant_tags;
        ImageView restaurantimage;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurant_tags = itemView.findViewById(R.id.tags);
            restaurant_ratings = itemView.findViewById(R.id.ratings);
            restauranttext = itemView.findViewById(R.id.restaurant_name);
            restaurantimage = itemView.findViewById(R.id.restaurant_image);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
