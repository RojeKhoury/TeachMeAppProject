package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.type.Date;
import com.google.type.DateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    Timestamp startTime, endTime;

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
        startTime = new Timestamp(new java.util.Date(calendarView.getDate()));
        endTime = new Timestamp(new java.util.Date(calendarView.getDate()));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime lessonTimeStart = LocalDateTime.of(startTime.toDate().getYear(), startTime.toDate().getMonth(), startTime.toDate().getDay(),
                editTextTimeStart.getText().charAt(0) + editTextTimeStart.getText().charAt(1),
                editTextTimeStart.getText().charAt(3) + editTextTimeStart.getText().charAt(4));
        super.onBackPressed();
    }
}