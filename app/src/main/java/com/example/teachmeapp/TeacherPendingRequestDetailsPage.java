package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class TeacherPendingRequestDetailsPage extends AppCompatActivity {

    /*TODO we need fields for:  @ABED
    *  1. checkbox zoom
    * 2. checkbox teacher's place
    * 3. checkbox student's place
    * 4. subject
    * 5. level
    **** price and the other person name and the other user UID will need to be transfered without being a field.*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_pending_request_details_page);
        String Subject = getIntent().getStringExtra("data1");
        String Price = getIntent().getStringExtra("data2");
        String Level = getIntent().getStringExtra("data3");

        TextView textViewSubject = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_Subject);
        textViewSubject.setText(Subject);
        TextView textViewPrice = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_Price);
        textViewPrice.setText(Price);
        TextView textViewLevel = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_Level);
        textViewLevel.setText(Level);

        //TODO pahri look at those set text, get the params from the data base and place them in there... theres TimeStart,TimeEnd,Address,
        TextView textViewTimeStart = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_TimeStart_Value);
        textViewTimeStart.setText("");
        TextView textViewTimeEnd = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_TimeEnd_Value);
        textViewTimeEnd.setText("");
        TextView textViewTimeAddress = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_Address);
        textViewTimeAddress.setText("");


    }

    public void BackButton(View view) {
        super.onBackPressed();
    }

    public void RejectClass(View view) {
        //TODO pahri here u Delete class from database and the function below after ur done is for going back activity
       // comm.removePendingBookedLesson();
        super.onBackPressed();
    }

    public void AcceptClass(View view) {
        //TODO pahri here u Accept class from database and the function below after ur done is for going back activity
        super.onBackPressed();
    }
}