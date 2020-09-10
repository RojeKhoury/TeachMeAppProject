package com.example.teachmeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleExpandingButton extends AppCompatActivity {
        // TODO put data into the vaules below from database
    ImageView imageView = findViewById(R.id.ScheduleExpanding_ImageView);
    TextView textViewSubject = findViewById(R.id.ScheduleExpanding_TextView_Subject);
    TextView textViewLevel = findViewById(R.id.ScheduleExpanding_TextView_Level);
    TextView textViewTimeStart = findViewById(R.id.ScheduleExpanding_TextView_TimeStart_Value);
    TextView textViewTimeEnd = findViewById(R.id.ScheduleExpanding_TextView_TimeEnd_Value);
    TextView textViewAddress = findViewById(R.id.ScheduleExpanding_TextView_Address);

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
        //TODO Delete Lesson from data base
        super.onBackPressed();// to go back to the previous activity
    }
}
