package com.example.teachmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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

public class HomePageTeacher extends AppCompatActivity {
    private ImageView m_profilePic;
    Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_teacher);
        m_profilePic = findViewById(R.id.ImageView_profilePic);
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
    }