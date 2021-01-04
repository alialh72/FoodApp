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
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapterLocalFavs extends RecyclerView.Adapter<RecyclerViewAdapterLocalFavs.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mRestaurantName = new ArrayList<>();
    private ArrayList<String> mRestaurantImage = new ArrayList<>();
    private ListMultimap<String, String> mCuisineTags = ArrayListMultimap.create();
    private HashMap<String, Double> mRatings = new HashMap<String, Double>();
    private Context mContext;


    public RecyclerViewAdapterLocalFavs(ArrayList<String> lrestauranttext, ArrayList<String> lrestaurantimage, Context context, ListMultimap<String, String> cuisineTags, HashMap<String, Double> Ratings){
        mRestaurantImage = lrestaurantimage;
        mRestaurantName = lrestauranttext;
        mCuisineTags = cuisineTags;
        mRatings = Ratings;
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

        double currentRating = mRatings.get(mRestaurantName.get(position));

        String mRating = String.valueOf(currentRating) + " ☆";

        holder.restaurant_ratings.setText(mRating);


        //gets tags from the listmap
        String selectedCuisine = mRestaurantName.get(position);
        ArrayList<String> Tags = new ArrayList<>(); //array needs to be reset for every restaurant so it is defined here

        Tags.addAll(mCuisineTags.get(selectedCuisine)); //turns the collection into an arraylist

        String answer = String.join(", ", Tags); //turns the arraylist into a string because an arraylist cant be set as text
        holder.restaurant_tags.setText(answer);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mRestaurantName.get(position));
                ((MainActivity)mContext).setCurrentRestaurant(mRestaurantName.get(position), mRestaurantImage.get(position));
            }
        });

    }



    @Override
    public int getItemCount() {
        return mRestaurantName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView restaurantimage;
        TextView restauranttext, restaurant_tags, restaurant_ratings;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantimage = itemView.findViewById(R.id.restaurant_image);
            restauranttext = itemView.findViewById(R.id.restaurant_name);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            restaurant_tags = itemView.findViewById(R.id.tags);
            restaurant_ratings = itemView.findViewById(R.id.ratings);

        }
    }

}
