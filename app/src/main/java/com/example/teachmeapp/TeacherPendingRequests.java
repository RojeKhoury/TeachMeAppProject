package com.example.teachmeapp;

import android.os.Bundle;
import android.telecom.Call;

import static com.example.teachmeapp.Helpers.Globals.TEACHER_PENDING_REQUESTS_VIEW;

public class TeacherPendingRequests extends HamburgerMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_pending_requests);

        CallViewAdapter(TEACHER_PENDING_REQUESTS_VIEW);
    }
}