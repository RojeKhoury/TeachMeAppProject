package com.example.teachmeapp;

import android.os.Bundle;

import static com.example.teachmeapp.Helpers.Globals.STUDENT_PENDING_REQUESTS_VIEW;

public class StudentPendingRequests extends HamburgerMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pending_requests);

        CallViewAdapter(STUDENT_PENDING_REQUESTS_VIEW);
    }
}