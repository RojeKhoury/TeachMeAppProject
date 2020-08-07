package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button m_loginButton,m_signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating listener to Login button
        m_loginButton= (Button) findViewById(R.id.main_button_login);
        m_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenLogin();
            }
        });

    }

    public void OpenLogin()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }
}