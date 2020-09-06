package com.example.teachmeapp;


import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AdapterCardViewList;

import static com.example.teachmeapp.Helpers.Globals.comm;


public class Schedule extends HamburgerMenu {

    String s1[] = {"testing", "this","helo"};
    String s2[] = {"stirng 2", "amazing","broo"};
    String s3[] = {"stirng 3", "amazingsad","bro3o"};
    Button MoreInfoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_item_cardview);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        AdapterCardViewList adapterCardViewList = new AdapterCardViewList(this, s1, s2, s3,MoreInfoButton);
        recyclerView.setAdapter(adapterCardViewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}