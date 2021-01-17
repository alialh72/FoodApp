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

public class recyclerorder2 extends RecyclerView.Adapter<recyclerorder2.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mRestaurantName = new ArrayList<>();
    private ArrayList<Double> price = new ArrayList<>();
    private ArrayList<String> desc = new ArrayList<>();
    private Context mContext;


    public recyclerorder2(ArrayList<String> foodname, Context context, ArrayList<String> descs, ArrayList<Double> price){
        mRestaurantName = foodname;
        desc = descs;
        this.price = price;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<String> abrvlist = new ArrayList<String>();

        Log.d(TAG, "onBindViewHolder: called.");
        for(int i = 0; i<mRestaurantName.size();i++){
            abrvlist.add(mRestaurantName.get(i));
        }


        ArrayList<String> abrvdesc = new ArrayList<String>();

        Log.d(TAG, "onBindViewHolder: called.");
        for (int x = 0; x < desc.size(); x++){
            String namem = desc.get(x);
            namem = StringUtils.abbreviate(namem, 30);
            abrvdesc.add(x, namem);
        }

        holder.foodnameview.setText(abrvlist.get(position));
        holder.additionalinfo.setText(abrvdesc.get(position));
        holder.price.setText("$"+String.valueOf(price.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });

    }



    @Override
    public int getItemCount() {
        return mRestaurantName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView foodnameview, price, additionalinfo;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodnameview = itemView.findViewById(R.id.nameoffood);
            price = itemView.findViewById(R.id.price);
            additionalinfo = itemView.findViewById(R.id.additionalinfo);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
