package com.example.teachmeapp;


import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AdapterCardViewList;

import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT;


public class Schedule extends HamburgerMenu {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_item_cardview);

        CallViewAdapter(SEARCH_RESULT);
    }
}