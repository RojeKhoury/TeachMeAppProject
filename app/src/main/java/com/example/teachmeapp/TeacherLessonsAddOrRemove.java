package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static com.example.teachmeapp.Helpers.Globals.LESSONS_FOR_TEACHER_VIEW;

public class TeacherLessonsAddOrRemove extends HamburgerMenu {
    String s1[] = {"Math", "biology", "chemistry"};
    String s2[] = {"50", "20", "40"};
    String s3[] = {"middle school", "elementary", "college"};
    Button MoreInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lessons_add_or_remove);

        //TODO pull data from data base and call view adapter using them
        CallViewAdapter(LESSONS_FOR_TEACHER_VIEW, this, s1, s2, s3, MoreInfoButton, null, null);

    }

    public void OnClick_add_lessons_button(View view) {
        EditText SubjectEditText = findViewById(R.id.EditTeacherLessonsSubject);
        EditText PriceEditText = findViewById(R.id.EditTeacherLessonsPrice);
        Spinner EducationSpinner = findViewById(R.id.SpinnerTeacherLessonsEducationLevel);

        String Subject = SubjectEditText.getText().toString();
        String Price = PriceEditText.getText().toString();
        String Education = EducationSpinner.getContext().toString();

        //TODO Add params to database
    }
}