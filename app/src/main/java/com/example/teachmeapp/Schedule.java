package com.example.teachmeapp;


import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.example.teachmeapp.Helpers.BookedLesson;
import com.google.firebase.firestore.DocumentReference;

import java.util.Calendar;

import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT;
import static com.example.teachmeapp.Helpers.Globals.comm;


public class Schedule extends HamburgerMenu {
    private CalendarView cal;
    private Calendar dater = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_item_cardview);

        cal = findViewById(R.id.shcedule_calenderView);

        cal.setDate(dater.getTime().getTime());

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                DocumentReference ref = comm.getDocumentReference(comm.getUid(), comm.isM_teacher());
                com.example.teachmeapp.Helpers.Calendar cal = (com.example.teachmeapp.Helpers.Calendar) ref.get().getResult().get("schedule");
                for(BookedLesson lesson: cal.getSchedule())
                {
                    if(lesson.getTimeStart().toDate().getDay() == day && lesson.getTimeStart().toDate().getYear() == year && lesson.getTimeStart().toDate().getMonth() == month)
                    {
                        addLessonToList(lesson);//here the list will be created
                    }
                }
            }
        });
    }

    private void addLessonToList(BookedLesson lesson) {
        CallViewAdapter(SEARCH_RESULT);
        //TODO do your thing abed!
        //uid is there so if they click on the lesson a page will open with more details and among the details the teacher's/student info
    }
}