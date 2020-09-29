package com.example.teachmeapp.service;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.teachmeapp.Chat.ChatWindow;
import com.example.teachmeapp.EditTeacherInfo;
import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.HomePageStudent;
import com.example.teachmeapp.HomePageTeacher;
import com.example.teachmeapp.MainActivity;
import com.example.teachmeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.teachmeapp.Helpers.Globals.comm;
import static com.example.teachmeapp.SignUp.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String ADMIN_CHANNEL_ID ="admin_channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (Globals.comm.getFirebaseUser() != null){

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser m_user = mAuth.getCurrentUser();

            DocumentReference ref = db.collection(Globals.COLLECTION_STUDENT).document(m_user.getUid());
            ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            comm.setTeacher(false);
                            comm.getData();
                            comm.realtimeUpadateMyData();

                        } else {

                            comm.setTeacher(true);
                            comm.getData();
                            comm.realtimeUpadateMyData();

                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });

        }

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationID = new Random().nextInt(3000);

      /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager);
        }

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notification);

        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder;

        String teacherUID, studentUID;
        boolean isTeacher;

        teacherUID = remoteMessage.getData().get("teacherUID");
        studentUID = remoteMessage.getData().get("studentUID");
        isTeacher = !Boolean.parseBoolean(remoteMessage.getData().get("isTeacher"));

        if (ChatWindow.isActivityVisible() && !Globals.currentTalkedWithUID.equals("") &&
        isTeacher ? Globals.currentTalkedWithUID.equals(studentUID) : Globals.currentTalkedWithUID.equals(teacherUID)){

            notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(largeIcon)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("message"))
                    .setAutoCancel(true)
                    .setSound(notificationSoundUri);

        } else {

            final Intent intent = new Intent(this, ChatWindow.class);

            intent.putExtra(Globals.IS_TEACHER, isTeacher);
            intent.putExtra(Globals.STUDENTS, studentUID);
            intent.putExtra(Globals.TEACHERS, teacherUID);

            if (isTeacher){
              Globals.currentTalkedWithUID = studentUID;
            } else {
                Globals.currentTalkedWithUID = teacherUID;
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this , 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(largeIcon)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("message"))
                    .setAutoCancel(true)
                    .setSound(notificationSoundUri)
                    .setContentIntent(pendingIntent);

        }

        //Set notification color to match your app color template
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        notificationManager.notify(notificationID, notificationBuilder.build());
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Map<String, String> data = new HashMap<>();
        data.put("token", s);

        if (Globals.comm.getFirebaseUser() != null){
            CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Tokens");

            collectionReference.document(Globals.comm.getFirebaseUser().getUid()).set(data);
        }

        Log.d("NEW_TOKEN",s);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager){
        CharSequence adminChannelName = "New notification";
        String adminChannelDescription = "Device to devie notification";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

}