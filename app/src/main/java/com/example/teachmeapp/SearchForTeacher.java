package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.teachmeapp.Helpers.Globals;
import com.google.firebase.firestore.CollectionReference;

import java.util.Arrays;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_NAME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_TEACHER_FOR_LESSON;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class SearchForTeacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_teacher);
    }
    public void searchForTeachers()
    {
        //here I am assuming that the data was collected so these are temporary values that need to be changed when the page is done
        String subject = "math";
        float maxPrice = 150;
        Boolean zoom = false;

        CollectionReference teacherRef = comm.db.collection(COLLECTION_TEACHER);
        teacherRef.whereArrayContains(FIELD_LESSONS, subject).whereEqualTo(FIELD_ZOOM, false);

    }


}