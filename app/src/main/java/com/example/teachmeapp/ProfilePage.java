package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;

public class ProfilePage extends HamburgerMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
    }

    public void onToggleStar(View view) {

    }
}