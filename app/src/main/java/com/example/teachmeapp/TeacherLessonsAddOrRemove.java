package com.example.teachmeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.teachmeapp.Helpers.Globals;

import static com.example.teachmeapp.Helpers.Globals.LESSONS_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class TeacherLessonsAddOrRemove extends HamburgerMenu {

    EditText SubjectEditText;
    EditText PriceEditText;
    Spinner EducationSpinner;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lessons_add_or_remove);
        SubjectEditText = findViewById(R.id.EditTeacherLessonsSubject);
        PriceEditText = findViewById(R.id.EditTeacherLessonsPrice);
        EducationSpinner = findViewById(R.id.SpinnerTeacherLessonsEducationLevel);

        CallViewAdapter(LESSONS_FOR_TEACHER_VIEW);
    }

    public void OnClick_add_lessons_button(View view) {

        String subject = SubjectEditText.getText().toString();
        String price = PriceEditText.getText().toString();
        level = EducationSpinner.getSelectedItem().toString();
        if (subject!=null){
            if(price!=null){
                comm.addCourse(subject, comm.getUid(), Double.parseDouble(price), level);
            }else{
                Toast.makeText(this, "Please add a Price in $", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please add a Subject", Toast.LENGTH_SHORT).show();
        }
    }

}