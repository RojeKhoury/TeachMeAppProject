package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teachmeapp.Helpers.Globals;

public class StudentPendingRequestDetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pending_request_details_page);

        StudentPendingRequestRow pendingRequestRow = getIntent().getParcelableExtra(Globals.BOOKED_LESSON);

        ImageView imageView = findViewById(R.id.StudentPendingRequestDetailsPage_ImageView);
        TextView textViewSubject =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Subject);
        TextView textViewLevel =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Level);
        TextView textViewPrice =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Price);
        TextView textViewStatus =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Status);
        TextView textViewTimeStartValue =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_TimeStart_Value);
        TextView textViewTimeEndValue =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_TimeEnd_Value);
        TextView textViewAddress =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Address);

    }

    public void OnClick_StudentPending_RequestDetails_PageCancelButton(View view) {
        //TODO delete/cancel lesson here
    }

    public void OnClick_StudentPending_RequestDetails_PageBackButton(View view) {
        super.onBackPressed();
    }
}