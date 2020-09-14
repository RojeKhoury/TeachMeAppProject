package com.example.teachmeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teachmeapp.Helpers.Lesson;
import com.example.teachmeapp.Helpers.communicationWithDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.HashMap;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_NAME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class test_profile_page extends AppCompatActivity {

    public EditText m_email, m_fName, m_lName, m_phone;
    private Button m_addLesson;
    private Button m_logOut;
    private ImageView m_ImageVUpload;
    private communicationWithDatabase m_comm = new communicationWithDatabase();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docref;
    StorageReference picRef;
    private ListView m_teachers;
    TextView temp;
    Button m_search_button;
    Button m_go_to_maps;

    String email;
    String name;
    String surname;
    String phone;
    String TAG = "I am a god";
    StorageReference gsReference;

    ArrayList<String> statesList = new ArrayList<String>();// add ur map here

    private float killme;

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
        docref = db.collection("Teachers").document(comm.getUid());
        m_addLesson = findViewById(R.id.test_add_lesson_button);
        comm.setM_teacher(true);
        m_logOut = findViewById(R.id.test_logout_button);
        m_teachers = findViewById(R.id.Test_Scroll_view);
        m_search_button = findViewById(R.id.test_search_button);
        temp = new TextView(this);
        m_go_to_maps = findViewById(R.id.button_go_to_maps);

    }


    @Override
    protected void onStart() {

        super.onStart();

        m_addLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       //         comm.addCourse(new Lesson(m_fName.getText().toString(), new ArrayList<String>()), comm.getUid(), new Float(149.5), "telaviv");
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
                storage.getReference().child("images/" + comm.getUid() + "/profile picture").
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
        m_go_to_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        comm.getData();
        killme = comm.getStarRating();
        Integer polio = 0;
    }

    private void setData() {
        storage.getReference().child("images/" + comm.getUid() + "/profile picture").
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

        comm.getDocRef(comm.getUid(), COLLECTION_TEACHER).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    email = documentSnapshot.getString("email");
                    name = documentSnapshot.getString("name");
                    surname = documentSnapshot.getString("surname");
                    phone = documentSnapshot.getString("phone");

                    m_email.setText(documentSnapshot.getString("email"));
                    m_fName.setText(documentSnapshot.getString("name"));
                    m_lName.setText(documentSnapshot.getString("surname"));
                    m_phone.setText(documentSnapshot.getString("phone"));

                } else {
                    Toast.makeText(getApplicationContext(), "does not exist", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        storage.getReference().child("images/" + comm.getUid() + "/profile picture").
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

        //gsReference = storage.getReferenceFromUrl(m_comm.getPicLocation("Teachers"));
    }

    public void searchForTeachers() {
        //here I am assuming that the data was collected so these are temporary values that need to be changed when the page is done
        String subject = "math";
        float maxPrice = 150;
        Boolean zoom = false;
        //.whereEqualTo(FIELD_ZOOM, false)
        CollectionReference teacherRef = comm.db.collection(COLLECTION_TEACHER);
        teacherRef.whereEqualTo(FIELD_ZOOM, true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        temp.append(document.getData().toString());
                        Log.d("Tag", document.getData().toString());
                        ArrayList<HashMap<String, Lesson>> maps = new ArrayList<>();
                        maps = (ArrayList<HashMap<String, Lesson>>) document.get(FIELD_LESSONS);

                        for (Object map : maps.toArray())
                        {
                            if (((HashMap) map).containsKey("math"))
                            {
                                String lesson = ((HashMap)((HashMap) map).get("math")).get("name").toString();
                                String price = ((HashMap)((HashMap) map).get("math")).get("price").toString();
                                statesList.add(document.getData().get(FIELD_NAME).toString() + "\n" + "price = " + price);
                                //statesList.add("price = " + price);

                                break;
                            }
                        }
                        /*{for (HashMap map : (ArrayList<HashMap>) document.getData().get(FIELD_LESSONS))
                        {
                            if(map.containsKey("math"))
                            {

                            }
                        }}
                        /*if (document.getData().get(FIELD_LESSONS + "." + "math") != null) {
                            statesList.add(document.getData().toString());
                        }*/
/*
                        D/Tag: {phone=0558870817, studentHome=false, teacherHome=false, surname=ant, rating=[], name=iw, bio=this my bio, zoom=true, email=todie@gmail.com, lessons={}}
                        D/Tag: {studentHome=false, phone=0558870817, surname=dont, teacherHome=false, name=men, rating=[], bio=this my bio, zoom=true, email=cry@gmail.com, lessons={}}
                        D/Tag: {phone=0558870817, studentHome=false, surname=isnt, teacherHome=false, rating=[], name=why, bio=this my bio, zoom=true, email=itworking123@gmail.com, lessons=[{why={price=149.5, name=why}}]}
                        D/Tag: {phone=0558870817, studentHome=false, surname=helpme, teacherHome=false, rating=[], name=helope, bio=this my bio, zoom=true, email=glikoperty@gmail.com, lessons=[{helope={price=149.5, name=helope}}, {math={price=149.5, name=math}}]}
                        D/Tag: {phone=0558870817, studentHome=false, teacherHome=false, surname= prock, rating=[], name=PRICK, bio=this my bio, zoom=true, email=prickprock@gmail.com, lessons={}}
                        D/Tag: {phone=0558870817, studentHome=false, teacherHome=false, surname=astra, rating=[], name=opel, bio=this my bio, zoom=true, email=zibiboja3ni@gmail.com, lessons={}}
                        D/Tag: {phone=0558870817, studentHome=false, teacherHome=false, surname=do, rating=[], name=lets, bio=this my bio, zoom=true, email=thisshit@gmail.com, lessons={}}
                        D/Tag: {studentHome=false, phone=0547645977, teacherHome=false, surname=khoury, rating=[], name=roje, bio=this my bio, zoom=true, email=tester@gmail.com, lessons={}}
 */
                    }
                    insertToList();
                }
            }
        });


    }

    private void insertToList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, statesList);
        m_teachers.setAdapter(adapter);
        /*  if u want a button
        m_teachers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) m_teachers.getItemAtPosition(position);

            }
        });
         */
    }
}