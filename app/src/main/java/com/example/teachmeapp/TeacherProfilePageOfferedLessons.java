package com.example.teachmeapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherProfilePageOfferedLessons extends AppCompatActivity {
    TextView textViewElementary;
    TextView textViewMiddleSchool;
    TextView textViewHighSchool;
    TextView textViewCollege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile_page_offered_lessons);
        textViewElementary = findViewById(R.id.textViewElementary);
    }
}