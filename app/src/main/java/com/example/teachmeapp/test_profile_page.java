package com.example.teachmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teachmeapp.Helpers.Lesson;
import com.example.teachmeapp.Helpers.communicationWithDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class test_profile_page extends AppCompatActivity {

    public EditText m_email,  m_fName, m_lName, m_phone;
    private Button m_addLesson;
    private Button m_logOut;
    private ImageView m_ImageVUpload;
    private communicationWithDatabase m_comm = new communicationWithDatabase();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser m_user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docref;
    StorageReference picRef;

    String email;
    String name;
    String surname;
    String phone;
    String TAG = "I am a god";
    StorageReference gsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_profile_page);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        m_fName = (EditText) findViewById(R.id.test_profile_name);
        m_lName = (EditText) findViewById(R.id.test_profile_surname);
        m_phone = (EditText) findViewById(R.id.test_profile_phone);
        m_email = (EditText) findViewById(R.id.test_profile_email);
        m_ImageVUpload = (ImageView) findViewById(R.id.test_profile_pic);
        m_user = mAuth.getCurrentUser();
        docref = db.collection("Teachers").document(m_user.getUid());
        m_addLesson = findViewById(R.id.test_add_lesson_button);
        m_comm.setTeacher(true);
        m_logOut = findViewById(R.id.test_logout_button);
    }

    @Override
    protected void onStart() {

        super.onStart();

        m_addLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_comm.addCourse(new Lesson(m_fName.getText().toString(), new ArrayList<String>()), m_user.getUid(), new Float (155.5));
            }
        });

        m_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_comm.signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        setData();
    }

    private void setData() {
        storage.getReference().child("images/"+ m_user.getUid() +"/pictures/profile picture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.get().load(uri).into(m_ImageVUpload);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            email = documentSnapshot.getString("email");
                            name = documentSnapshot.getString("name");
                            surname = documentSnapshot.getString("surname");
                            phone = documentSnapshot.getString("phone");
                            picRef = storage.getReferenceFromUrl(documentSnapshot.getString("pic"));

                            m_email.setText(documentSnapshot.getString("email"));
                            m_fName.setText(documentSnapshot.getString("name"));
                            m_lName.setText(documentSnapshot.getString("surname"));
                            m_phone.setText(documentSnapshot.getString("phone"));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "does not exist", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


        m_user = mAuth.getCurrentUser();
       //gsReference = storage.getReferenceFromUrl(m_comm.getPicLocation("Teachers"));
    }
}