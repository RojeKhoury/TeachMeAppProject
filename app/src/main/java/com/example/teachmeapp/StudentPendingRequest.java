package com.example.teachmeapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AddOrRemoveLessonAdapter;
import com.example.teachmeapp.Adapter.StudentPendingRequestsAdapter;
import com.example.teachmeapp.Helpers.Globals;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_STUDENT;
import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.LESSONS_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.TEACHERS_HOME;
import static com.example.teachmeapp.Helpers.Globals.TEACHER_PENDING_REQUESTS_VIEW;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class StudentPendingRequest extends HamburgerMenu {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<StudentPendingRequestRow> lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pending_requests);

       // CallViewAdapter(TEACHER_PENDING_REQUESTS_VIEW);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_MyLessons);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lessons = new ArrayList<>();

        realtimeDataUpdate();

        recycleViewFill();
    }

    private void recycleViewFill() {

        lessons.clear();

        if (comm.getUserPendingLessons() != null) {
            for (Map.Entry lesson : comm.getUserPendingLessons().getLessons().entrySet()) {
                String name = (String) lesson.getKey();
                HashMap pendingLesson = (HashMap)lesson.getValue();

                StudentPendingRequestRow temp = new StudentPendingRequestRow(pendingLesson.get(Globals.TEACHER_UID).toString(),
                        pendingLesson.get(Globals.STUDENT_NAME).toString(),
                        (Timestamp)pendingLesson.get(Globals.TIME_START), (Timestamp)pendingLesson.get(Globals.TIME_END),
                        pendingLesson.get(Globals.TEACHER_NAME).toString(), (Boolean) pendingLesson.get(Globals.FIELD_ZOOM),
                        (Boolean) pendingLesson.get(Globals.STUDENTS_HOME), (Boolean) pendingLesson.get(TEACHERS_HOME),
                        ((HashMap)pendingLesson.get(Globals.FIELD_LESSONS)).get(Globals.FIELD_NAME).toString(),
                        Double.parseDouble(((HashMap)pendingLesson.get(Globals.FIELD_LESSONS)).get(Globals.FIELD_PRICE).toString()),
                        ((HashMap)pendingLesson.get(Globals.FIELD_LESSONS)).get(Globals.FIELD_LEVEL).toString());

                lessons.add(temp);
            }
        }


        adapter = new StudentPendingRequestsAdapter(lessons, this);

        recyclerView.setAdapter(adapter);
        //CallViewAdapter(LESSONS_FOR_TEACHER_VIEW);
    }

    private void realtimeDataUpdate()
    {
        String collection = (comm.isTeacher()) ? COLLECTION_TEACHER : COLLECTION_STUDENT;
        comm.getDb().collection(collection).document(comm.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null && value != null && value.exists())
                {
                    recycleViewFill();
                }}
        });
    }
}
