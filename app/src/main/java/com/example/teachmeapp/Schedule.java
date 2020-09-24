package com.example.teachmeapp;


import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.example.teachmeapp.Helpers.BookedLesson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_SCHEDULE;
import static com.example.teachmeapp.Helpers.Globals.comm;


public class Schedule extends HamburgerMenu {
    private CalendarView cal;
    private Calendar dater = Calendar.getInstance();
    Map<String, BookedLesson> lessonsToShow;
    com.example.teachmeapp.Helpers.Schedule calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        lessonsToShow = new HashMap<>();

        cal = findViewById(R.id.shcedule_calenderView);

        cal.setDate(dater.getTime().getTime());

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, final int year, final int month, final int day) {
                DocumentReference ref = comm.getDocumentReference(comm.getUid(), comm.isTeacher());
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            calendar = new com.example.teachmeapp.Helpers.Schedule((HashMap<String, BookedLesson>) document.get(FIELD_SCHEDULE));

                            if (calendar.getLessons().get(FIELD_LESSONS) != null) {
                                for (Map.Entry lesson : calendar.getLessons().entrySet()) {
                                    if (((BookedLesson) lesson.getValue()).getTimeStart().getDayOfMonth() == day && ((BookedLesson) lesson.getValue()).getTimeStart().getYear() == year && ((BookedLesson) lesson.getValue()).getTimeStart().getMonthValue() == month)
                                    {
                                        lessonsToShow.put((String) lesson.getKey(), (BookedLesson) lesson.getValue());
                                        TempStringArray1[0] = ((BookedLesson) lesson.getValue()).getTeacherName();
                                        TempStringArray2[0] = ((BookedLesson) lesson.getValue()).getLesson().getName();
                                        TempStringArray3[0] = ((BookedLesson) lesson.getValue()).getTimeStart().toString();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
    }

}