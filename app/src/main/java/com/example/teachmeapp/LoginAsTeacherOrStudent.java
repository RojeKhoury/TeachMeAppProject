package com.example.teachmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.teachmeapp.Chat.Chats;
import com.example.teachmeapp.Helpers.Globals;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser m_user = mAuth.getCurrentUser();

        DocumentReference ref = db.collection(Globals.COLLECTION_STUDENT).document(m_user.getUid());
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        comm.setTeacher(false);
                        comm.getData();
                        comm.realtimeUpadateMyData();
                        Intent intent = new Intent(getApplicationContext(), HomePageStudent.class);
                        startActivity(intent);
                    } else {
                        comm.setTeacher(true);
                        comm.getData();
                        comm.realtimeUpadateMyData();
                        Intent intent = new Intent(getApplicationContext(), HomePageTeacher.class);
                        startActivity(intent);
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    public void onClick_OpenStudentHomePage(View view) {
        comm.setTeacher(false);
        comm.getData();
        Intent intent = new Intent(this, HomePageStudent.class);
        startActivity(intent);
    }

    public void onClick_OpenTeacherHomePage(View view) {
        comm.setTeacher(true);
        comm.getData();
        Log.d("Page_ID","Try enter Chats");
        Intent intent = new Intent(this, HomePageTeacher.class);
        startActivity(intent);
    }

}