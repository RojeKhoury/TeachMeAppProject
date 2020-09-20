package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AddOrRemoveLessonAdapter;
import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.Helpers.UserLesson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_STUDENT;
import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_SCHEDULE;
import static com.example.teachmeapp.Helpers.Globals.LESSONS_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class TeacherLessonsAddOrRemove extends HamburgerMenu {

    EditText SubjectEditText;
    EditText PriceEditText;
    Spinner EducationSpinner;
    String level;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<TeacherLessonRow> lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teacher_lessons_add_or_remove);
        SubjectEditText = findViewById(R.id.EditTeacherLessonsSubject);
        PriceEditText = findViewById(R.id.EditTeacherLessonsPrice);
        EducationSpinner = findViewById(R.id.SpinnerTeacherLessonsEducationLevel);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_MyLessons);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lessons = new ArrayList<>();

        realtimeDataUpdate();

        recycleViewFill();
    }

    private void recycleViewFill() {

        lessons.clear();

        if (comm.getUserLessons() != null) {
            for (Map.Entry lesson : comm.getUserLessons().entrySet()) {
                String name = (String) lesson.getKey();
                HashMap userLesson = (HashMap)lesson.getValue();
                TeacherLessonRow temp = new TeacherLessonRow(userLesson.get(Globals.SUBJECT).toString(), userLesson.get(Globals.LEVEL).toString(), userLesson.get(Globals.FIELD_PRICE).toString());
                lessons.add(temp);
            }
        }


        adapter = new AddOrRemoveLessonAdapter(lessons, this);

        recyclerView.setAdapter(adapter);
        //CallViewAdapter(LESSONS_FOR_TEACHER_VIEW);
    }

    public void OnClick_add_lessons_button(View view) {

        String subject = SubjectEditText.getText().toString();
        String price = PriceEditText.getText().toString();
        level = EducationSpinner.getSelectedItem().toString();

        if (subject != null) {
            if (price != null) {
                comm.addCourse(subject, comm.getUid(), Double.parseDouble(price), level);
                SubjectEditText.setText("");
                PriceEditText.setText("");
            } else {
                Toast.makeText(this, "Please add a Price in $", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please add a Subject", Toast.LENGTH_SHORT).show();
        }
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

    public void removeLesson(String lesson) {
        comm.removeCourseFromTeacher(lesson);
    }
}