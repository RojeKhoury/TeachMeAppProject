package com.example.teachmeapp;


import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AdapterCardViewList;
import com.example.teachmeapp.Helpers.BookedLesson;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.sql.Timestamp;
import java.util.Calendar;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
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

        CallViewAdapter(SEARCH_RESULT);

        cal.setDate(dater.getTime().getTime());

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                DocumentReference ref = comm.getDocumentReference(comm.getUid(), comm.isM_teacher());
                com.example.teachmeapp.Helpers.Calendar cal = (com.example.teachmeapp.Helpers.Calendar) ref.get().getResult().get("schedule");
                for(BookedLesson lesson: cal.getSchedule())
                {
                    if(lesson.getTime().toDate().getDay() == day && lesson.getTime().toDate().getYear() == year && lesson.getTime().toDate().getMonth() == month)
                    {
                        addLessonToList(lesson);
                    }
                }
            }
        });
    }

    private void addLessonToList(BookedLesson lesson) {
        //do your thing abed!
        //uid is there so if they click on the lesson a page will open with more details and among the details the teacher's/student info
    }
}