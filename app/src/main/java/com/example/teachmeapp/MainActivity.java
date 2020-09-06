package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import static com.example.teachmeapp.Helpers.Globals.comm;

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
        m_signUpButton= (Button) findViewById(R.id.main_button_signUp);
        m_signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSignUp();
            }
        });
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

//uncomment in final version
  /*  @Override
    protected void onStart()
    {
        super.onStart();
        updateUI(comm.getFirebaseUser());
    }
    // here we will go to th next screen with the proper information required from the user received from firebase
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null)
        {
            Toast.makeText(getApplicationContext(), currentUser.toString(),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, test_profile_page.class);
            startActivity(intent);
        }
    }*/
}