package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends HamburgerMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);






    }

    public void onClick_FavoriteButton(View view)
    {
        Intent intent = new Intent(this, FavoriteTeachers.class);
        startActivity(intent);
    }

    public void onClick_conversationButton(View view)
    {
        Intent intent = new Intent(this, ConversationChatPage.class);
        startActivity(intent);
    }

    public void onClick_historyButton(View view)
    {
        Intent intent = new Intent(this, HistoryClassesPage.class);
        startActivity(intent);
    }

    public void onClick_scheduleButton(View view)
    {
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    public void onClick_recommendationButton(View view)
    {
        Intent intent = new Intent(this, RecommendationForStudent.class);
        startActivity(intent);
    }

    public void onClick_searchButton(View view)
    {
        Intent intent = new Intent(this, SearchForTeacher.class);
        startActivity(intent);
    }
}