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

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mCuisineText = new ArrayList<>();
    private ArrayList<String> mCuisineImage = new ArrayList<>();
    private Context mContext;


    public RecyclerViewAdapter(ArrayList<String> lcuisinetext, ArrayList<String> lcuisineimage, Context context){
        mCuisineImage = lcuisineimage;
        mCuisineText = lcuisinetext;

        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .circleCrop()
                .load(mCuisineImage.get(position))
                .into(holder.cuisineimage);



        holder.cuisinetext.setText(mCuisineText.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mCuisineText.get(position));
            }
        });

    }



    @Override
    public int getItemCount() {
        return mCuisineText.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView cuisineimage;
        TextView cuisinetext;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cuisineimage = itemView.findViewById(R.id.cuisine_image);
            cuisinetext = itemView.findViewById(R.id.cuisine_text);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }


}
