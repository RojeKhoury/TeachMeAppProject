package com.example.teachmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginAsTeacherOrStudent extends HamburgerMenu
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_teacher_or_student);
    }

    public void onClick_OpenStudentHomePage(View view)
    {
        Intent intent = new Intent(this, HomePageStudent.class);
        startActivity(intent);
    }

    public void onClick_OpenTeacherHomePage(View view)
    {
        Intent intent = new Intent(this, HomePageTeacher.class);
        startActivity(intent);
    }
}