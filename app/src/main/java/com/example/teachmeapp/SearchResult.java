package com.example.teachmeapp;

import android.os.Bundle;

import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT;

public class SearchResult extends HamburgerMenu {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_result);

            CallViewAdapter(SEARCH_RESULT);
        }

}
