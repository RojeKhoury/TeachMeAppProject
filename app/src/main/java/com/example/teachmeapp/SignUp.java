package com.example.teachmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView m_emailBox;
    private TextView m_passwordBox;
    private TextView m_passwordCopyBox;
    public Button m_signUpNextButton;
    private static final String TAG = "Sign up page1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        m_emailBox = (TextView) findViewById(R.id.signUp_editText_enterName);
        m_passwordBox = (TextView) findViewById(R.id.signUp_editText_enterSurname);
        m_passwordCopyBox = (TextView) findViewById(R.id.signUp_editText_enterPhone);

        m_signUpNextButton = (Button) findViewById(R.id.signUp_button_generalInfo);
        m_signUpNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (authenticator()) {
                    if (m_passwordBox.getText().toString().equals(m_passwordCopyBox.getText().toString())) {
                    } else {
                        Snackbar.make(findViewById(R.id.SignUp), R.string.passwords_dont_match,
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
    }

    private boolean authenticator() {
        return validate(m_emailBox.getText().toString());
    }


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    // here we will go to th next screen with the proper information required from the user received from firebase
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            openNextSignUp();
        }
    }

    //global page
    public void openNextSignUp() {
        Intent intent = new Intent(this, SignUpGetGeneralInfo.class);
        startActivity(intent);
    }

}

