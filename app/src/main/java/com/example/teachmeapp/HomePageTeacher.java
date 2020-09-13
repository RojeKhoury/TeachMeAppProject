package com.example.teachmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static com.example.teachmeapp.Helpers.Globals.FAILED_TO_UPLOAD_IMAGE;
import static com.example.teachmeapp.Helpers.Globals.comm;
import static com.example.teachmeapp.SignUp.PICK_IMAGE_REQUEST;

public class HomePageTeacher extends HamburgerMenu {
    private ImageView m_profilePic;
    Uri filePath;
    ImageButton m_addLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_teacher);
        m_profilePic = findViewById(R.id.ImageView_profilePic);
        m_addLesson = findViewById(R.id.homepage_teacher_button_requests);
    }

    @Override
    protected void onStart() {
        super.onStart();
        m_profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(view);
            }
        });

        m_addLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick_searchButton();
            }
        });
    }


    public void chooseImage(View view) {
        //Log.e(TAG, "OnClickUpload >>");

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        // set the data and type.  Get all image types.
        galleryIntent.setType("image/*");
        // we will invoke this activity, and get something back from it.
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // m_profilePic.setImageBitmap(bitmap);
                filePath = data.getData();
                uploadImageToFirebase(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebase(Uri filePath) {
        final StorageReference fileRef = comm.storage.getReference().child("images/" + comm.getUid() + "/profile picture");
        fileRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(m_profilePic);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomePageTeacher.this, FAILED_TO_UPLOAD_IMAGE, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                        System.exit(0);
                    }
                }).create().show();
    }

    public void onClick_searchButton()
    {
        Intent intent = new Intent(this, TeacherLessonsAddOrRemove.class);
        startActivity(intent);
    }
}