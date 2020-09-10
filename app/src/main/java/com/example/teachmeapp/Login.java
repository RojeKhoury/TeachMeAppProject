package com.example.teachmeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teachmeapp.Helpers.Globals;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class Login extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private Button m_loginButton;
    EditText m_expAuthenticator;
    EditText m_etEmail;
    EditText m_etPassword;
    TextView m_resetPassword;
    String popUpEmail = "";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //m_Auth = FirebaseAuth.getInstance();
        m_etEmail = findViewById(R.id.login_editText_enterEmail);
        m_etPassword = findViewById(R.id.login_editText_enterPassword);


        //Creating listener to Login button
        m_loginButton = (Button) findViewById(R.id.login_button_login);
        m_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });

        m_resetPassword = findViewById(R.id.Login_Reset_Password);
        m_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText temp = new EditText(Login.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle(Globals.EMAIL_ALERT_TITLE);
                builder.setMessage(Globals.EMAIL_ALERT_TEXT);
                builder.setView(temp);
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        popUpEmail = temp.getText().toString();
                        comm.sendResetPasswordEmail(popUpEmail);
                        Toast.makeText(Login.this, "an email is on its way to you", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI(comm.getFirebaseUser());
    }

    // here we will go to th next screen with the proper information required from the user received from firebase
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Toast.makeText(getApplicationContext(), currentUser.toString(),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, test_profile_page.class);
            startActivity(intent);
        }
    }


    private void openLogin() {
        //if(authenticator()==true)
        // {
       mAuth.signInWithEmailAndPassword(m_etEmail.getText().toString(), m_etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            updateUI(comm.getFirebaseUser());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
        // }
        // else
        // {
        //     invalidEmail();
        // }
    }

    private void invalidEmail() {
        Toast.makeText(Login.this, "Incorrect Email", Toast.LENGTH_LONG).show();
    }

    private boolean authenticator() {
        return validate(m_etEmail.getText().toString());
    }


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}

//test sign in =>
//email = adminroje@teachme.com
//password = ADMIN1