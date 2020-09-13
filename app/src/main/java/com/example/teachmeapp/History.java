package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.teachmeapp.Adapter.AdapterCardViewList;

import static com.example.teachmeapp.Helpers.Globals.HISTORY_OF_LESSONS_VIEW;

public class History extends HamburgerMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        CallViewAdapter(HISTORY_OF_LESSONS_VIEW);
    }
}