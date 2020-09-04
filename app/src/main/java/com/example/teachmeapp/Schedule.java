package com.example.teachmeapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Schedule extends HamburgerMenu {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

    }

    public void AddButtonAndTextViewToScheduleList(String Name , String Subject , String Time ,int Id) {
        Button button = new Button(this);
        button.setText(Name+"/n"+Subject+"/n"+Time);
        button.setBackgroundColor(R.drawable.buttonshapecool);
        button.setTextColor(getColor(R.color.white));
        button.setId(Id);
        

    }
}