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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class RecyclerViewAdapterMain extends RecyclerView.Adapter<RecyclerViewAdapterMain.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mRestaurantName = new ArrayList<>();
    private ArrayList<String> mRestaurantImage = new ArrayList<>();
    private Context mContext;


    public RecyclerViewAdapterMain(ArrayList<String> lrestauranttext, ArrayList<String> lrestaurantimage, Context context){
        mRestaurantImage = lrestaurantimage;
        mRestaurantName = lrestauranttext;

        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_box_small, parent, false);
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

        String line = StringUtils.abbreviate(mRestaurantName.get(position), 13);

        holder.restauranttext.setText(line);
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
        TextView restauranttext;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantimage = itemView.findViewById(R.id.restaurant_image);
            restauranttext = itemView.findViewById(R.id.restaurant_name);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
