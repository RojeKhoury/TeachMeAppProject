package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AddOrRemoveLessonAdapter;
import com.example.teachmeapp.Helpers.Globals;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class TeacherLessonsAddOrRemove extends HamburgerMenu {

    EditText SubjectEditText;
    EditText PriceEditText;

    private RadioGroup levelSelection;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<TeacherLessonRow> lessons;

    private String currentLevel = "";
    private Integer currentLevelNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teacher_lessons_add_or_remove);
        SubjectEditText = findViewById(R.id.EditTeacherLessonsSubject);
        PriceEditText = findViewById(R.id.EditTeacherLessonsPrice);
        levelSelection = findViewById(R.id.group_level_selection);

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
                HashMap userLesson = (HashMap) lesson.getValue();
                TeacherLessonRow temp = new TeacherLessonRow(userLesson.get(Globals.SUBJECT).toString(),
                        ((Long) userLesson.get(Globals.LEVEL)).intValue(), userLesson.get(Globals.FIELD_PRICE).toString());
                lessons.add(temp);
            }
        }
        adapter = new AddOrRemoveLessonAdapter(lessons, this);
        recyclerView.setAdapter(adapter);
        //CallViewAdapter(LESSONS_FOR_TEACHER_VIEW);
    }

    public void OnClick_add_lessons_button(View view) {

        //TODO Check if level is good for database

        if (levelSelection.getCheckedRadioButtonId() == -1) {
            Toast.makeText(TeacherLessonsAddOrRemove.this, "Please select the desired level!", Toast.LENGTH_LONG).show();
        } else {

            if (levelSelection.getCheckedRadioButtonId() == R.id.radioButtonElementary) {
                currentLevel = "Elementary";
                currentLevelNum = 0;
            } else if (levelSelection.getCheckedRadioButtonId() == R.id.radioButtonMiddleSchool) {
                currentLevel = "MiddleSchool";
                currentLevelNum = 1;
            } else if (levelSelection.getCheckedRadioButtonId() == R.id.radioButtonHighSchool) {
                currentLevel = "HighSchool";
                currentLevelNum = 2;
            } else {
                currentLevel = "College";
                currentLevelNum = 3;
            }

            String subject = SubjectEditText.getText().toString() + "_" + currentLevel;
            String price = PriceEditText.getText().toString();

            if (!SubjectEditText.getText().toString().isEmpty()) {
                if (!price.isEmpty()) {
                    comm.addCourse(subject, comm.getUid(), Double.parseDouble(price), currentLevelNum);
                    SubjectEditText.setText("");
                    PriceEditText.setText("");

                    //lessons.add(new TeacherLessonRow(subject, currentLevelNum, price));
                    recycleViewFill();
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(this, "Please add a Price in $", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please add a Subject", Toast.LENGTH_SHORT).show();
            }

        }


    }

    public void realtimeDataUpdate() {
        comm.getDb().collection(COLLECTION_TEACHER).document(comm.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null && value != null && value.exists())
                {
        recycleViewFill();}
    }});}


    public void removeLesson(String lesson) {
        comm.removeCourseFromTeacher(lesson);
    }
}