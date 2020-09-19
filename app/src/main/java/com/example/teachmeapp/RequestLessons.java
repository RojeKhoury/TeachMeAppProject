package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class RequestLessons extends HamburgerMenu {

    TextView textViewTeacherName;
    TextView textViewPrice;

    EditText editTextTimeStart;
    EditText editTextTimeEnd;
    Spinner spinnerSubject;
    Spinner spinnerLevel;

    RadioButton radioButtonAtStudentPlace;
    RadioButton radioButtonAtTeacherPlace;
    RadioButton radioButtonZoom;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_lessons);

        //TODO i need data from teacher what he teachs and so on subjects,levels,prices
        textViewTeacherName = findViewById(R.id.TeacherName);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        spinnerLevel = findViewById(R.id.spinnerLevel);
        textViewPrice = findViewById(R.id.textViewPrice);

        editTextTimeStart = findViewById(R.id.editTextTimeStart);
        editTextTimeEnd = findViewById(R.id.editTextTimeEnd);
        radioButtonAtStudentPlace = findViewById(R.id.radioButtonAtStudentPlaceGetLesson);
        radioButtonAtTeacherPlace = findViewById(R.id.radioButtonAtTeacherPlaceGetLesson);
        radioButtonZoom = findViewById(R.id.radioButtonZoomGetLesson);
        calendarView = findViewById(R.id.calendarView);
    }

    public void Onclick_RequestLesson(View view) {
        //TODO put data into requesting lesson from student to teacher
        super.onBackPressed();
    }
}