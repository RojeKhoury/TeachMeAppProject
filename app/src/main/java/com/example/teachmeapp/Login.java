package com.example.teachmeapp;
import java.util.regex.Pattern;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity
{

    private Button m_loginButton;
    EditText m_expAuthenticator;
    EditText m_etEmail;
    EditText m_etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //m_Auth = FirebaseAuth.getInstance();
        m_etEmail = findViewById(R.id.editTextTextEmailAddress);
        m_etPassword = findViewById(R.id.editTextTextPassword);


        //Creating listener to Login button
        m_loginButton= (Button) findViewById(R.id.SignInbutton);
        m_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });


    }


    private void openLogin()
    {
        if(authenticator()==true)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            invalidEmailOrPass();
        }
    }

    private void invalidEmailOrPass()
    {

        Toast.makeText(Login.this, "Incorrect Email or Password ",Toast.LENGTH_LONG).show();
    }

    private boolean authenticator()
    {
        return validate(m_etEmail.getText().toString());
    }


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}