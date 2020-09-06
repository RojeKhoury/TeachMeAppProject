package com.example.teachmeapp.Helpers;

import android.net.Uri;
import android.util.Log;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.teachmeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;

public class communicationWithDatabase {
    String TAG = "commincation with database";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser m_user = mAuth.getCurrentUser();
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference m_userDatabase;
    private String res;
    private boolean teacher = false;

    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }


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
    public DocumentReference getTeacherStorageRef() {
        return db.collection(Globals.COLLECTION_TEACHER).document(getUid());
    }

    public DocumentReference getStudentStorageRef() {
        return db.collection(Globals.COLLECTION_STUDENT).document(getUid());
    }

    private void updateElementInDatabase(DocumentReference docRef, String element, String newValue)
    {
        docRef.update(element, newValue)
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
    public void createTeacher(String name, String surname, String email, String imageLocation, String phoneNumber) {// will now also upload the image, all I need is the location on the device of the image.
        final Map<String, Object> user = new HashMap<>();
        float rating = 0;
        m_user = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Teacher teacher = new Teacher(name, surname, phoneNumber, new ArrayList<Integer>(), new ArrayList<UserLesson>(), new ArrayList<Comment>(), email);
        insertTeacherToDatabase(teacher, "Teachers", m_user.getUid());

    }

    private void insertTeacherToDatabase(Teacher data, String collection, String document) {
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


    public void createStudent(String name, String surname, String email, String imageLocation, String phoneNumber) {// will now also upload the image, all I need is the location on the device of the image.

        final Map<String, Object> user = new HashMap<>();
        float rating = 0;
        m_user = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Student student = new Student(name, surname, phoneNumber, new ArrayList<Lesson>(), email);
        insertStudentToDatabase(student, "Students", m_user.getUid());
    }

    private void insertStudentToDatabase(Student data, String collection, String document) {
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


    public void addCourse(Lesson lesson, String Uid, Float price) {
        //addLessonToDatabase(lesson);
        if (teacher) {
            Map temp = new HashMap<String, UserLesson>();
            temp.put(lesson.getName(), new UserLesson(lesson.getName(), new ArrayList<Integer>(), new ArrayList<Comment>(), price));
            addLessonToTeacher(temp, lesson.getName());
        } else {
            addLessonToStudent(lesson);
        }
    }


    private void addLessonToStudent(Lesson lesson) {
        db.collection("Students").document(m_user.getUid())
                .update("classes", lesson)
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

    private void addLessonToDatabase(Lesson lesson) {
        db.collection("lessons").document(lesson.getName())
                .set(lesson)
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


    private void addLessonToTeacher(Map<String, UserLesson> lesson, String name) {
        String collection;
        collection = "Teachers";
        m_user = mAuth.getCurrentUser();
        db.collection(collection).document(m_user.getUid())
                .update("lessons", FieldValue.arrayUnion(lesson))
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

       /* db.collection("lessons").document(name)
                .update("teachers", FieldValue.arrayUnion(m_user.getUid()))
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
                });*/

    }

    public String getUid() {
        return m_user.getUid();
    }

    public void sendResetPasswordEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

    public void changeTeacherName(String name) {
        updateElementInDatabase(getTeacherStorageRef(),Globals.FIELD_NAME, name);
    }

    public void changeTeacherSurname(String surname) {
        updateElementInDatabase(getTeacherStorageRef(),Globals.FIELD_SURNAME, surname);
    }

    public void changeStudentName(String name) {
        updateElementInDatabase(getStudentStorageRef(),Globals.FIELD_NAME, name);
    }

    public void changeStudentSurname(String surname) {
        updateElementInDatabase(getStudentStorageRef(),Globals.FIELD_SURNAME, surname);
    }

    public void signIn(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)

                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                        }

                    }
                });
    }

    public FirebaseUser getFirebaseUser() {
        return mAuth.getCurrentUser();
    }
}

