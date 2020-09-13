package com.example.teachmeapp;


import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AdapterCardViewList;

import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT;


public class Schedule extends HamburgerMenu {

    String s1[] = {"testing", "this","helo"};
    String s2[] = {"stirng 2", "amazing","broo"};
    String s3[] = {"stirng 3", "amazingsad","bro3o"};
    Button MoreInfoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_item_cardview);


        CallViewAdapter(SEARCH_RESULT,this, s1, s2, s3,MoreInfoButton,null,null);

    }


}