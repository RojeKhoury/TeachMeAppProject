package com.example.teachmeapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AddOrRemoveLessonAdapter;
import com.example.teachmeapp.Adapter.SearchForTeacherAdapter;
import com.example.teachmeapp.Adapter.StudentPendingRequestsAdapter;
import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.Helpers.UserLesson;
import com.example.teachmeapp.model.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.teachmeapp.Helpers.Globals.CITY;
import static com.example.teachmeapp.Helpers.Globals.COLLECTION_STUDENT;
import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.COUNTRY;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSON_TOPIC_LIST;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LEVEL;
import static com.example.teachmeapp.Helpers.Globals.FIELD_NAME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_PRICE;
import static com.example.teachmeapp.Helpers.Globals.FIELD_STUDENTHOME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_SURNAME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_TEACHERHOME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_UID;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.example.teachmeapp.Helpers.Globals.LESSONS_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.LEVEL;
import static com.example.teachmeapp.Helpers.Globals.RATINGS;
import static com.example.teachmeapp.Helpers.Globals.TEACHERS_HOME;
import static com.example.teachmeapp.Helpers.Globals.TEACHER_PENDING_REQUESTS_VIEW;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class StudentPendingRequest extends HamburgerMenu {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Request> lessons;
    private boolean shouldExecuteOnResume;
    private TextView emptyListTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pending_requests);

        shouldExecuteOnResume = false;
       // CallViewAdapter(TEACHER_PENDING_REQUESTS_VIEW);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPendingRequestStudent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyListTextView=findViewById(R.id.emptyView);
        lessons = new ArrayList<>();

        retrievePendingRequests();

        //realtimeDataUpdate();

        //recycleViewFill();
    }

    private void recycleViewFill() {

        /*lessons.clear();

        if (comm.getUserPendingLessons() != null) {
            for (Map.Entry lesson : comm.getUserPendingLessons().getLessons().entrySet()) {
                String name = (String) lesson.getKey();
                HashMap pendingLesson = (HashMap)lesson.getValue();

                StudentPendingRequestRow temp = new StudentPendingRequestRow(pendingLesson.get(Globals.STUDENT_ID).toString(), pendingLesson.get(Globals.TEACHER_UID).toString(),
                        pendingLesson.get(Globals.STUDENT_NAME).toString(),
                        (LocalDateTime)pendingLesson.get(Globals.TIME_START), (LocalDateTime)pendingLesson.get(Globals.TIME_END),
                        pendingLesson.get(Globals.TEACHER_NAME).toString(), (Boolean) pendingLesson.get(Globals.FIELD_ZOOM),
                        (Boolean) pendingLesson.get(Globals.STUDENTS_HOME), (Boolean) pendingLesson.get(TEACHERS_HOME),
                        ((HashMap)pendingLesson.get(Globals.FIELD_LESSONS)).get(Globals.FIELD_NAME).toString(),
                        Double.parseDouble(((HashMap)pendingLesson.get(Globals.FIELD_LESSONS)).get(Globals.FIELD_PRICE).toString()),
                        ((HashMap)pendingLesson.get(Globals.FIELD_LESSONS)).get(Globals.FIELD_LEVEL).toString());

                lessons.add(temp);
            }
        }


        //adapter = new StudentPendingRequestsAdapter(lessons, this);

        recyclerView.setAdapter(adapter);
        //CallViewAdapter(LESSONS_FOR_TEACHER_VIEW);*/
    }

    private void retrievePendingRequests(){

        lessons.clear();
        if (adapter != null)
        adapter.notifyDataSetChanged();

        final CollectionReference pendingRequestsRef = comm.db.collection("Request");

        if (!comm.isTeacher()){

            pendingRequestsRef.whereEqualTo("m_studentUID", comm.getUid()).whereEqualTo("pending", true)
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

                            lessons.add(request);
                        }

                        pendingRequestsRef.whereEqualTo("m_studentUID", comm.getUid()).whereEqualTo("rejecting", true)
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

                                        lessons.add(request);
                                    }

                                    adapter = new StudentPendingRequestsAdapter(lessons, StudentPendingRequest.this);
                                    if(lessons.isEmpty())
                                    {
                                        emptyListTextView.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        emptyListTextView.setVisibility(View.GONE);
                                    }
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        });

                    }
                }
            });

        } else {

            pendingRequestsRef.whereEqualTo("m_teacherUID", comm.getUid()).whereEqualTo("pending", true)
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

                            lessons.add(request);
                        }
                        adapter = new StudentPendingRequestsAdapter(lessons, StudentPendingRequest.this);

                        if(lessons.isEmpty())
                        {
                            emptyListTextView.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            emptyListTextView.setVisibility(View.GONE);
                        }

                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }
            });

        }

    }

    @Override
    public void onResume(){

        if(shouldExecuteOnResume){

            retrievePendingRequests();

        } else{
            shouldExecuteOnResume = true;
        }


        super.onResume();


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
