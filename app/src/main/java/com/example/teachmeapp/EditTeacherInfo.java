package com.example.teachmeapp;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.teachmeapp.Helpers.Globals;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class EditTeacherInfo extends AppCompatActivity {

    private Button m_editName;
    private Button m_editLastName;
    private Button m_editBio;
    private Button m_editPhone;
    private Button m_addLessons;
    private Button m_editPicture;
    String popUpFName = "";
    String popUpLName = "";
    String popUpBio = "";
    String popUpPhone = "";
    AwesomeValidation awesomeValidation;
    StorageReference storageRef = comm.getStorage().getReference();
    public static final int PICK_IMAGE_REQUEST = 22;
    Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teacher_info);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        m_editName = findViewById(R.id.edit_teacher_info_button_name);
        m_editBio = findViewById(R.id.edit_teacher_info_button_Bio);
        m_editPicture = findViewById(R.id.edit_teacher_info_button_picture);
        m_editPhone = findViewById(R.id.edit_teacher_info_button_Phone);
        m_addLessons = findViewById(R.id.edit_teacher_info_button_Lessons);

        m_addLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TeacherLessonsAddOrRemove.class);
                startActivity(intent);
            }
        });

        m_editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText fName = new EditText(EditTeacherInfo.this);
                final EditText lName = new EditText(EditTeacherInfo.this);
                awesomeValidation.addValidation(EditTeacherInfo.this, fName.getId(), "[a-zA-Z\\s]+", R.string.fnameerr);
                awesomeValidation.addValidation(EditTeacherInfo.this, lName.getId(), "[a-zA-Z\\s]+", R.string.fnameerr);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditTeacherInfo.this);
                builder.setTitle(Globals.NAME_ALERT_TITLE);
                builder.setMessage(Globals.NAME_ALERT_TEXT);
                builder.setView(fName);
                builder.setView(lName);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (awesomeValidation.validate()) {
                            popUpFName = fName.getText().toString();
                            comm.changeName(popUpFName);
                            popUpLName = lName.getText().toString();
                            comm.changeSurname(popUpLName);
                            Toast.makeText(EditTeacherInfo.this, "fuck this shit im out", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.show();
            }
        });
        //--------------------------<EDIT BIO>
        m_editBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText BioEdit = new EditText(EditTeacherInfo.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(EditTeacherInfo.this);
                builder.setTitle(Globals.BIO_ALERT_TITLE);
                builder.setMessage(Globals.BIO_ALERT_TEXT);
                builder.setView(BioEdit);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        popUpBio = BioEdit.getText().toString();
                        comm.changeBio(popUpBio);
                        Toast.makeText(EditTeacherInfo.this, "fuck this shit im out", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });

        m_editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText PhoneEdit = new EditText(EditTeacherInfo.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(EditTeacherInfo.this);
                builder.setTitle(Globals.PHONE_ALERT_TITLE);
                builder.setMessage(Globals.PHONE_ALERT_TEXT);
                builder.setView(PhoneEdit);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        popUpPhone = PhoneEdit.getText().toString();
                        comm.changePhoneNumber(popUpBio);
                        Toast.makeText(EditTeacherInfo.this, "fuck this shit im out", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });

        m_editPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadPic(view);
            }
        });
    }

    public void UploadPic(View view) {
        Log.e("this is a tag", "OnClickUpload >>");

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
            comm.uploadImage(data.getData());
           /* StorageReference pPic = storageRef.child("images/" + comm.getUid() + "/profile picture");
            pPic.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(EditTeacherInfo.this, "Profile picture updated", Toast.LENGTH_SHORT).show();
                }

            });
        }*/
        }
    }
}