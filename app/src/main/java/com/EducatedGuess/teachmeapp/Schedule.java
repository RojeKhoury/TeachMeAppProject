package com.EducatedGuess.teachmeapp;


import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.EducatedGuess.teachmeapp.Adapter.StudentPendingRequestsAdapter;
import com.EducatedGuess.teachmeapp.model.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.EducatedGuess.teachmeapp.Helpers.Globals.comm;


public class Schedule extends HamburgerMenu {

    private CalendarView cal;
    private Calendar dater = Calendar.getInstance();
    private Timestamp startTime;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Request> acceptedLessons;
    private TextView emptyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        acceptedLessons = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.Recycler_View_schedule_page_results);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cal = findViewById(R.id.shcedule_calenderView);

        cal.setDate(dater.getTime().getTime());
        emptyListView = findViewById(R.id.emptyView);
        startTime = new Timestamp(new java.util.Date(cal.getDate()));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        calendar.setTime(startTime.toDate());

        int month = calendar.get(Calendar.MONTH) + 1;
        retrieveAcceptedRequests(calendar.get(Calendar.YEAR) + "-0" +
                month + "-" + calendar.get(Calendar.DAY_OF_MONTH));

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, final int year, final int month, final int day) {

                int monthNew = month + 1;
                String date = "";
                if (monthNew < 10) {
                    date = year + "-" + "0" + monthNew + "-";
                    if(day < 10)
                    { date += "0" + day;}
                    else
                    {
                        date += day;
                    }
                } else {
                    date = year + "-" + monthNew + "-";
                    if(day < 10)
                    { date += "0" + day;}
                    else
                    {
                        date += day;
                    }
                }
                retrieveAcceptedRequests(date);
            }
        });
    }

    private void retrieveAcceptedRequests(String startDate) {

        acceptedLessons.clear();
        if (adapter != null)
            adapter.notifyDataSetChanged();

        final CollectionReference acceptedRequestsRef = comm.db.collection("Request");

        if (!comm.isTeacher()) {

            acceptedRequestsRef.whereEqualTo("m_studentUID", comm.getUid()).whereEqualTo("accepting", true)
                    .whereEqualTo("m_dateStart", startDate)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Request request = new Request((String) document.get("m_studentUID"),
                                    (String) document.get("m_teacherUID"),
                                    (String) document.get("m_teacherName"),
                                    (String) document.get("m_studentName"),
                                    (String) document.get("m_timeStart"),
                                    (String) document.get("m_timeEnd"),
                                    (Boolean) document.get("m_zoom"),
                                    (Boolean) document.get("m_faceToFace"),
                                    (String) document.get("m_subject"),
                                    (String) document.get("m_price"),
                                    (String) document.get("m_level"),
                                    (Boolean) document.get("pending"),
                                    (Boolean) document.get("accepting"),
                                    (Boolean) document.get("rejecting"),
                                    (String) document.get("m_dateStart"));

                            request.setId(document.getId());

                            acceptedLessons.add(request);
                        }

                        adapter = new StudentPendingRequestsAdapter(acceptedLessons, Schedule.this);
                        if (acceptedLessons.isEmpty()) {
                            emptyListView.setVisibility(View.VISIBLE);
                        } else {
                            emptyListView.setVisibility(View.GONE);
                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }
            });

        } else {

            acceptedRequestsRef.whereEqualTo("m_teacherUID", comm.getUid()).whereEqualTo("accepting", true)
                    .whereEqualTo("m_dateStart", startDate)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Request request = new Request((String) document.get("m_studentUID"),
                                    (String) document.get("m_teacherUID"),
                                    (String) document.get("m_teacherName"),
                                    (String) document.get("m_studentName"),
                                    (String) document.get("m_timeStart"),
                                    (String) document.get("m_timeEnd"),
                                    (Boolean) document.get("m_zoom"),
                                    (Boolean) document.get("m_faceToFace"),
                                    (String) document.get("m_subject"),
                                    (String) document.get("m_price"),
                                    (String) document.get("m_level"),
                                    (Boolean) document.get("pending"),
                                    (Boolean) document.get("accepting"),
                                    (Boolean) document.get("rejecting"),
                                    (String) document.get("m_dateStart"));

                            request.setId(document.getId());

                            acceptedLessons.add(request);
                        }

                        adapter = new StudentPendingRequestsAdapter(acceptedLessons, Schedule.this);
                        if (acceptedLessons.isEmpty()) {
                            emptyListView.setVisibility(View.VISIBLE);
                        } else {
                            emptyListView.setVisibility(View.GONE);
                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }
            });

        }

    }

}