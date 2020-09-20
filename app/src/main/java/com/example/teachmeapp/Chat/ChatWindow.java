package com.example.teachmeapp.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.R;
import com.example.teachmeapp.model.BabySitter;
import com.example.teachmeapp.model.Message;
import com.example.teachmeapp.model.Parent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_STUDENT;

public class ChatWindow extends AppCompatActivity {
    public static final String TAG = "Test1";

    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    DatabaseReference reference1;
    String mTeacherUID, mStudentUID;
    boolean mIsTeacher,mFirstChat;
    Message mMessage;
    TextView m_TVTalkWith;
    private Parent parent;
    private BabySitter babySitter;

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

        if(mTeacherUID.compareTo(mStudentUID)>0)
        {
            reference1 = FirebaseDatabase.getInstance().getReference().child("messages").child(mTeacherUID + "_" + mStudentUID);
        }
        else
        {
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
                    HashMap<String,String> offerNotificationMap = new HashMap<>();
                    if (mIsTeacher) {
                        mMessage.setmUserUid(mTeacherUID);

                        offerNotificationMap.put("from", mTeacherUID);
                        offerNotificationMap.put("type","request");
                        NotificationsRef.child(mStudentUID).push()
                                .setValue(offerNotificationMap);
                    } else {
                        mMessage.setmUserUid(mStudentUID);

                        offerNotificationMap.put("from", mStudentUID);
                        offerNotificationMap.put("type","request");
                        NotificationsRef.child(mTeacherUID).push()
                                .setValue(offerNotificationMap);
                    }

                    try {
                        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.getChildrenCount() == 0) {
                                    DatabaseReference TalkedWithRef = FirebaseDatabase.getInstance().getReference(Globals.TALKEDWITH).child(mTeacherUID);
                                    TalkedWithRef.push().setValue(mStudentUID);
                                    TalkedWithRef = FirebaseDatabase.getInstance().getReference(Globals.TALKEDWITH).child(mStudentUID);
                                    TalkedWithRef.push().setValue(mTeacherUID);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        reference1.push().setValue(mMessage);

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
                    addMessageBox(message2.getmMessage(), 1,message2.getmMessgeTime());
                } else {
                    addMessageBox(message2.getmMessage(), 2,message2.getmMessgeTime());
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
    private void setChatWithText()
    {
        if (mIsTeacher)
        {//mStudentUID
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final DocumentReference docRef = db.collection(COLLECTION_STUDENT).document(mStudentUID);
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                    m_TVTalkWith.setText(snapshot.get(Globals.FIELD_NAME).toString()+" "+snapshot.get(Globals.FIELD_SURNAME).toString());
                }
            });
        }
        else
        {//mTeacherUID
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final DocumentReference docRef = db.collection(COLLECTION_STUDENT).document(mTeacherUID);
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                    m_TVTalkWith.setText(snapshot.get(Globals.FIELD_NAME).toString()+" "+snapshot.get(Globals.FIELD_SURNAME).toString());
                }
            });
        }
    }

    public void addMessageBox(String message, int type,long time) {
        Log.e(TAG, "addMessageBox <<");
        LinearLayout linearLayout = new LinearLayout(ChatWindow.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(ChatWindow.this);
        TextView textViewTime = new TextView(ChatWindow.this);
        textView.setTextSize(20);
        textView.setText(message);
        textViewTime.setTextSize(10);
        textViewTime.setText(fromMillisToHoursMinutes(time));
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
    public long getCurrentTime()
    {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }
    public String fromMillisToHoursMinutes(long timeInMillis)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(timeInMillis);
    }
    public String getCurrentDate()
    {
        return fromMillisToHoursMinutes(getCurrentTime());
    }
}