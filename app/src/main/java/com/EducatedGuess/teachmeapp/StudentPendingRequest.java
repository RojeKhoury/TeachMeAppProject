package com.EducatedGuess.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.EducatedGuess.teachmeapp.Adapter.StudentPendingRequestsAdapter;
import com.EducatedGuess.teachmeapp.model.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.EducatedGuess.teachmeapp.Helpers.Globals.COLLECTION_STUDENT;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.comm;

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
