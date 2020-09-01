package com.example.teachmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/*this page was made just so i can extract data from firebase and then display it*/

public class profilePage extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    communicationWithDatabase comm;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button m_logoutButton;
    private FirebaseAuth mAuth;
    private FirebaseUser m_user;
    private ImageView m_profilePicture;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference m_userDatabase;
    private TextView m_textBox;
    communicationWithDatabase m_comm = new communicationWithDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        mAuth = FirebaseAuth.getInstance();

        m_profilePicture = (ImageView) findViewById(R.id.profilePage_profilePicture_image);
        m_textBox = (TextView) findViewById(R.id.profile_page_text_box);
        m_user = mAuth.getCurrentUser();
        if (m_user != null)
        {
            m_userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(m_user.getUid());
        }

        m_logoutButton = (Button) findViewById(R.id.profilePage_logout_button);
        m_logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        //here we can get the data snapshot from the DB
        final DocumentReference docRef = db.collection("Teachers").document(m_user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name = (String) document.get("name");
                        m_textBox.append(name);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void loadPic(StorageReference ref) throws IOException {
        File localFile = File.createTempFile("images", "jpg");
        ref.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                        Glide.with(getApplicationContext()).load(taskSnapshot).into(m_profilePicture);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });

    }

    //to log the user out
    private void signOut() {
        comm.signOut();
        goToLoginPage();
    }
//when logging out we need to go back to the login page
    private void goToLoginPage() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}

