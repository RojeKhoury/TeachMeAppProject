package com.example.teachmeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class MainActivity extends AppCompatActivity {

    //Creating listener to Login button
    private Button m_loginButton, m_signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_signUpButton = findViewById(R.id.main_button_signUp);
        m_signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSignUp();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (comm.getFirebaseUser() != null) {
            Intent intent = new Intent(this, LoginAsTeacherOrStudent.class);
            startActivity(intent);
        }
    }

    public void OpenSignUp() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void OnClickLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                        System.exit(0);
                    }
                }).create().show();
    }
}