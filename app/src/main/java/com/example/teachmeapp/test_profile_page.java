package com.example.teachmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachmeapp.Helpers.Lesson;
import com.example.teachmeapp.Helpers.communicationWithDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.example.teachmeapp.Helpers.Globals.comm;

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
    private RecyclerView m_teachers;
    TextView temp;
    Button m_search_button;

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
        comm.setTeacher(true);
        m_logOut = findViewById(R.id.test_logout_button);
        m_teachers = findViewById(R.id.Test_Scroll_view);
        m_search_button = findViewById(R.id.test_search_button);
        TextView temp = new TextView(this);

    }

    @Override
    protected void onStart() {

        super.onStart();

        m_addLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comm.addCourse(new Lesson(m_fName.getText().toString(), new ArrayList<String>()), m_user.getUid(), new Float (149.5));
            }
        });

        m_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comm.signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        setData();
        m_ImageVUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.getReference().child("images/"+ comm.getUid() +"/profile picture").
                        getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
            }
        });

        m_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchForTeachers();
            }
        });
    }

    private void setData() {
        storage.getReference().child("images/"+ comm.getUid() +"/profile picture").
                getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        storage.getReference().child("images/"+ comm.getUid() +"/profile picture").
                getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        m_user = mAuth.getCurrentUser();
       //gsReference = storage.getReferenceFromUrl(m_comm.getPicLocation("Teachers"));
    }

    public void searchForTeachers()
    {
        //here I am assuming that the data was collected so these are temporary values that need to be changed when the page is done
        String subject = "math";
        float maxPrice = 150;
        Boolean zoom = false;
//.whereEqualTo(FIELD_ZOOM, false)
        CollectionReference teacherRef = comm.db.collection(COLLECTION_TEACHER);
        teacherRef.whereArrayContains(FIELD_LESSONS, subject).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        temp.setText(document.getData().toString());
                        m_teachers.addView(temp);
                    }
                }
            }
        });
    }
}