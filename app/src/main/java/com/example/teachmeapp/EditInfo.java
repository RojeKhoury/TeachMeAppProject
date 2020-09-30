package com.example.teachmeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.teachmeapp.Helpers.Globals;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class EditInfo extends AppCompatActivity {

    private Button m_editName;
    private Button m_editLastName;
    private Button m_editBio;
    private Button m_editPhone;
    private Button m_editLocation;
    private Button m_editPicture;
    private LinearLayout editBio;
    String popUpFName = "";
    String popUpLName = "";
    String popUpBio = "";
    String popUpPhone = "";
    AwesomeValidation awesomeValidation;
    //StorageReference storageRef = comm.getStorage().getReference();
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
        m_editLocation = findViewById(R.id.edit_teacher_info_edit_location);

        editBio = findViewById(R.id.linear_editBio);

        //m_editLocation.setVisibility(View.GONE);

        if (!comm.isTeacher()){
            m_editBio.setVisibility(View.GONE);
            editBio.setVisibility(View.GONE);
        }

        m_editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Maps_activity_get_location.class);
                intent.putExtra("changeLocation", true);
                startActivity(intent);
//                Intent intent = new Intent(getApplicationContext(), TeacherLessonsAddOrRemove.class);
//                startActivity(intent);
            }
        });

        m_editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText fName = new EditText(EditInfo.this);
                final EditText lName = new EditText(EditInfo.this);
                awesomeValidation.addValidation(EditInfo.this, fName.getId(), "[a-zA-Z\\s]+", R.string.fnameerr);
                awesomeValidation.addValidation(EditInfo.this, lName.getId(), "[a-zA-Z\\s]+", R.string.fnameerr);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditInfo.this);
                builder.setTitle(Globals.NAME_ALERT_TITLE);
                builder.setMessage(Globals.NAME_ALERT_TEXT);
                builder.setView(fName);
                builder.setView(lName);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (awesomeValidation.validate() && lName.getText().toString().contains(" ")) {
                            popUpFName = lName.getText().toString().split(" ")[0];
                            comm.changeName(popUpFName);
                            popUpLName = lName.getText().toString().split(" ")[1];
                            comm.changeSurname(popUpLName);

                            //Toast.makeText(EditTeacherInfo.this, "fuck this shit im out", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(EditInfo.this, "Enter all fields. Your format must be {First_Name} {Sure_Name}"
                            , Toast.LENGTH_LONG).show();
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

                final EditText BioEdit = new EditText(EditInfo.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(EditInfo.this);
                builder.setTitle(Globals.BIO_ALERT_TITLE);
                builder.setMessage(Globals.BIO_ALERT_TEXT);
                builder.setView(BioEdit);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        popUpBio = BioEdit.getText().toString();
                        comm.changeBio(popUpBio);
                    }
                });
                builder.show();
            }
        });

        m_editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText PhoneEdit = new EditText(EditInfo.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(EditInfo.this);
                builder.setTitle(Globals.PHONE_ALERT_TITLE);
                builder.setMessage(Globals.PHONE_ALERT_TEXT);
                builder.setView(PhoneEdit);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        popUpPhone = PhoneEdit.getText().toString();
                        comm.changePhoneNumber(popUpPhone);
                        //Toast.makeText(EditTeacherInfo.this, "fuck this shit im out", Toast.LENGTH_LONG).show();
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