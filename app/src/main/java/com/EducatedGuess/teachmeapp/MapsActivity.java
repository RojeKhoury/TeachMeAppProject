package com.EducatedGuess.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.EducatedGuess.teachmeapp.Adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import static com.EducatedGuess.teachmeapp.Helpers.Globals.MAPS_REGULAR;

public class MapsActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this, MAPS_REGULAR);
        viewPager.setAdapter(fragmentAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }


}