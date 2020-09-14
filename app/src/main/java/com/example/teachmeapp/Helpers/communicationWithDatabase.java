package com.example.teachmeapp.Helpers;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.teachmeapp.EditTeacherInfo;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_STUDENT;
import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_NAME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_RATING;
import static com.example.teachmeapp.Helpers.Globals.FIELD_SURNAME;
import static com.example.teachmeapp.Helpers.Globals.LOCATION;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class communicationWithDatabase {
    String TAG = "communication with database";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser m_user = mAuth.getCurrentUser();

    public FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference m_userDatabase;
    private String res;

    private String m_firstName;
    private String m_lastName;
    private String m_bio;
    private ArrayList<Integer> m_starRating;
    private float m_rating;

    private boolean teacher = false;
    private String m_phone;
    private String m_email;

    private String city, country;
    private Uri temp;

    Calendar m_targetCalendar,m_userCalendar;


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
    }
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

    public void getData() {
        m_user = mAuth.getCurrentUser();
        String collection;
        if (teacher)
            collection = COLLECTION_TEACHER;
        else
            collection = COLLECTION_STUDENT;

        db.collection(collection).document(getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    m_firstName = document.get(Globals.FIELD_NAME).toString();
                    m_lastName = document.get(Globals.FIELD_SURNAME).toString();
                    m_phone = document.get(Globals.FIELD_PHONE).toString();
                    m_email = document.get(Globals.FIELD_EMAIL).toString();
                    if (teacher) {
                        m_starRating = (ArrayList<Integer>) document.get(Globals.FIELD_RATING);
                        m_bio = document.get(Globals.FIELD_BIO).toString();
                    }
                }
            }
        });

        Integer total = 0;

        if (teacher) {
            if (m_starRating != null) {
                for (Integer element : m_starRating) {
                    total += element;
                }
                m_rating = total / m_starRating.size();
            }
        }
    }

    /*public String getName(String group) {
    return getData(group,"name");
    }
{.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
}*/
    public String getSurname() {
        return m_lastName;
    }

    public String getame() {
        return m_firstName;
    }

    public String getPhone() {
        return m_phone;
    }

    public String getEmail() {
        return m_email;
    }

    public float getStarRating() {
        return m_rating;
    }

    public String getBio() {
        return m_bio;
    }

    public DocumentReference getStorageRef() {
        if(teacher)
            return db.collection(Globals.COLLECTION_TEACHER).document(getUid());
        else
            return db.collection(Globals.COLLECTION_STUDENT).document(getUid());
    }

    /*public DocumentReference getStudentStorageRef() {
        return db.collection(Globals.COLLECTION_STUDENT).document(getUid());
    }*/

    private void updateElementInDatabase(DocumentReference docRef, String element, String newValue) {
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

    public void createTeacher(String name, String surname, String email, String phoneNumber) {// will now also upload the image, all I need is the location on the device of the image.
        final Map<String, Object> user = new HashMap<>();
        float rating = 0;
        m_user = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Teacher teacher = new Teacher(name, surname, phoneNumber, new ArrayList<Comment>(), email, m_user.getUid());
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

    public HashMap<String, String> search(HashMap<String, Object> user) {
        HashMap<String, String> res = new HashMap<>();

        storage.getReference().child("images/" + comm.getUid() + "/profile picture").
                getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                temp = uri;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        res.put(FIELD_NAME, (String) user.get(FIELD_NAME));
        res.put(FIELD_SURNAME, (String) user.get(FIELD_SURNAME));
        res.put("uid", (String) user.get("uid"));
        res.put(FIELD_RATING, user.get(FIELD_RATING).toString());
        res.put("picture", temp.toString());

        return res;
    }

    public void createStudent(String name, String surname, String email, String phoneNumber) {// will now also upload the image, all I need is the location on the device of the image.

        final Map<String, Object> user = new HashMap<>();
        float rating = 0;
        m_user = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Student student = new Student(name, surname, phoneNumber, new HashMap<String, UserLesson>(), email, m_user.getUid());
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


    public void addCourse(String lesson, String uid, Float price, String level) {
        //addLessonToDatabase(lesson);
        if (teacher) {
            Map temp = new HashMap<String, UserLesson>();
            temp.put(lesson, new UserLesson(lesson, price, level));
            addLessonToTeacher(temp, lesson);
            addTeacherToLesson(lesson, getUid());
        } else {
            addLessonToStudent(new UserLesson(lesson, price, level), uid);
        }
    }

    private void addTeacherToLesson(final String name,final String uid) {
        db.collection("lessons").document(name)
                .update(name, FieldValue.arrayUnion(uid))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ArrayList<String> uids = new ArrayList<>();
                        uids.add(uid);
                        db.collection("lessons").document(name).set(new Lesson(name, uids))
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
                });
    }

    private void addLessonToStudent(UserLesson lesson, String uid) {
        Map<String, UserLesson> add = new HashMap<String, UserLesson>();
        add.put(uid, lesson);

        db.collection("Students").document(m_user.getUid())
                .update("classes", add)
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

    public void changeName(String name) {
            updateElementInDatabase(getStorageRef(), Globals.FIELD_NAME, name);
        m_firstName = name;
    }

    public void changeSurname(String surname) {
        updateElementInDatabase(getStorageRef(), Globals.FIELD_SURNAME, surname);

        m_lastName = surname;
    }

    public void changeBio(String bio) {
        updateElementInDatabase(getStorageRef(), Globals.FIELD_NAME, bio);
    }

    public void changePhoneNumber(String phone) {
        updateElementInDatabase(getStorageRef(), Globals.FIELD_PHONE, phone);
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            m_user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                        }

                    }
                });


    }

    public FirebaseUser getFirebaseUser() {
        m_user = mAuth.getCurrentUser();
        return m_user;
    }

    public StorageReference profileImagePicRef(String uid) {
       return storage.getReference().child("images/" + uid + "/profile picture");
    }

    public void addTeacherToFavourites(final String uid) {
        db.collection(COLLECTION_STUDENT).document(m_user.getUid())
                .update("favourites", FieldValue.arrayUnion(uid))
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
                }); }

    public void removeTeacherToFavourites(final String uid) {
        db.collection(COLLECTION_STUDENT).document(m_user.getUid())
                .update("favourites", FieldValue.arrayRemove(uid))
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

    public DocumentReference getDocRef(String uid, String collection) {
        return db.collection(collection).document(uid);
    }

    public void setLocation(LatLng location, String city, String country, String address) {
        DocumentReference ref;
        if(teacher)
        {ref = getDocRef(getUid(), COLLECTION_TEACHER);
        ref.update(LOCATION, location);
            ref.update(Globals.CITY, city);
            ref.update(Globals.COUNTRY, country);
            ref.update(Globals.ADDRESS, address);}

        ref = getDocRef(getUid(), COLLECTION_STUDENT);
        ref.update(LOCATION, location);}

    public FirebaseStorage getStorage() {
        return storage;
    }

    public void uploadImage(Uri data) {
        StorageReference pPic = storageRef.child("images/" + comm.getUid() + "/profile picture");
        pPic.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // Toast.makeText(EditTeacherInfo.this, "Profile picture updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

