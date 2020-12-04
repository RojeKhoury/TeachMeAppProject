package com.EducatedGuess.teachmeapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.EducatedGuess.teachmeapp.Adapter.FragmentAdapter;
import com.EducatedGuess.teachmeapp.Helpers.Globals;
import com.google.android.material.tabs.TabLayout;

import static com.EducatedGuess.teachmeapp.Helpers.Globals.comm;

public class Maps_activity_get_location extends AppCompatActivity {

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
    }

    @Override
    public void onBackPressed() {
        if(Globals.SignedIn == false)
        {comm.getFirebaseUser().delete();}
        super.onBackPressed();
    }
}