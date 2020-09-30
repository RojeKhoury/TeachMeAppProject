package com.example.teachmeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleExpandingButton extends AppCompatActivity {

    ImageView imageView;
    TextView textViewSubject;
    TextView textViewLevel;
    TextView textViewTimeStart;
    TextView textViewTimeEnd;
    TextView textViewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_expanding_button);

    }

    public void OnClick_schedule_expanding_button_back(View view) {
        super.onBackPressed();
    }

    public void OnClick_schedule_expanding_button_cancel_lesson(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Lesson?")
                .setMessage("Are you sure you want to cancel the lesson?")
                .setNegativeButton("Back", null)
                .setPositiveButton("Yes, Cancel Lesson", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        DeleteLessonFromDataBase();
                    }
                }).create().show();
    }


    private void DeleteLessonFromDataBase() {

        imageView = findViewById(R.id.ScheduleExpanding_ImageView);
        textViewSubject = findViewById(R.id.ScheduleExpanding_TextView_Subject);
        textViewLevel = findViewById(R.id.ScheduleExpanding_TextView_Level);
        textViewTimeStart = findViewById(R.id.ScheduleExpanding_TextView_TimeStart_Value);
        textViewTimeEnd = findViewById(R.id.ScheduleExpanding_TextView_TimeEnd_Value);
        textViewAddress = findViewById(R.id.ScheduleExpanding_TextView_Address);


        //comm.removeBookedLesson(key));
        // comm.removeLessonFomSchedule();
        super.onBackPressed();// to go back to the previous activity
    }
}
