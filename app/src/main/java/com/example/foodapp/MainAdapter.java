package com.example.foodapp;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class MainAdapter extends BaseExpandableListAdapter {
    //initalise variables
    ArrayList<String> listGroup;
    HashMap<String,ArrayList<String>> listChild;
    int image = R.drawable.ic_radio_button_uncheck;
    HashMap<String, Integer> selectedposition = new HashMap<String, Integer>();

    //constructor
    public MainAdapter(ArrayList<String> listGroup, HashMap<String, ArrayList<String>> listChild){
        this.listGroup = listGroup;
        this.listChild=listChild;
        Log.d(TAG, "MainAdapter: listgroup: " + listGroup);
        Log.d(TAG, "MainAdapter: listchild: " + listChild);


        //this section stores the selected box's position so it isnt changed when selecting another option in another group
        int i =0;
        for (i = 0; i<listGroup.size(); i++){
            selectedposition.put(listGroup.get(i), -1);
        }
    }

    @Override
    public int getGroupCount() {
        //return group size
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //return child size
        return listChild.get(listGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        //return group item
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //return child item
        return listChild.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        //initialise view
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_header
                        ,viewGroup, false);

        //initialise and assign variables
        TextView textView = view.findViewById(R.id.titletext);
        String sgroup = String.valueOf(getGroup(groupPosition));

        textView.setText(sgroup);

        //return view
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        //initialise view
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.itembox
                        ,viewGroup, false);

        //initialise and assign variables
        TextView textView = view.findViewById(R.id.textView);
        ImageView checkbox = view.findViewById(R.id.checkbox);

        //initalise string
        String schild = String.valueOf(getChild(groupPosition,childPosition));
        Log.d(TAG, "getChildView: Schild: " + schild);
        String sgroup = String.valueOf(getGroup(groupPosition));
        Log.d(TAG, "getChildView: sgroup: " + sgroup);
        //set text on text view
        textView.setText(schild);

        //setonclicklistener

        int check = R.drawable.ic_radio_button;
        int uncheck = R.drawable.ic_radio_button_uncheck;


        if (childPosition == selectedposition.get(listGroup.get(groupPosition))){
            checkbox.setImageResource(check);
        }
        else{
            checkbox.setImageResource(uncheck);
        }


        View finalView = view;




        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedposition.put(listGroup.get(groupPosition), childPosition);
                TextView textView = finalView.findViewById(R.id.textView);
                Toast.makeText(viewGroup.getContext()
                        ,schild,Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onClick: "+schild+" " + sgroup);

                ((food_page)viewGroup.getContext()).addtoArray(sgroup, schild);
                //Log.d(TAG, "onClick: image draw " + imageView.getDrawable());
                notifyDataSetChanged();
            }
        });



        //return view
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
