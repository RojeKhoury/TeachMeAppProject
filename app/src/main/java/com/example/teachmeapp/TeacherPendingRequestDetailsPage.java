package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teachmeapp.Helpers.BookedLesson;
import com.example.teachmeapp.Helpers.Globals;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class TeacherPendingRequestDetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_pending_request_details_page);
        String Subject = getIntent().getStringExtra("Subject");
        String Price = getIntent().getStringExtra("Price");
        String Level = getIntent().getStringExtra("Level");
        BookedLesson lesson = (BookedLesson) getIntent().getExtras().get(Globals.BOOKED_LESSON);
        String UID = getIntent().getStringExtra("UID");


        //comm.getViewedUserData(UID, !comm.isTeacher());

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
<<<<<<< Updated upstream
        String startTime = "this is a time";//TODO Check startTime
        comm.getViewedUserData("UID", !comm.isTeacher());
        comm.deleteLessonRequest(comm.keyBuilder("Subject", startTime));
=======
        //TODO pahri here u Delete class from database and the function below after ur done is for going back activity
        // comm.removePendingBookedLesson();
        String startTime = "this is a time";
      //  comm.getViewedUserData("UID", !comm.isTeacher());
        comm.deleteLessonRequest(comm.keyBuilder("Subject", startTime, "teacher name", "student name"));
>>>>>>> Stashed changes
        //comm.deleteLessonRequest();
        super.onBackPressed();
    }

    public void AcceptClass(View view) {
<<<<<<< Updated upstream
        String startTime = "this is a time";//TODO Check startTime
        comm.getViewedUserData("UID", !comm.isTeacher());
        comm.acceptLessonRequest(comm.keyBuilder("Subject", startTime));
=======
        //TODO pahri here u Accept class from database and the function below after ur done is for going back activity
        String startTime = "this is a time";
        //comm.getViewedUserData("UID", !comm.isTeacher());
        comm.acceptLessonRequest(comm.keyBuilder("Subject", startTime,"teacher name", "student name"));
>>>>>>> Stashed changes
        super.onBackPressed();
    }
}