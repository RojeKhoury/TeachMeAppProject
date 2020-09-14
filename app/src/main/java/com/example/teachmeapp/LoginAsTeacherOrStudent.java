package com.example.teachmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import static com.example.teachmeapp.Helpers.Globals.comm;
import static com.example.teachmeapp.SignUp.TAG;

public class LoginAsTeacherOrStudent extends HamburgerMenu {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_teacher_or_student);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        DocumentReference ref = comm.getStorageRef();
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                    } else {
                        Intent intent = new Intent(getApplicationContext(), HomePageStudent.class);
                        startActivity(intent);
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    public void onClick_OpenStudentHomePage(View view) {
        comm.setM_teacher(false);
        Intent intent = new Intent(this, HomePageStudent.class);
        startActivity(intent);
    }

    public void onClick_OpenTeacherHomePage(View view) {
        comm.setM_teacher(true);
        Intent intent = new Intent(this, HomePageTeacher.class);
        startActivity(intent);
    }

}