package com.example.teachmeapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.teachmeapp.Adapter.FragmentAdapter;
import com.example.teachmeapp.Helpers.Globals;
import com.google.android.material.tabs.TabLayout;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class maps_activity_get_location extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_get_location);

        tabLayout = findViewById(R.id.tabLayoutChooseLocation);
        viewPager = findViewById(R.id.viewPagerChooseLocation);

        if (Globals.locationOrSignUp){

            FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), this, Globals.MAPS_CHOOSE_LOCATION);
            viewPager.setAdapter(fragmentAdapter);

        }

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Toast.makeText(getApplicationContext(), "Please choose your location", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        comm.getFirebaseUser().delete();
        super.onBackPressed();
    }
}