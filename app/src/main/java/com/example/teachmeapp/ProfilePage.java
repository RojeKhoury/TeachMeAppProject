package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.core.content.ContextCompat;

import com.example.teachmeapp.Helpers.Globals;


public class ProfilePage extends HamburgerMenu {


    private ListView profile_page_list_lesson_offered = findViewById(R.id.profile_page_list_lesson_offered);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
    }

    public void onToggleStar(View view) {
        final ImageButton ButtonStar = findViewById(R.id.profile_page_btn_favorite_star);
        if (ButtonStar.getTag() == "on") {
            ButtonStar.setTag("off");
            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_btn_star_off));

        } else {
            ButtonStar.setTag("on");
            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_btn_star_on));

        }
    }

    public void ImageView_profile_page_check_or_cancel(boolean trueForCheck_falseForCancel, int numberPlacement) {
        switch (numberPlacement) {
            case Globals.AT_MY_PLACE:
                ImageView imageView = findViewById(R.id.profile_page_image_view_check_or_cancel_at_my_place);
                if (trueForCheck_falseForCancel) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.check_icon));
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cancel_icon));
                }
            case Globals.AT_YOUR_PLACE:
                imageView = findViewById(R.id.profile_page_image_view_check_or_cancel_at_your_place);
                if (trueForCheck_falseForCancel) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.check_icon));
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cancel_icon));
                }
            case Globals.ZOOM:
                imageView = findViewById(R.id.profile_page_image_view_check_or_cancel_zoom);
                if (trueForCheck_falseForCancel) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.check_icon));
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cancel_icon));
                }
                break;
            default:
                break;
        }

    }

    public void OnClick_profile_button_check_location(View view) {

    }

    public void OnClick_profile_button_check_radius(View view) {

    }

    public void OnClick_profile_button_get_lessons_now(View view) {

    }
}
