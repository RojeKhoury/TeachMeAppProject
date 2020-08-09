package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {


    public Button m_signUpNextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        m_signUpNextButton= (Button) findViewById(R.id.signUp_button_next);
        m_signUpNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextSignUp();
            }
        });
    }
    //global page
    public void openNextSignUp()
    {
        Intent intent = new Intent(this, SignUpGetGeneralInfo.class);
        startActivity(intent);
    }

}

