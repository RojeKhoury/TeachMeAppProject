package com.example.teachmeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.Helpers.communicationWithDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class SignUp extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    public static final String TAG = "Test";
    private FirebaseUser m_user;
    public static final int PICK_IMAGE_REQUEST = 22;
    private EditText m_email, m_password, m_reEnterPass, m_fName, m_lName, m_phone;
    private StorageReference profilPicRef;
    private FirebaseAuth mAuth;
    ImageView mImageVUpload;
    Button bUploadImage, bNext;
    Uri filePath;
    final Map<String, Object> user = new HashMap<>();
    private communicationWithDatabase m_communicationWithDatabase = new communicationWithDatabase();
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (comm.getFirebaseUser() != null) {
            comm.signOut();
        }
        updateUI();
    }

    private void updateUI() {
        bNext = findViewById(R.id.signUp_button_generalInfo);
        m_fName = findViewById(R.id.signUp_editText_enterName);
        m_lName = findViewById(R.id.signUp_editText_enterSurname);
        m_phone = findViewById(R.id.signUp_editText_enterPhone);
        m_reEnterPass = findViewById(R.id.signUp_editText_enterPasswordAgain);
        m_email = findViewById(R.id.signUp_editText_enterEmail);
        m_password = findViewById(R.id.signUp_editText_enterPassword);
        mAuth = FirebaseAuth.getInstance();
        profilPicRef = FirebaseStorage.getInstance().getReference(Globals.PROFILE_PIC_STORAGE_PATH);
        DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference(Globals.PROFILE_PIC_STORAGE_PATH);
        mImageVUpload = (ImageView) findViewById(R.id.imageViewUpload);
        bUploadImage = (Button) findViewById(R.id.buttonUpload);
        bNext = (Button) findViewById(R.id.buttonNext);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        String regexPassword = "^[A-Za-z\\d].{5,}$";
        String regexPhone = "^(?=.*\\d).{10,10}$";
        String regexAddress = "^[A-Za-z\\d].{3,}$";

        awesomeValidation.addValidation(SignUp.this, R.id.signUp_editText_enterName, "[a-zA-Z\\s]+", R.string.fnameerr);
        awesomeValidation.addValidation(SignUp.this, R.id.signUp_editText_enterSurname, "[a-zA-Z\\s]+", R.string.lnameerr);
        awesomeValidation.addValidation(SignUp.this, R.id.signUp_editText_enterEmail, android.util.Patterns.EMAIL_ADDRESS, R.string.emailerr);
        awesomeValidation.addValidation(SignUp.this, R.id.signUp_editText_enterPhone, regexPhone, R.string.phoneerr);
        awesomeValidation.addValidation(SignUp.this, R.id.signUp_editText_enterPassword, regexPassword, R.string.passerr);
    }

    public void onClickNext(View view) {
        if (awesomeValidation.validate()) {
            if (!m_password.getText().toString().equals(m_reEnterPass.getText().toString())) {
                Toast.makeText(SignUp.this, "Password does not match", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Password does not match");
            } else {

                Toast.makeText(SignUp.this, "Data Received Successfully", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "checkInfo success");
                Task<AuthResult> authResult;
                authResult = mAuth.createUserWithEmailAndPassword(m_email.getText().toString(), m_password.getText().toString());
                authResult.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "WEEEEeeeee.", Toast.LENGTH_LONG).show();
                            pushData(); //push data to the database
                            //m_communicationWithDatabase.signOut();
                            Intent intent = new Intent(getApplicationContext(), test_profile_page.class);
                            startActivity(intent);
                        } else {
                            //failed to add to firebase.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }


    private void pushData() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        m_communicationWithDatabase.createTeacher(m_fName.getText().toString(), m_lName.getText().toString(), m_email.getText().toString(), mImageVUpload.toString(), m_phone.getText().toString());
        m_communicationWithDatabase.createStudent(m_fName.getText().toString(), m_lName.getText().toString(), m_email.getText().toString(), mImageVUpload.toString(), m_phone.getText().toString());

        StorageReference pPic = storageRef.child("images/" + m_communicationWithDatabase.getUid() + "/profile picture");
        pPic.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
        //this uploads the picture

        /*mImageVUpload.setDrawingCacheEnabled(true);
        mImageVUpload.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) mImageVUpload.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        m_user = mAuth.getCurrentUser();



        UploadTask uploadTask = pPic.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                //m_communicationWithDatabase.insertImageUrlToFirestore( taskSnapshot.toString(), "Teachers");

            }
        });*/

        //m_communicationWithDatabase.insertImageUrlToFirestore(storage.getReferenceFromUrl(pPic.toString()).toString(), "Teachers");
    }


    public void OnClickUpload(View view) {
        Log.e(TAG, "OnClickUpload >>");

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
                mImageVUpload.setImageBitmap(bitmap);
                filePath = data.getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String getUid() {
        return mAuth.getCurrentUser().getUid();
    }

}
