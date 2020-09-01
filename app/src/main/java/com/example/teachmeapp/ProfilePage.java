package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;

public class ProfilePage extends HamburgerMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
    }

    public void onToggleStar(View view) {
        final ImageButton ButtonStar = findViewById(R.id.profile_page_btn_favorite_star);
        if (ButtonStar.getTag()=="on"){
            ButtonStar.setTag("off");
            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.favorite_btn_star_off));
        }else{
            ButtonStar.setTag("on");
            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.favorite_btn_star_on));
        }
    }
}