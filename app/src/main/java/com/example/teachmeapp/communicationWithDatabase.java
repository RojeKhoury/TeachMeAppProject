package com.example.teachmeapp;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;

public class communicationWithDatabase {
    String TAG = "commincation with database";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //here you can insert data to the data base itself (no the storage just the database) you will need to specify the data (a map containing the fields) the collection (teacher/student/admin/ any other you may want to add)
    //and finally the collection (i have it set to be the user id of the user for easier access)
    public void insertToDatabase(Map<String, Object> data, String collection, String document) {
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

    //if you want to upload any image this is where you do it (in the storage reference you can add the location you want to place the image in the database)
    public void uploadImage(String uid, Bitmap bitmap, StorageReference reference) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
        });
    }

    //just a little thing to help you with the storage ref building.
    public StorageReference buildStorageRef(String folder, String userId, String subFolder, String fileName) {
        return storageRef.child(folder + "/" + userId + "/" + subFolder + "/" + fileName);
    }

    public String buildStorageRefString(String folder, String userId, String subFolder, String fileName) {
        return folder + "/" + userId + "/" + subFolder + "/" + fileName;
    }

    public String getDownloadUrl(String url)
    {
        return storage.getReferenceFromUrl(url).getDownloadUrl().toString();
    }

    public void createUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });

    }
    public String getName(String uid) {
        return "sss";
    }

}

