package com.example.teachmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class MainActivity extends AppCompatActivity {

    //Creating listener to Login button
    private Button m_loginButton = (Button) findViewById(R.id.main_button_login),
            m_signUpButton = (Button) findViewById(R.id.main_button_signUp);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenLogin();
            }
        });
        m_signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSignUp();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(comm.getFirebaseUser() != null)
        {
            Intent intent = new Intent(this, LoginAsTeacherOrStudent.class);
            startActivity(intent);
        }
    }

    public void OpenLogin()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void OpenSignUp()
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

}