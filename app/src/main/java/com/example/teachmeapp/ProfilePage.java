package com.example.teachmeapp;

import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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

public class ProfilePage extends HamburgerMenu {

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
        setContentView(R.layout.activity_profile_page);
    }

    public void onToggleStar(View view) {
        final ImageButton ButtonStar = findViewById(R.id.profile_page_btn_favorite_star);
        if (ButtonStar.getTag()=="on"){
            ButtonStar.setTag("off");
            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.favorite_btn_star_off));
        }else{
            ButtonStar.setTag("on");
            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.favorite_btn_star_on));
        }
    }
    @Override
    protected void onStart() {

        super.onStart();

        setData();
    }
//picture update may not be working, hope to finish it tomorrow if not will skip until later (non essential)
    private void setData() {

        storage.getReference().child("images/"+ m_user.getUid() +"/pictures/profile picture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.get().load(uri).into(m_ImageVUpload);//place the view you want to insert the profile pic to
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {//I collect the data from the database
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    email = documentSnapshot.getString("email");
                    name = documentSnapshot.getString("name");
                    surname = documentSnapshot.getString("surname");
                    phone = documentSnapshot.getString("phone");
                    picRef = storage.getReferenceFromUrl(documentSnapshot.getString("pic"));
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

    }
}