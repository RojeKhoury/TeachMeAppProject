package com.example.teachmeapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.teachmeapp.Helpers.BookedLesson;
import com.example.teachmeapp.Helpers.Globals;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class TeacherPendingRequestDetailsPage extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewSubject, textViewPrice, textViewLevel, textViewTimeStart, textViewTimeEnd, textViewTimeAddress;

    private Button accept, reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_pending_request_details_page);


        imageView = findViewById(R.id.TeacherPendingRequestDetailsPage_ImageView_Pic);
        textViewSubject = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_Subject);
        textViewPrice = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_Price);
        textViewLevel = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_Level);

        textViewTimeStart = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_TimeStart_Value);
        textViewTimeEnd = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_TimeEnd_Value);
        textViewTimeAddress = findViewById(R.id.TeacherPendingRequestDetailsPage_TextView_Address);

        accept = findViewById(R.id.TeacherPendingRequestDetailsPage_Button_Accept_Lesson);
        reject = findViewById(R.id.TeacherPendingRequestDetailsPage_Button_Reject_Lesson);

        if (Globals.currentRequest.getAccepting()){
            accept.setVisibility(View.INVISIBLE);
            reject.setVisibility(View.INVISIBLE);
        }

        setUpDetails();
    }

    private void setUpDetails(){

        loadImage(Globals.currentRequest.getM_studentUID());

        textViewSubject.setText(Globals.currentRequest.getM_subject());
        textViewLevel.setText(Globals.currentRequest.getM_level());
        textViewPrice.setText(Globals.currentRequest.getM_price());
        textViewTimeStart.setText(Globals.currentRequest.getM_timeStart());
        textViewTimeEnd.setText(Globals.currentRequest.getM_timeEnd());

        if (Globals.currentRequest.getM_zoom()){
            textViewTimeAddress.setText("Zoom Meeting");
        } else {
            textViewTimeAddress.setText("Face-to-Face Meeting");
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

    public void BackButton(View view) {
        super.onBackPressed();
    }

    public void RejectClass(View view) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference requestIdRef = db.collection("Request").document(Globals.currentRequest.getId());

        HashMap<String, Object> updateData = new HashMap<>();
        updateData.put("rejecting", true);
        updateData.put("pending", false);

        requestIdRef
                .update(updateData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TeacherPendingRequestDetailsPage.this, "This lesson is rejected successfully!",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FailureUpdating", "Error writing document", e);
                    }
                });

        super.onBackPressed();
    }

   public void AcceptClass(View view) {

       FirebaseFirestore db = FirebaseFirestore.getInstance();

       DocumentReference requestIdRef = db.collection("Request").document(Globals.currentRequest.getId());

       HashMap<String, Object> updateData = new HashMap<>();
       updateData.put("accepting", true);
       updateData.put("pending", false);

       requestIdRef
               .update(updateData)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Toast.makeText(TeacherPendingRequestDetailsPage.this, "This lesson is accepted successfully!",
                               Toast.LENGTH_LONG).show();
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Log.e("FailureUpdating", "Error writing document", e);
                   }
               });

        super.onBackPressed();
    }
}