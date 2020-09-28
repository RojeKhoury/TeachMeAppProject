package com.example.teachmeapp;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teachmeapp.Helpers.Globals;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class StudentPendingRequestDetailsPage extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewSubject, textViewLevel, textViewPrice, textViewStatus,
            textViewTimeStartValue, textViewTimeEndValue, textViewAddress;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pending_request_details_page);

        imageView = findViewById(R.id.StudentPendingRequestDetailsPage_ImageView);
        textViewSubject =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Subject);
        textViewLevel =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Level);
        textViewPrice =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Price);
        textViewStatus =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Status);
        textViewTimeStartValue =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_TimeStart_Value);
        textViewTimeEndValue =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_TimeEnd_Value);
        textViewAddress =findViewById(R.id.StudentPendingRequestDetailsPage_TextView_Address);

        cancel = findViewById(R.id.StudentPendingRequestDetailsPage_Button_Cancel);

        setUpDetails();

    }

    private void setUpDetails(){

        loadImage(Globals.currentRequest.getM_teacherUID());

        textViewSubject.setText(Globals.currentRequest.getM_subject());
        textViewLevel.setText(Globals.currentRequest.getM_level());
        textViewPrice.setText(Globals.currentRequest.getM_price());
        textViewTimeStartValue.setText(Globals.currentRequest.getM_timeStart());
        textViewTimeEndValue.setText(Globals.currentRequest.getM_timeEnd());

        if (Globals.currentRequest.getPending()){
            textViewStatus.setText("Pending");
            textViewStatus.setTextColor(Color.YELLOW);
        } else if (Globals.currentRequest.getRejecting()){
            textViewStatus.setText("Rejected");
            textViewStatus.setTextColor(Color.RED);
        } else {
            textViewStatus.setText("Accepted");
            textViewStatus.setTextColor(Color.GREEN);
            cancel.setVisibility(View.INVISIBLE);
        }

        if (Globals.currentRequest.getM_zoom()){
            textViewAddress.setText("Zoom Meeting");
        } else {
            textViewAddress.setText("Face-to-Face Meeting");
        }
    }

    private void loadImage(String uid) {

        comm.profileImagePicRef(uid).
                getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.get().load(uri.toString()).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), Globals.ERROR_LOADING_PICTURE, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void OnClick_StudentPending_RequestDetails_PageCancelButton(View view) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference requestIdRef = db.collection("Request").document(Globals.currentRequest.getId());

        requestIdRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(StudentPendingRequestDetailsPage.this,
                        "Request is deleted successfully.", Toast.LENGTH_LONG).show();

                finish();
            }
        });


    }

    public void OnClick_StudentPending_RequestDetails_PageBackButton(View view) {
        super.onBackPressed();
    }
}