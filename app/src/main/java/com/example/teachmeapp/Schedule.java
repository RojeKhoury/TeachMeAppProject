package com.example.teachmeapp;


import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.example.teachmeapp.Helpers.BookedLesson;
import com.example.teachmeapp.Helpers.Globals;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT_FOR_SCHDULE;
import static com.example.teachmeapp.Helpers.Globals.comm;


public class Schedule extends HamburgerMenu {
    private CalendarView cal;
    private Calendar dater = Calendar.getInstance();
    ArrayList<BookedLesson> lessonsToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        lessonsToShow = new ArrayList<>();

        cal = findViewById(R.id.shcedule_calenderView);

        cal.setDate(dater.getTime().getTime());

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                DocumentReference ref = comm.getDocumentReference(comm.getUid(), comm.isM_teacher());
                com.example.teachmeapp.Helpers.Calendar cal = (com.example.teachmeapp.Helpers.Calendar) ref.get().getResult().get(Globals.FIELD_SCHEDULE);

                for(BookedLesson lesson: cal.getSchedule())
                {
                    if(lesson.getTimeStart().toDate().getDay() == day && lesson.getTimeStart().toDate().getYear() == year && lesson.getTimeStart().toDate().getMonth() == month)
                    {
                        lessonsToShow.add(lesson);
                        TempStringArray1[0] = lesson.getTeacherStudentName();
                        TempStringArray2[0] = lesson.getLesson().getName();
                        TempStringArray3[0] = lesson.getTimeStart().toString();
                    }

                    CombineArrays();
                }
            }
        });
        CallViewAdapter(SEARCH_RESULT_FOR_SCHDULE);
    }

}