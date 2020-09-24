package com.example.teachmeapp.Helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_STUDENT;
import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_COMMENTS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSON_TOPIC_LIST;
import static com.example.teachmeapp.Helpers.Globals.FIELD_NAME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_RATING;
import static com.example.teachmeapp.Helpers.Globals.FIELD_SCHEDULE;
import static com.example.teachmeapp.Helpers.Globals.FIELD_STUDENTHOME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_SURNAME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.example.teachmeapp.Helpers.Globals.LALTITUDE;
import static com.example.teachmeapp.Helpers.Globals.LOCATION;
import static com.example.teachmeapp.Helpers.Globals.LONGITUDE;
import static com.example.teachmeapp.Helpers.Globals.PENDING_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class communicationWithDatabase {
    String TAG = "communication with database";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser m_user = mAuth.getCurrentUser();

    public FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    public FirebaseFirestore getDb() {
        return db;
    }

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference m_userDatabase;
    private String res;

    //current user
    private Map<String, UserLesson> m_currentUserLessons;
    private String m_currentUserFirstName;
    private String m_currentUserLastName;
    private String m_currentUserBio;
    private Double m_currentUserStarRating;
    private int m_currentUserRatingCount;
    private boolean m_teacher = false;
    private String m_currentUserPhone;
    private String m_currentUserEmail;
    private String m_currentUserCity, m_currentUserCountry;
    private Schedule m_currentUserPendingLessons;
    private Schedule m_currentUserCalendar;
    private List<Comment> m_currentUserComments;
    private HashMap m_currentUserLocation;
    private boolean m_currentUserZoom;
    private boolean m_currentUserStudentHome;
    private boolean m_currentUserTeacherHome;
    private List<String> m_currentUserRatingsList;
    private List<String> m_currentUserFavourites;
    private Uri m_currentUserImageURI;


    private Uri temp;

    //viewed user
    private Map<String, UserLesson> m_viewedUserLessons;
    private String m_viewedUserFirstName;
    private String m_viewedUserLastName;
    private String m_viewedUserBio;
    private Double m_viewedUserStarRating;
    private int m_viewedUserRatingCount;
    private String m_viewedUserPhone;
    private String m_viewedUserEmail;
    private String m_viewedUserCity, m_viewedUserCountry;
    private Schedule m_viewedUserCalendar;
    private HashMap m_viewedUserLocation;
    private Schedule m_viewedUserPendingLessons;
    private List<Comment> m_viewedUserComments;
    private boolean m_viewedUserZoom;
    private boolean m_viewedUserStudentHome;
    private boolean m_viewedUserTeacherHome;
    private String m_viewedUserUID;
    private List<String> m_viewedUserRatingsList;
    private List<String> m_viewedUserFavourites;
    private Uri m_viewedUserImageURI;


    public Uri getM_viewedUserImageURI() {
        return m_viewedUserImageURI;
    }

    public void setTeacher(boolean m_teacher) {
        this.m_teacher = m_teacher;
    }

    public boolean isTeacher() {
        return m_teacher;
    }

    public List<String> getViewedUserRatingsList() {
        return m_viewedUserRatingsList;
    }


    public List<String> getViewedUserFavourites() {
        return m_viewedUserFavourites;
    }

    public Schedule getUserCalendar() {
        return m_currentUserCalendar;
    }

    public Schedule getUserPendingLessons() {
        return m_currentUserPendingLessons;
    }

    public Double getUserStarRating() {
        return m_currentUserStarRating;
    }

    public String getUserPhone() {
        return m_currentUserPhone;
    }

    public int getUserRatingCount() {
        return m_currentUserRatingCount;
    }

    public List<Comment> getUserComments() {
        return m_currentUserComments;
    }

    public LatLng getUserLocation() {
        return new LatLng(Double.parseDouble(m_currentUserLocation.get(LALTITUDE).toString()), (Double.parseDouble(m_currentUserLocation.get(LONGITUDE).toString())));
    }

    public String getUserCity() {
        return m_currentUserCity;
    }

    public String getUserCountry() {
        return m_currentUserCountry;
    }

    public boolean isUserZoom() {
        return m_currentUserZoom;
    }

    public boolean isUserStudentHome() {
        return m_currentUserStudentHome;
    }

    public boolean isUserTeacherHome() {
        return m_currentUserTeacherHome;
    }

    public List<String> getUserRatingsList() {
        return m_currentUserRatingsList;
    }

    public List<String> getUserFavourites() {
        return m_currentUserFavourites;
    }

    public Map<String, UserLesson> getUserLessons() {
        return m_currentUserLessons;
    }

    public String getUserSurname() {
        return m_currentUserLastName;
    }

    public String getUserName() {
        return m_currentUserFirstName;
    }

    public String getUserEmail() {
        return m_currentUserEmail;
    }

    public String getUserBio() {
        return m_currentUserBio;
    }

    public Map<String, UserLesson> getViewedUserLessons() {
        return m_viewedUserLessons;
    }

    public Schedule getViewedUserCalendar() {
        return m_viewedUserCalendar;
    }

    public Schedule getViewedUserPendingLessons() {
        return m_viewedUserPendingLessons;
    }

    public Double getViewedUserStarRating() {
        return m_viewedUserStarRating;
    }

    public String getViewedUserPhone() {
        return m_viewedUserPhone;
    }

    public int getViewedUserRatingCount() {
        return m_viewedUserRatingCount;
    }

    public List<Comment> getViewedUserComments() {
        return m_viewedUserComments;
    }

    public LatLng getViewedUserLocation() {
        return new LatLng(Double.parseDouble(m_viewedUserLocation.get(LALTITUDE).toString()), Double.parseDouble(m_viewedUserLocation.get(LOCATION).toString()));
    }

    public String getViewedUserCity() {
        return m_viewedUserCity;
    }

    public String getViewedUserCountry() {
        return m_viewedUserCountry;
    }

    public boolean isViewedUserZoom() {
        return m_viewedUserZoom;
    }

    public boolean isViewedUserStudentHome() {
        return m_viewedUserStudentHome;
    }

    public boolean isViewedUserTeacherHome() {
        return m_viewedUserTeacherHome;
    }

    public String getViewedUserSurname() {
        return m_viewedUserLastName;
    }

    public String getViewedUserName() {
        return m_viewedUserFirstName;
    }

    public String getViewedUserEmail() {
        return m_viewedUserEmail;
    }

    public String getViewedUserBio() {
        return m_viewedUserBio;
    }

    public String getViewedUserUID() {
        return m_viewedUserUID;
    }


    //here you can insert data to the data base itself (no the storage just the database) you will need to specify the data (a map containing the fields) the collection (teacher/student/admin/ any other you may want to add)
    //and finally the collection (i have it set to be the user id of the user for easier access)

    public void realTimeUpdateViewedUserData(String uid, final boolean teacher) {

        String collection = (!teacher) ? COLLECTION_TEACHER : COLLECTION_STUDENT;

        db.collection(collection).document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null && value != null && value.exists())
                {
                    DocumentSnapshot document = value;

                    m_viewedUserFirstName = document.get(Globals.FIELD_NAME).toString();
                    m_viewedUserLastName = document.get(Globals.FIELD_SURNAME).toString();
                    m_viewedUserPhone = document.get(Globals.FIELD_PHONE).toString();
                    m_viewedUserEmail = document.get(Globals.FIELD_EMAIL).toString();
                    m_viewedUserCity = document.get(Globals.CITY).toString();
                    m_viewedUserCountry = document.get(Globals.COUNTRY).toString();
                    m_viewedUserCalendar = new Schedule((HashMap<String, BookedLesson>)document.get(FIELD_SCHEDULE));
                    m_viewedUserUID = (String) document.get(Globals.FIELD_UID);

                    if (teacher) {
                        Log.d("GETSURENAME","if (teacher) {");
                        m_viewedUserBio = document.get(Globals.FIELD_BIO).toString();
                        m_viewedUserPendingLessons = new Schedule ((HashMap<String, BookedLesson>) document.get(PENDING_LESSONS));
                        m_viewedUserStarRating = (Double) document.get(Globals.FIELD_RATING);
                        m_viewedUserRatingCount = ((Long) document.get(Globals.RATING_COUNT)).intValue();
                        m_viewedUserLessons = (Map<String, UserLesson>) document.get(FIELD_LESSONS);

                    }

                    storage.getReference().child("images/" + m_viewedUserUID + "/profile picture").
                            getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            m_viewedUserImageURI = uri;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
            }
        });
    }


    private void realtimeUpadateMyData()
    {
        String collection = (isTeacher()) ? COLLECTION_TEACHER : COLLECTION_STUDENT;
        db.collection(collection).document(getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null && value != null && value.exists())
                {
                    DocumentSnapshot document = value;
                    m_currentUserFirstName = document.get(Globals.FIELD_NAME).toString();
                    m_currentUserLastName = document.get(Globals.FIELD_SURNAME).toString();
                    m_currentUserPhone = document.get(Globals.FIELD_PHONE).toString();
                    m_currentUserEmail = document.get(Globals.FIELD_EMAIL).toString();
                    m_currentUserCity = document.get(Globals.CITY).toString();
                    m_currentUserCountry = document.get(Globals.COUNTRY).toString();
                    m_currentUserLocation = (HashMap) document.get(LOCATION);
                    m_currentUserPendingLessons = new Schedule ((HashMap<String, BookedLesson>) document.get(PENDING_LESSONS));
                    m_currentUserCalendar = new Schedule((HashMap<String, BookedLesson>)document.get(FIELD_SCHEDULE));

                    if (m_teacher) {
                        m_currentUserStarRating = (Double) document.get(Globals.FIELD_RATING);
                        m_currentUserRatingCount =((Long) document.get(Globals.RATING_COUNT)).intValue();
                        m_currentUserBio = document.get(Globals.FIELD_BIO).toString();
                        m_currentUserLessons = (Map<String, UserLesson>) document.get(FIELD_LESSONS);
                        m_currentUserComments = (List<Comment>) document.get(FIELD_COMMENTS);
                        m_currentUserZoom = (boolean) document.get(FIELD_ZOOM);
                        m_currentUserStudentHome = (boolean) document.get(FIELD_STUDENTHOME);
                        m_currentUserTeacherHome = (boolean) document.get(FIELD_STUDENTHOME);
                    } else {
                        m_currentUserRatingsList = (List<String>) document.get(Globals.RATINGS);
                        m_currentUserFavourites = (List<String>) document.get(Globals.FAVOURITES);
                    }
                    storage.getReference().child("images/" + comm.getUid() + "/profile picture").
                            getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            m_currentUserImageURI = uri;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
            }
        });
    }


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
        if (m_teacher)
            collection = COLLECTION_TEACHER;
        else
            collection = COLLECTION_STUDENT;

        db.collection(collection).document(getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    m_currentUserFirstName = document.get(Globals.FIELD_NAME).toString();
                    m_currentUserLastName = document.get(Globals.FIELD_SURNAME).toString();
                    m_currentUserPhone = document.get(Globals.FIELD_PHONE).toString();
                    m_currentUserEmail = document.get(Globals.FIELD_EMAIL).toString();
                    m_currentUserCity = document.get(Globals.CITY).toString();
                    m_currentUserCountry = document.get(Globals.COUNTRY).toString();
                    m_currentUserLocation = (HashMap) document.get(LOCATION);
                    m_currentUserPendingLessons = new Schedule ((HashMap<String, BookedLesson>) document.get(PENDING_LESSONS));
                    m_currentUserCalendar = new Schedule((HashMap<String, BookedLesson>)document.get(FIELD_SCHEDULE));

                    if (m_teacher) {
                        m_currentUserStarRating = (Double) document.get(Globals.FIELD_RATING);
                        m_currentUserRatingCount =((Long) document.get(Globals.RATING_COUNT)).intValue();
                        m_currentUserBio = document.get(Globals.FIELD_BIO).toString();
                        m_currentUserLessons = (Map<String, UserLesson>) document.get(FIELD_LESSONS);
                        m_currentUserComments = (List<Comment>) document.get(FIELD_COMMENTS);
                        m_currentUserZoom = (boolean) document.get(FIELD_ZOOM);
                        m_currentUserStudentHome = (boolean) document.get(FIELD_STUDENTHOME);
                        m_currentUserTeacherHome = (boolean) document.get(FIELD_STUDENTHOME);
                    } else {
                        m_currentUserRatingsList = (List<String>) document.get(Globals.RATINGS);
                        m_currentUserFavourites = (List<String>) document.get(Globals.FAVOURITES);
                    }

                    storage.getReference().child("images/" + comm.getUid() + "/profile picture").
                            getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            m_currentUserImageURI = uri;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
            }
        });
        realtimeUpadateMyData();
    }

    public boolean getViewedUserData(String uid, final boolean teacher, final Context context, final Intent intent) {

        String collection = (teacher) ? COLLECTION_TEACHER : COLLECTION_STUDENT;

        db.collection(collection).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("GETSURENAME","task.isSuccessful()");
                    DocumentSnapshot document = task.getResult();
                    m_viewedUserFirstName = document.get(Globals.FIELD_NAME).toString();
                    m_viewedUserLastName = document.get(Globals.FIELD_SURNAME).toString();
                    m_viewedUserPhone = document.get(Globals.FIELD_PHONE).toString();
                    m_viewedUserEmail = document.get(Globals.FIELD_EMAIL).toString();
                    m_viewedUserCity = document.get(Globals.CITY).toString();
                    m_viewedUserCountry = document.get(Globals.COUNTRY).toString();
                    m_viewedUserCalendar = new Schedule((HashMap<String, BookedLesson>)document.get(FIELD_SCHEDULE));
                    m_viewedUserUID = (String) document.get(Globals.FIELD_UID);

                    if (teacher) {
                        Log.d("GETSURENAME","if (teacher) {");
                        m_viewedUserBio = document.get(Globals.FIELD_BIO).toString();
                        m_viewedUserPendingLessons = new Schedule ((HashMap<String, BookedLesson>) document.get(PENDING_LESSONS));
                        m_viewedUserStarRating = (Double) document.get(Globals.FIELD_RATING);
                        m_viewedUserRatingCount = ((Long) document.get(Globals.RATING_COUNT)).intValue();
                        m_viewedUserLessons = (Map<String, UserLesson>) document.get(FIELD_LESSONS);
                        m_viewedUserZoom = (boolean) document.get(FIELD_ZOOM);
                        m_viewedUserStudentHome = (boolean) document.get(FIELD_STUDENTHOME);
                        m_viewedUserTeacherHome = (boolean) document.get(FIELD_STUDENTHOME);
                    }

                    storage.getReference().child("images/" + m_viewedUserUID + "/profile picture").
                            getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            m_viewedUserImageURI = uri;
                            context.startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
            }
        });
       //realTimeUpdateViewedUserData(uid, teacher);
        if(m_viewedUserFirstName != null)
            return true;
        else
            return false;
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

    public DocumentReference getStorageRef() {
        if (m_teacher)
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

    public void createTeacher(String name, String surname, String email, String phoneNumber, ArrayList<String> languages) {// will now also upload the image, all I need is the location on the device of the image.
        final Map<String, Object> user = new HashMap<>();
        float rating = 0;
        m_user = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Teacher teacher = new Teacher(name, surname, phoneNumber, new ArrayList<Comment>(), email, m_user.getUid(), languages);
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

    public void createStudent(String name, String surname, String email, String phoneNumber, ArrayList<String> languages) {// will now also upload the image, all I need is the location on the device of the image.

        final Map<String, Object> user = new HashMap<>();
        float rating = 0;
        m_user = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Student student = new Student(name, surname, phoneNumber, new HashMap<String, UserLesson>(), email, m_user.getUid(), languages);
        insertStudentToDatabase(student, "Students", m_user.getUid());
        realtimeUpadateMyData();
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

    public void addCourse(String lesson, String uid, Double price, Integer level) {
        //addLessonToDatabase(lesson);
        String collection = ((isTeacher()) ? COLLECTION_TEACHER : COLLECTION_STUDENT);
        if (m_teacher) {
            Map temp = new HashMap<String, UserLesson>();
            temp.put(lesson, new UserLesson(lesson, price, level));
            addLessonToTeacher(new UserLesson(lesson, price, level), lesson);
            addTeacherToLesson(lesson, getUid());
            addLessonToLessonList(lesson);
        } else {
            addLessonToStudent(new UserLesson(lesson, price, level), uid);
        }
        //comm.getData();
    }

    private void addLessonToLessonList(String lesson) {
        db.collection(COLLECTION_TEACHER).document(getUid()).update(FIELD_LESSON_TOPIC_LIST, FieldValue.arrayUnion(lesson));
    }

    private void addTeacherToLesson(final String name, final String uid) {
        db.collection("lessons").document(name)
                .update(Globals.FIELD_TEACHERS, FieldValue.arrayUnion(uid))
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


    private void addLessonToTeacher(final /*Map<String, */UserLesson lesson, String name) {
        String collection;
        collection = "Teachers";
        m_user = mAuth.getCurrentUser();
        db.collection(collection).document(m_user.getUid()).update("lessons." + name, lesson);

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
        m_currentUserFirstName = name;
    }

    public void changeSurname(String surname) {
        updateElementInDatabase(getStorageRef(), Globals.FIELD_SURNAME, surname);

        m_currentUserLastName = surname;
    }

    public void changeBio(String bio) {
        updateElementInDatabase(getStorageRef(), Globals.FIELD_BIO, bio);
        m_currentUserBio = bio;
    }

    public void changePhoneNumber(String phone) {
        updateElementInDatabase(getStorageRef(), Globals.FIELD_PHONE, phone);
        m_currentUserPhone = phone;
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
                });
    }

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
        if (m_teacher) {
            ref = getDocRef(getUid(), COLLECTION_TEACHER);
            ref.update(LOCATION, location);
            ref.update(Globals.CITY, city);
            ref.update(Globals.COUNTRY, country);
            ref.update(Globals.ADDRESS, address);
        }

        ref = getDocRef(getUid(), COLLECTION_STUDENT);
        ref.update(LOCATION, location);
        ref.update(Globals.CITY, city);
        ref.update(Globals.COUNTRY, country);
        ref.update(Globals.ADDRESS, address);
    }

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

    public CollectionReference getCollectionReference(String uid, Boolean teacher) {
        if (teacher)
            return db.collection(COLLECTION_TEACHER);
        else
            return db.collection(COLLECTION_STUDENT);
    }

    public DocumentReference getDocumentReference(String uid, Boolean teacher) {
        if (teacher)
            return db.collection(COLLECTION_TEACHER).document(uid);
        else
            return db.collection(COLLECTION_STUDENT).document(uid);
    }

    public String getCity() {
        return m_currentUserCity;
    }


    public String getCountry() {
        return m_currentUserCountry;
    }

    public void removeCourseFromTeacher(final String lesson) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(lesson, FieldValue.delete());
        db.collection(COLLECTION_TEACHER).document(m_user.getUid())
                .update("lessons" + "." + lesson, FieldValue.delete())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection(COLLECTION_TEACHER).document(m_user.getUid())
                                .update(FIELD_LESSON_TOPIC_LIST, FieldValue.arrayRemove(lesson));                   }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void removeBookedLesson(BookedLesson lesson) {
        if (m_teacher) {
            db.collection(COLLECTION_TEACHER).document(m_user.getUid())
                    .update(Globals.FIELD_SCHEDULE, FieldValue.arrayRemove(lesson))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully removed!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

            db.collection(COLLECTION_STUDENT).document(lesson.getTeacherUID())
                    .update(Globals.FIELD_SCHEDULE, FieldValue.arrayRemove(lesson))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully removed!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        } else {
            db.collection(COLLECTION_STUDENT).document(m_user.getUid())
                    .update(FIELD_SCHEDULE, FieldValue.arrayRemove(lesson))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully removed!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

            db.collection(COLLECTION_TEACHER).document(lesson.getTeacherUID())
                    .update(Globals.FIELD_SCHEDULE, FieldValue.arrayRemove(lesson))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully removed!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }
    }

    public void addBookedLesson(final BookedLesson lesson) {
        //addLessonToDatabase(lesson)
        if (m_teacher) {
            db.collection(COLLECTION_TEACHER).document(m_user.getUid())
                    .update(Globals.FIELD_SCHEDULE, FieldValue.arrayUnion(lesson))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            removeLessonFromPending(lesson, getUid(), COLLECTION_TEACHER);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

            db.collection(COLLECTION_STUDENT).document(lesson.getTeacherUID())
                    .update(Globals.FIELD_SCHEDULE, FieldValue.arrayUnion(lesson))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            removeLessonFromPending(lesson, lesson.getTeacherUID(), COLLECTION_STUDENT);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        } else {
            db.collection(COLLECTION_STUDENT).document(getUid())
                    .update(FIELD_SCHEDULE, FieldValue.arrayUnion(lesson))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            removeLessonFromPending(lesson, getUid(), COLLECTION_TEACHER);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

            db.collection(COLLECTION_TEACHER).document(lesson.getTeacherUID())
                    .update(Globals.FIELD_SCHEDULE, FieldValue.arrayUnion(lesson))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully removed!");
                            removeLessonFromPending(lesson, lesson.getTeacherUID(), COLLECTION_STUDENT);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }
    }

    private void removeLessonFromPending(BookedLesson lesson, String teacherStudentUID, String collection) {
        db.collection(collection).document(teacherStudentUID)
                .update(PENDING_LESSONS, FieldValue.arrayRemove(lesson)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully removed!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        ;
    }

    /*
    public Map<String, UserLesson> getTargetLessons(String uid) {
        getTeacherLessons(uid);
        return m_currentUserLessons;
    }

    private void getTeacherLessons(String uid) {
        db.collection(FIELD_TEACHERS).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    m_viewedUserLessons = (Map<String, UserLesson>) task.getResult().get(FIELD_LESSONS);
                }
            }
        });
    }*/

    public ArrayList<UserLesson> MapToArray(Map<String, UserLesson> target) {
        ArrayList<UserLesson> res = new ArrayList<>();
        int i = 0;
        if (target != null) {
            for (Map.Entry<String, UserLesson> lesson : target.entrySet()) {
                res.add(lesson.getValue());
                return res;
            }
        }
        return null;
    }

    public ArrayList<BookedLesson> MapToArrayBookedLessons(Map<String, BookedLesson> target) {
        ArrayList<BookedLesson> res = new ArrayList<>();
        int i = 0;
        if (target != null) {
            for (Map.Entry<String, BookedLesson> lesson : target.entrySet()) {
                res.add(lesson.getValue());
                return res;
            }
        }
        return null;
    }

    public void addOrEditLessonRequest(LocalDateTime start, LocalDateTime end, String lesson, Integer level, double price, boolean zoom, boolean teachersPlace, boolean studentPlace) {


        String teacherName, teacherUID, studentName, studentUID;
        teacherName = ((isTeacher()) ? getUserName() : getViewedUserName());
        teacherUID = ((isTeacher()) ? getUid() : getViewedUserUID());

        studentName = ((!isTeacher()) ? getUserName() : getViewedUserName());
        studentUID = ((!isTeacher()) ? getUid() : getViewedUserUID());

        final BookedLesson bookedLesson = new BookedLesson(new UserLesson(lesson, price, level), start, end, teacherName, teacherUID, studentName, studentUID, zoom, teachersPlace, studentPlace, isTeacher());
        final String key = keyBuilder(lesson, start.toString(), teacherName, studentName);

        final String collection = ((isTeacher()) ? COLLECTION_TEACHER : COLLECTION_STUDENT);
        final String secondCollection = ((!isTeacher()) ? COLLECTION_TEACHER : COLLECTION_STUDENT);

        db.collection(collection).document(getViewedUserUID()).update(PENDING_LESSONS + "." + key, bookedLesson).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                m_viewedUserPendingLessons.getLessons().put(key, bookedLesson);
                db.collection(secondCollection).document(m_viewedUserUID).update(PENDING_LESSONS + "." + key, bookedLesson);
            }
        });
    }

    public boolean acceptLessonRequest(final String lessonKey) {

        final BookedLesson lesson = m_currentUserPendingLessons.getLessons().get(lessonKey);
        final String collection =  ((isTeacher()) ? COLLECTION_TEACHER : COLLECTION_STUDENT);
        final String secondCollection = ((isTeacher()) ? COLLECTION_TEACHER : COLLECTION_STUDENT);


        BookedLesson tester = m_currentUserPendingLessons.getLessons().get(lessonKey);

        if (tester != null) {
            db.collection(collection).document(getUid()).update(FIELD_SCHEDULE + "." + lessonKey, lesson).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    db.collection(collection).document(getUid()).update(PENDING_LESSONS + "." + lessonKey, FieldValue.delete());
                    db.collection(secondCollection).document(m_viewedUserUID).update(FIELD_SCHEDULE + "." + lessonKey, lesson).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            db.collection(secondCollection).document(lesson.getTeacherUID()).update(PENDING_LESSONS + "." + lessonKey, FieldValue.delete());
                        }
                    });
                }
            });

            return true;
        }

    return false;

    }

    public void deleteLessonRequest(final String lessonKey)
    {
        final String collection =  ((isTeacher()) ? COLLECTION_TEACHER : COLLECTION_STUDENT);

        db.collection(collection).document(getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    final String teacherUID = ((Schedule) task.getResult().get(PENDING_LESSONS)).getLessons().get(lessonKey).getTeacherUID();
                    final String studentUID = ((Schedule) task.getResult().get(PENDING_LESSONS)).getLessons().get(lessonKey).getTeacherUID();

                    db.collection(COLLECTION_TEACHER).document(teacherUID).update(PENDING_LESSONS + "." + lessonKey, FieldValue.delete());
                    db.collection(COLLECTION_STUDENT).document(studentUID).update(PENDING_LESSONS + "." + lessonKey, FieldValue.delete());
                }
            }
        });
    }


    public String keyBuilder(String subject, String startTime, String teacherName, String studentName) {
        //String teacherName1 = ((isTeacher()) ? getUserName() : getViewedUserName());
        //String studentName1 = ((!isTeacher()) ? getUserName() : getViewedUserName());

        return teacherName + studentName + subject + startTime;
    }

    public Uri getM_currentUserImageURI() {
        return m_currentUserImageURI;
    }

}

