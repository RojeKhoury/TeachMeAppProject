package com.example.teachmeapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.Helpers.UserLesson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_NAME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_RATING;
import static com.example.teachmeapp.Helpers.Globals.FIELD_SURNAME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.example.teachmeapp.Helpers.Globals.PROFILE_PAGE_OF_SPECIFIC_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT_FOR_SCHDULE;
import static com.example.teachmeapp.Helpers.Globals.comm;


public class ProfilePageOfTeacherForStudent extends HamburgerMenu {


    String m_uid;
    ImageView m_profile_pic;
    TextView m_teacherName;
    RatingBar m_teacherRating;
    Button m_goToLocationButton;
    Button m_getLessonButton;
    ImageButton ButtonStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String UID = getIntent().getStringExtra("UID" );

        setContentView(R.layout.activity_profile_page_of_teacher_for_student);

        m_uid = "CosM3yLfsTOxwnZvc91hY0Um4fn1";//getIntent().getStringExtra("uid");//temporary

        if (UID != null) {
            uidSendToTeacherProfilePageToGetLessonsOffered = UID;
            m_uid = UID;
        }
        else
        {
            m_uid = "CosM3yLfsTOxwnZvc91hY0Um4fn1";//getIntent().getStringExtra("uid");//temporary
        }

        setContentView(R.layout.activity_profile_page_of_teacher_for_student);
        //profile_page_list_lesson_offered = findViewById(R.id.profile_page_list_lesson_offered);
        m_getLessonButton = findViewById(R.id.button_getLesson_displayProfile);
        m_goToLocationButton = findViewById(R.id.button_showLocation_displayProfile);
        m_profile_pic = findViewById(R.id.profile_page_user_picture);
        m_teacherRating = findViewById(R.id.profile_page_rating_bar);
        m_teacherName = findViewById(R.id.profile_page_teacher_name);


        CallViewAdapter(PROFILE_PAGE_OF_SPECIFIC_TEACHER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadImage();
        loadTeacherData();
        setStar();
    }

    private void setStar() {
        comm.getStorageRef().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    List<String> fav = (ArrayList<String>) document.get("favourites");

                    for (String teacher : fav) {
                        if (teacher.equals(m_uid)) {
                            ButtonStar.setTag("on");
                            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_btn_star_on));
                            break;
                        }
                    }
                }
            }
        });
    }

    private void loadImage() {
        comm.profileImagePicRef(m_uid).
                getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.get().load(uri).into(m_profile_pic);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), Globals.ERROR_LOADING_PICTURE, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onToggleStar(View view) {
        if (ButtonStar.getTag() == "on") {
            ButtonStar.setTag("off");
            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_btn_star_off));
            comm.removeTeacherToFavourites(m_uid);

        } else {
            ButtonStar.setTag("on");
            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_btn_star_on));
            comm.addTeacherToFavourites(m_uid);

        }
    }

    public void ImageView_profile_page_check_or_cancel(boolean zoom, boolean teacherHome, boolean studentHome) {

        ImageView imageView = findViewById(R.id.profile_page_image_view_check_or_cancel_at_my_place);

        if (teacherHome) {
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.check_icon));
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cancel_icon));
        }

        imageView = findViewById(R.id.profile_page_image_view_check_or_cancel_at_your_place);
        if (studentHome) {
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.check_icon));
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cancel_icon));
        }

        imageView = findViewById(R.id.profile_page_image_view_check_or_cancel_zoom);
        if (zoom) {
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.check_icon));
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cancel_icon));
        }

    }

    public void loadTeacherData() {
        comm.getDocRef(m_uid, COLLECTION_TEACHER).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    m_teacherName.setText(documentSnapshot.getString(FIELD_NAME) + " " + documentSnapshot.getString(FIELD_SURNAME));
                    m_teacherRating.setRating(Float.parseFloat(documentSnapshot.getDouble(FIELD_RATING).toString()));
                    ImageView_profile_page_check_or_cancel(documentSnapshot.getBoolean(FIELD_ZOOM), documentSnapshot.getBoolean("teacherHome"), documentSnapshot.getBoolean("studentHome"));
                    Iterator it = ((HashMap<String, UserLesson>) documentSnapshot.get(FIELD_LESSONS)).entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        UserLesson lesson = (UserLesson) pair.getValue();
                        String name = lesson.getName();
                        Double price = lesson.getPrice();
                        ;
                        String level = lesson.getlevel();

                        //statesList.add(document.getData().get(FIELD_NAME).toString() + "\n" + "price = " + price);
                        //statesList.add("price = " + price);
                        //need abed's help in inserting this to the list
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "does not exist", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void OnClick_profile_button_get_lessons_now(View view) {

    }
}
