package com.example.teachmeapp;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;

public class communicationWithDatabase {
    String TAG = "commincation with database";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser m_user;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference m_userDatabase;
    private String res;

    //here you can insert data to the data base itself (no the storage just the database) you will need to specify the data (a map containing the fields) the collection (teacher/student/admin/ any other you may want to add)
    //and finally the collection (i have it set to be the user id of the user for easier access)
    private void insertToDatabase(Map<String, Object> data, String collection, String document) {
        db.collection(collection).document(document)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void updateDatabase(Map<String, Object> data, String collection, String document) {
        db.collection(collection).document(document)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }


    //if you want to upload any image this is where you do it (in the storage reference you can add the location you want to place the image in the database)
    public void uploadImage(String imageLocation, StorageReference ref, final String owner) {

        Uri file = Uri.fromFile(new File(imageLocation));

        ref.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        insertImageUrlToFirestore(downloadUrl.toString(), owner);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });*/
    }

    public void insertImageUrlToFirestore(String toString, String owner) //owner is Teacher/Student
    {
        final Map<String, Object> user = new HashMap<>();
        user.put("pic", toString);

        m_user = mAuth.getCurrentUser();
        updateDatabase(user, owner, m_user.getUid());
    }

    //just a little thing to help you with the storage ref building.
    public StorageReference buildStorageRef(String folder, String userId, String subFolder, String fileName) {
        return storageRef.child(folder + "/" + userId + "/" + subFolder + "/" + fileName);
    }

    public String buildStorageRefString(String folder, String userId, String subFolder, String fileName) {
        return folder + "/" + userId + "/" + subFolder + "/" + fileName;
    }

    public String getDownloadUrl(String url) {
        return storage.getReferenceFromUrl(url).getDownloadUrl().toString();
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

   /* private String getData(String collection, String data) {
        m_user = mAuth.getCurrentUser();
        .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    return m_userDatabase.toString();
    }*/

    /*public String getName(String group) {
    return getData(group,"name");
    }

    public String getSurname(String group) {
        return getData(group,"surname");
    }

    public String getPhone(String group) {
        return getData(group,"phone");
    }

    public String getEmail(String group) {
        return getData(group,"email");
    }

    public String getPicLocation(String group) {
        return getData(group,"phone");
    }*/


    public void createTeacher(String name, String surname, String email, String imageLocation, String phoneNumber)
    {// will now also upload the image, all I need is the location on the device of the image.
        final Map<String, Object> user = new HashMap<>();
        float rating = 0;
        m_user = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        //profilPicRef = uploadImage();
        ArrayList<Map<String, Float>> courses = new ArrayList<Map<String, Float>>();
        user.put("name", name);
        user.put("surname", surname);
        user.put("phone", phoneNumber);
        user.put("email", email);
        user.put("star rating", rating);
        user.put("courses", courses);
        //storageRef.child("images/" + m_user.getUid() + "/profile pic/profile picture.jpg");
        //String url = storageRef.getDownloadUrl().toString();

        insertToDatabase(user, "Teachers", m_user.getUid());
        //uploadImage(imageLocation, buildStorageRef("images", m_user.getUid(), "profile picture", "profile pic"), "Teacher");
    }

    public void createStudent(String name, String surname, String email, String imageLocation, String phoneNumber)
    {// will now also upload the image, all I need is the location on the device of the image.
        final Map<String, Object> user = new HashMap<>();
        float rating = 0;
        m_user = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        //profilPicRef = uploadImage();
        user.put("name", name);
        user.put("surname", surname);
        user.put("phone", phoneNumber);
        user.put("email", email);
        user.put("star rating", rating);
        //storageRef.child("images/" + m_user.getUid() + "/profile pic/profile picture.jpg");
        //String url = storageRef.getDownloadUrl().toString();

        insertToDatabase(user, "Teachers", m_user.getUid());
        //uploadImage(imageLocation, buildStorageRef("images", m_user.getUid(), "profile picture", "profile pic"), "Teacher");
    }

}

