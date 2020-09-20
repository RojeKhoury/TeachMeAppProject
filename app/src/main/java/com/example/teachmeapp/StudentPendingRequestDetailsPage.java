package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentPendingRequestDetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pending_request_details_page);

        String Subject = getIntent().getStringExtra("Subject");
        String Price = getIntent().getStringExtra("Price");
        String Level = getIntent().getStringExtra("Level");
        String Status = getIntent().getStringExtra("Status");

        ImageView imageView = findViewById(R.id.StudentPendingRequestDetailsPage_ImageView);
        TextView textViewSubject =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Subject);
        TextView textViewLevel =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Level);
        TextView textViewPrice =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Price);
        TextView textViewStatus =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Status);
        TextView textViewTimeStartValue =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_TimeStart_Value);
        TextView textViewTimeEndValue =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_TimeEnd_Value);
        TextView textViewAddress =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Address);


        textViewSubject.setText(Subject);
        textViewLevel.setText(Level);
        textViewPrice.setText(Price);
        textViewStatus.setText(Status);
        //TODO do u need params to locate the student pending request ?or all above is enough .. we need imageview timestart/end and address
        // then  place them in the correct space or tell me after ill do it abed.
        //TODO @abed we need to call the function comm.getViewedUserData(uid, !isTeacher()) and then call the function comm.keyBuilder(subject, startTime)
        // this will give a key string that will allow us to do whatever we want with the bookedlesson

    }

    public void OnClick_StudentPending_RequestDetails_PageCancelButton(View view) {
        //TODO delete/cancel lesson here
    }

    public void OnClick_StudentPending_RequestDetails_PageBackButton(View view) {
        super.onBackPressed();
    }
}