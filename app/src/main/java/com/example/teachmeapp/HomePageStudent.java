package com.example.teachmeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class HomePageStudent extends HamburgerMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_student);

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
    public void onClick_searchButton(View view)
    {
        Intent intent = new Intent(this, SearchForTeacher.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                        System.exit(0);
                    }
                }).create().show();
    }
}