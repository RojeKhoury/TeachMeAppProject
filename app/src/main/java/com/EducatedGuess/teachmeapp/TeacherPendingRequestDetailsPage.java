package com.EducatedGuess.teachmeapp;

import android.annotation.SuppressLint;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.EducatedGuess.teachmeapp.Helpers.Globals;
import com.EducatedGuess.teachmeapp.Helpers.MySingleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.EducatedGuess.teachmeapp.Helpers.Globals.comm;

public class TeacherPendingRequestDetailsPage extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewSubject, textViewPrice, textViewLevel, textViewTimeStart, textViewTimeEnd, textViewTimeAddress;

    private Button accept, reject;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAqu1rCgA:APA91bHnv46UjDflweYMPpNCQSPcwb8SyKBeUVQ4oPvvtKHtoIoPJIt-lRmh4rd6Aa-U8NSJ2QHhx7Fon-rOpUbxYDBtAchOnvEVwoD1V8DoN3Bpv7Civdb17YK4uky3YPCJGupE160-";
    final private String contentType = "application/json";
    final String TAG_NOTIFICATION = "NOTIFICATION TAG";

    private String NOTIFICATION_TITLE;
    private String NOTIFICATION_MESSAGE;
    private String TOPIC;

    private static boolean activityVisible;

    public static final String TAG = "Notification_Request";

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

                        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Tokens");

                        collectionReference.document(Globals.currentRequest.getM_studentUID())
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        TOPIC = (String) document.get("token");

                                        NOTIFICATION_TITLE = "Request Notification!";
                                        NOTIFICATION_MESSAGE = "Your request for " + Globals.currentRequest.getM_subject()
                                                +" have been rejected by " + Globals.currentRequest.getM_teacherName();

                                        JSONObject notification = new JSONObject();
                                        JSONObject notificationBody = new JSONObject();

                                        try {
                                            notificationBody.put("title", NOTIFICATION_TITLE);
                                            notificationBody.put("message", NOTIFICATION_MESSAGE);
                                            notificationBody.put("studentUID", Globals.currentRequest.getM_studentUID());
                                            notificationBody.put("type", "reject_request");

                                            notification.put("to", TOPIC);
                                            notification.put("data", notificationBody);

                                        } catch (JSONException e) {
                                            Log.e(TAG, "onCreate: " + e.getMessage() );
                                        }
                                        sendNotification(notification);

                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    } else {

                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

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

                       CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Tokens");

                       collectionReference.document(Globals.currentRequest.getM_studentUID())
                               .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                               if (task.isSuccessful()) {
                                   DocumentSnapshot document = task.getResult();
                                   if (document.exists()) {

                                       TOPIC = (String) document.get("token");

                                       NOTIFICATION_TITLE = "Request Notification!";
                                       NOTIFICATION_MESSAGE = "Your request for " + Globals.currentRequest.getM_subject()
                                               +" have been accepted by " + Globals.currentRequest.getM_teacherName();

                                       JSONObject notification = new JSONObject();
                                       JSONObject notificationBody = new JSONObject();

                                       try {
                                           notificationBody.put("title", NOTIFICATION_TITLE);
                                           notificationBody.put("message", NOTIFICATION_MESSAGE);
                                           notificationBody.put("studentUID", Globals.currentRequest.getM_studentUID());
                                           notificationBody.put("type", "accept_request");

                                           notification.put("to", TOPIC);
                                           notification.put("data", notificationBody);

                                       } catch (JSONException e) {
                                           Log.e(TAG, "onCreate: " + e.getMessage() );
                                       }
                                       sendNotification(notification);

                                       Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                   } else {

                                       Log.d(TAG, "No such document");
                                   }
                               } else {
                                   Log.d(TAG, "get failed with ", task.getException());
                               }
                           }
                       });

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

    @SuppressLint("RestrictedApi")
    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TeacherPendingRequestDetailsPage.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        TeacherPendingRequestDetailsPage.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TeacherPendingRequestDetailsPage.activityPaused();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TeacherPendingRequestDetailsPage.activityPaused();
    }

    @Override
    protected void onStop() {
        super.onStop();
        TeacherPendingRequestDetailsPage.activityPaused();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TeacherPendingRequestDetailsPage.activityPaused();
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }
}