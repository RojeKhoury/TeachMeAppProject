package com.example.teachmeapp;


import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.example.teachmeapp.Helpers.BookedLesson;
import com.example.teachmeapp.Helpers.Globals;
import com.google.firebase.firestore.DocumentReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT_FOR_SCHDULE;
import static com.example.teachmeapp.Helpers.Globals.comm;


public class Schedule extends HamburgerMenu {
    private CalendarView cal;
    private Calendar dater = Calendar.getInstance();
    Map<String, BookedLesson> lessonsToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        lessonsToShow = new HashMap<>();

        cal = findViewById(R.id.shcedule_calenderView);

        cal.setDate(dater.getTime().getTime());

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                DocumentReference ref = comm.getDocumentReference(comm.getUid(), comm.isTeacher());
                com.example.teachmeapp.Helpers.Schedule cal = (com.example.teachmeapp.Helpers.Schedule) ref.get().getResult().get(Globals.FIELD_SCHEDULE);

                for(Map.Entry lesson: cal.getLessons().entrySet())
                {
                    if(((BookedLesson)lesson.getValue()).getTimeStart().toDate().getDay() == day && ((BookedLesson)lesson.getValue()).getTimeStart().toDate().getYear() == year && ((BookedLesson)lesson.getValue()).getTimeStart().toDate().getMonth() == month)
                    {
                        lessonsToShow.put((String)lesson.getKey(), (BookedLesson) lesson.getValue());
                        TempStringArray1[0] = ((BookedLesson)lesson.getValue()).getTeacherName();
                        TempStringArray2[0] = ((BookedLesson)lesson.getValue()).getLesson().getName();
                        TempStringArray3[0] = ((BookedLesson)lesson.getValue()).getTimeStart().toString();
                    }
                    CombineArrays();
                }
            }
        });
        CallViewAdapter(SEARCH_RESULT_FOR_SCHDULE);
    }

}