package com.example.teachmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpGetGeneralInfo extends AppCompatActivity {

    String TAG = "sign up general info";
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser m_user;
    private Button m_signUpButton;
    private TextView m_nameBox;
    private TextView m_surnameBox;
    private TextView m_phoneBox;
    private ImageView m_profilePic;
    private communicationWithDatabase comm = new communicationWithDatabase();

    StorageReference profilPicRef;

    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_get_general_info);
        final Map<String, Object> user = new HashMap<>();
        mAuth = FirebaseAuth.getInstance();
        m_user = mAuth.getCurrentUser();
        m_profilePic = findViewById(R.id.generalInfo_profile_pic);
        m_signUpButton = findViewById(R.id.signUp_button_generalInfo);
        m_nameBox = findViewById(R.id.signUp_editText_enterName);
        m_surnameBox = findViewById(R.id.signUp_editText_enterSurname);
        m_phoneBox = findViewById(R.id.signUp_editText_enterPhone);
        db = FirebaseFirestore.getInstance();

        m_profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }

            private void SelectImage()
            {
                // Defining Implicit Intent to mobile gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(
                                intent,
                                "Select Image from here..."),
                        PICK_IMAGE_REQUEST);
            }
        });

        m_signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushData(); //push data to the database
                openNextPage(); //open the next screen (view)
            }

            private void pushData() {

                profilPicRef = uploadImage();
                user.put("name", m_nameBox.getText().toString());
                user.put("surname", m_surnameBox.getText().toString());
                user.put("phone", m_phoneBox.getText().toString());
                user.put("profile picture", profilPicRef.toString());

                comm.insertToDatabase(user,"Teachers", m_user.getUid());
            }

            private StorageReference uploadImage() {

                Bitmap bitmap = ((BitmapDrawable) m_profilePic.getDrawable()).getBitmap();
                StorageReference ref = comm.buildStorageRef("images", m_user.getUid().toString(), "profile Pic", m_profilePic.getContentDescription().toString());
                comm.uploadImage(m_user.getUid(), bitmap, ref);

                return ref;
            }
        });
    }
    public void openNextPage()
    {
        Intent intent = new Intent(this, profilePage.class);
        startActivity(intent);
    }
    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                m_profilePic.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}