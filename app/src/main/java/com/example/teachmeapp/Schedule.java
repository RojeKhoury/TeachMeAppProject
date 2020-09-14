package com.example.teachmeapp;


import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AdapterCardViewList;

import java.util.Calendar;

import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT;


public class Schedule extends HamburgerMenu {
    private CalendarView cal;
    private Calendar dater = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_item_cardview);

        cal = findViewById(R.id.shcedule_calenderView);

        CallViewAdapter(SEARCH_RESULT);

        cal.setDate(dater.getTime().getTime());
    }
}