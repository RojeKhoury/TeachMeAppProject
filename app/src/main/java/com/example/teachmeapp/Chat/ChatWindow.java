package com.example.teachmeapp.Chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.teachmeapp.HamburgerMenu;
import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.Helpers.MySingleton;
import com.example.teachmeapp.ProfilePageOfTeacherForStudent;
import com.example.teachmeapp.R;
import com.example.teachmeapp.model.Message;
import com.example.teachmeapp.model.TalkedWithModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_STUDENT;
import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class ChatWindow extends HamburgerMenu {
    public static final String TAG = "Test1";

    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    DatabaseReference reference1;
    String mTeacherUID, mStudentUID;
    boolean mIsTeacher, mFirstChat;
    Message mMessage;
    TextView m_TVTalkWith;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAqu1rCgA:APA91bHnv46UjDflweYMPpNCQSPcwb8SyKBeUVQ4oPvvtKHtoIoPJIt-lRmh4rd6Aa-U8NSJ2QHhx7Fon-rOpUbxYDBtAchOnvEVwoD1V8DoN3Bpv7Civdb17YK4uky3YPCJGupE160-";
    final private String contentType = "application/json";
    final String TAG_NOTIFICATION = "NOTIFICATION TAG";

    private String NOTIFICATION_TITLE;
    private String NOTIFICATION_MESSAGE;
    private String TOPIC;

    private static boolean activityVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate Chat << ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        mFirstChat = true;
        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);
        mTeacherUID = getIntent().getStringExtra(Globals.TEACHERS);
        mStudentUID = getIntent().getStringExtra(Globals.STUDENTS);

        if (mTeacherUID.compareTo(mStudentUID) > 0) {
            reference1 = FirebaseDatabase.getInstance().getReference().child("messages").child(mTeacherUID + "_" + mStudentUID);
        } else {
            reference1 = FirebaseDatabase.getInstance().getReference().child("messages").child(mStudentUID + "_" + mTeacherUID);
        }

        mIsTeacher = getIntent().getBooleanExtra(Globals.IS_TEACHER, false);
        m_TVTalkWith = findViewById(R.id.chat_with);
        mMessage = new Message();
        setChatWithText();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "onSendButtonClick << ");
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    mMessage.setmMessage(messageText);
                    mMessage.setmMessgeTime(getCurrentTime());
                    DatabaseReference NotificationsRef = FirebaseDatabase.getInstance().getReference().child("Notifications/NewMessage");
                    HashMap<String, String> offerNotificationMap = new HashMap<>();
                    if (mIsTeacher) {
                        mMessage.setmUserUid(mTeacherUID);

                        offerNotificationMap.put("from", mTeacherUID);
                        offerNotificationMap.put("type", "request");
                        NotificationsRef.child(mStudentUID).push()
                                .setValue(offerNotificationMap);
                    } else {
                        mMessage.setmUserUid(mStudentUID);

                        offerNotificationMap.put("from", mStudentUID);
                        offerNotificationMap.put("type", "request");
                        NotificationsRef.child(mTeacherUID).push()
                                .setValue(offerNotificationMap);
                    }

                    try {

                        TalkedWithModel talkedWithModelStudent = new TalkedWithModel(mTeacherUID, getCurrentTime());
                        TalkedWithModel talkedWithModelTeacher = new TalkedWithModel(mStudentUID, getCurrentTime());

                        DatabaseReference TalkedWithRef;
                        TalkedWithRef = FirebaseDatabase.getInstance().getReference(Globals.TALKEDWITH).child(mTeacherUID);
                        TalkedWithRef.child(mStudentUID).setValue(talkedWithModelTeacher);

                        TalkedWithRef = FirebaseDatabase.getInstance().getReference(Globals.TALKEDWITH).child(mStudentUID);
                        TalkedWithRef.child(mTeacherUID).setValue(talkedWithModelStudent);

                        reference1.push().setValue(mMessage);

                        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Tokens");

                        collectionReference.document(comm.isTeacher() ? mStudentUID : mTeacherUID)
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        TOPIC = (String) document.get("token");

                                        NOTIFICATION_TITLE = "Chat Notification!";
                                        NOTIFICATION_MESSAGE = "You have a new message from " + comm.getUserName() + " " + comm.getUserSurname();

                                        JSONObject notification = new JSONObject();
                                        JSONObject notificationBody = new JSONObject();

                                        try {
                                            notificationBody.put("title", NOTIFICATION_TITLE);
                                            notificationBody.put("message", NOTIFICATION_MESSAGE);
                                            notificationBody.put("teacherUID", mTeacherUID);
                                            notificationBody.put("studentUID", mStudentUID);
                                            notificationBody.put("isTeacher", mIsTeacher);
                                            notificationBody.put("type", "chat");

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


                        //---Here i should update the LRU
                        //.child(id).removeValue();

                    } catch (Exception e) {
                        Log.e(TAG, "Push Exception = " + e.getMessage());
                    }
                    messageArea.setText("");
                }
                Log.e(TAG, "onSendButtonClick >> ");
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message2 = dataSnapshot.getValue(Message.class);

                String userName;
                if (mIsTeacher) {
                    userName = mTeacherUID;
                } else {
                    userName = mStudentUID;
                }
                if ((message2.getmUserUid().equals(userName))) {
                    addMessageBox(message2.getmMessage(), 1, message2.getmMessgeTime());
                } else {
                    addMessageBox(message2.getmMessage(), 2, message2.getmMessgeTime());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                        Toast.makeText(ChatWindow.this, "Request error", Toast.LENGTH_LONG).show();
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

    private void setChatWithText() {
        if (mIsTeacher) {//mStudentUID

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final DocumentReference docRef = db.collection(COLLECTION_STUDENT).document(mStudentUID);
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                    m_TVTalkWith.setText(snapshot.get(Globals.FIELD_NAME).toString() + " " + snapshot.get(Globals.FIELD_SURNAME).toString());
                }
            });

        } else {//mTeacherUID

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final DocumentReference docRef = db.collection(COLLECTION_TEACHER).document(mTeacherUID);
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                    m_TVTalkWith.setText(snapshot.get(Globals.FIELD_NAME).toString() + " " + snapshot.get(Globals.FIELD_SURNAME).toString());
                }
            });

        }

        if (!comm.isTeacher()){
            m_TVTalkWith.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ProfilePageOfTeacherForStudent.class);
                    comm.getViewedUserData(mTeacherUID,true,view.getContext(),intent);
                    //startActivity(intent);
                }
            });
        }
    }

    public void addMessageBox(String message, int type, long time) {
        Log.e(TAG, "addMessageBox <<");
        LinearLayout linearLayout = new LinearLayout(ChatWindow.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(ChatWindow.this);
        TextView textViewTime = new TextView(ChatWindow.this);
        textView.setTextSize(16);
        textView.setText(message);
        textViewTime.setTextSize(10);
        textViewTime.setText(fromMillisToHoursMinutes(time));
        textView.setPadding(10, 10, 10, 10);
        textViewTime.setPadding(5, 5, 5, 5);
        linearLayout.addView(textView);
        linearLayout.addView(textViewTime);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 7.0f;
        lp2.topMargin = 10;

        if (type == 2) {
            lp2.gravity = Gravity.LEFT;
            linearLayout.setBackgroundResource(R.drawable.bubble_in);
        } else {
            lp2.gravity = Gravity.RIGHT;
            linearLayout.setBackgroundResource(R.drawable.bubble_out);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }

            });
            scrollView.fullScroll(View.FOCUS_DOWN);
        }

        linearLayout.setLayoutParams(lp2);
        layout.addView(linearLayout);
        Log.e(TAG, "addMessageBox >>");
    }

    public long getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    public String fromMillisToHoursMinutes(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(timeInMillis);
    }

    public String getCurrentDate() {
        return fromMillisToHoursMinutes(getCurrentTime());
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
        ChatWindow.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ChatWindow.activityPaused();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChatWindow.activityPaused();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ChatWindow.activityPaused();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ChatWindow.activityPaused();
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }
}