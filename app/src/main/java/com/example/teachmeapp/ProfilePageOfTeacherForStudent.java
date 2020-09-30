package com.example.teachmeapp;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.teacherProfileLessonsAdapter;
import com.example.teachmeapp.Chat.ChatWindow;
import com.example.teachmeapp.Helpers.Globals;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.teachmeapp.Helpers.Globals.FIELD_NAME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_PRICE;
import static com.example.teachmeapp.Helpers.Globals.FIELD_TEACHERHOME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.example.teachmeapp.Helpers.Globals.LEVEL;
import static com.example.teachmeapp.Helpers.Globals.comm;


public class ProfilePageOfTeacherForStudent extends HamburgerMenu {


    String m_uid;
    private ImageView m_profile_pic, checkZoom, checkFaceToFace;
    private TextView m_teacherName, giveRate;
    RatingBar m_teacherRating;
    private Button m_goToLocationButton, chatBtn;
    SearchResultsRow user;

    // Bundle bundle = getIntent().getExtras();

    //  ImageButton ButtonStar;
    private TextView emptyListView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<TeacherLessonRow> lessons;

    private Dialog dialog;
    private ProgressBar progressBar;
    private RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (comm.isTeacher()) {
            m_uid = comm.getUid();
        } else {
            m_uid = comm.getViewedUserUID();
        }
        emptyListView = findViewById(R.id.emptyView);
        //getIntent().getStringExtra("UID" );

        //   user = bundle.getParcelable("user");

        //comm.realTimeUpdateViewedUserData(m_uid, true);

        setContentView(R.layout.activity_profile_page_of_teacher_for_student);
        //profile_page_list_lesson_offered = findViewById(R.id.profile_page_list_lesson_offered);
        //m_getLessonButton = findViewById(R.id.button_getLesson_displayProfile);
        m_goToLocationButton = findViewById(R.id.button_showLocation_displayProfile);
        m_profile_pic = findViewById(R.id.profile_page_user_picture);
        m_teacherRating = findViewById(R.id.profile_page_rating_bar);
        m_teacherName = findViewById(R.id.profile_page_teacher_name);
        giveRate = findViewById(R.id.give_rate);
        chatBtn = findViewById(R.id.chat_with_teacher);
        checkZoom = findViewById(R.id.profile_page_image_view_check_or_cancel_zoom);
        checkFaceToFace = findViewById(R.id.profile_page_image_view_check_or_cancel_at_my_place);
        if (comm.isTeacher()) {

            checkZoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (comm.isUserZoom()) {

                        checkZoom.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cancel_icon));
                        updateZoomAndFaceToFace(FIELD_ZOOM, false);

                    } else {

                        checkZoom.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.check_icon));
                        updateZoomAndFaceToFace(FIELD_ZOOM, true);
                    }
                }
            });

            checkFaceToFace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (comm.isUserTeacherHome()) {

                        checkFaceToFace.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cancel_icon));
                        updateZoomAndFaceToFace(FIELD_TEACHERHOME, false);

                    } else {

                        checkFaceToFace.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.check_icon));
                        updateZoomAndFaceToFace(FIELD_TEACHERHOME, true);
                    }
                }
            });
        }

        if (comm.isTeacher()) {
            giveRate.setVisibility(View.INVISIBLE);
            m_goToLocationButton.setVisibility(View.INVISIBLE);
            chatBtn.setVisibility(View.INVISIBLE);
        }

        recyclerView = (RecyclerView) findViewById(R.id.Recycler_View_TeacherProfile_LessonsOffered);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //  requiredData();

        lessons = new ArrayList<>();

        giveRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpRateDialog();
            }
        });

        //  recycleViewFill();
    }
//
//    private void requiredData() {
//        comm.getViewedUserData(m_uid, !comm.isTeacher());
//    }

    private void updateZoomAndFaceToFace(final String field, Boolean value) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Globals.COLLECTION_TEACHER).document(comm.getUid())
                .update(field, value)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Update Rate", "DocumentSnapshot successfully written!");

                        Toast.makeText(ProfilePageOfTeacherForStudent.this, "Done setting your new " + (field.equals("zoom") ? "Zoom" : "Face-to-Face") + " status.",
                                Toast.LENGTH_LONG).show();
                        comm.realtimeUpadateMyData();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Update Rate Error", "Error writing document", e);

                        Toast.makeText(ProfilePageOfTeacherForStudent.this, "Error with setting your " + field + " Status. Try again later!",
                                Toast.LENGTH_LONG).show();
                    }
                });


    }

    private void setUpRateDialog() {

        dialog = new Dialog(ProfilePageOfTeacherForStudent.this);
        dialog.setContentView(R.layout.custom_give_rate_dialog);

        ratingBar = dialog.findViewById(R.id.rating_bar);
        Button confirm = dialog.findViewById(R.id.confirm_btn);
        ImageButton cancel = dialog.findViewById(R.id.close_btn);
        progressBar = dialog.findViewById(R.id.progressBar);

        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                int count = comm.getViewedUserRatingCount();
                int currentRating = Math.toIntExact(Math.round(comm.getViewedUserStarRating()));
                int totalRating = count * currentRating;
                int afterRate = totalRating + Math.round(ratingBar.getRating());
                int finalRating = afterRate / (count + 1);

                updateRating(finalRating, count + 1);

            }
        });

        dialog.show();


    }

    private void updateRating(final int finalRating, int count) {

        HashMap<String, Object> data = new HashMap<>();
        data.put("rating", finalRating);
        data.put("numberOFRatings", count);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Globals.COLLECTION_TEACHER).document(comm.getViewedUserUID())
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Update Rate", "DocumentSnapshot successfully written!");

                        comm.realTimeUpdateViewedUserData(comm.getViewedUserUID(), true);
                        progressBar.setVisibility(View.GONE);
                        dialog.dismiss();
                        m_teacherRating.setRating(Float.parseFloat(String.valueOf(finalRating)));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Update Rate Error", "Error writing document", e);

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProfilePageOfTeacherForStudent.this, "Error with setting your rating. Try again later!",
                                Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

    }


    private void recycleViewFill() {

        lessons.clear();

        if (comm.isTeacher()) {

            if (comm.getUserLessons() != null) {
                for (Map.Entry lesson : comm.getUserLessons().entrySet()) {
                    HashMap<String, Object> less = (HashMap<String, Object>) lesson.getValue();
                    // HashMap userLesson = (HashMap)lesson.getValue();
                    TeacherLessonRow temp = new TeacherLessonRow((String) less.get(FIELD_NAME), ((Long) less.get(LEVEL)).intValue(), less.get(FIELD_PRICE).toString());
                    lessons.add(temp);
                }
            }


        } else {
            if (comm.getViewedUserLessons() != null) {
                for (Map.Entry lesson : comm.getViewedUserLessons().entrySet()) {
                    HashMap<String, Object> less = (HashMap<String, Object>) lesson.getValue();
                    // HashMap userLesson = (HashMap)lesson.getValue();
                    TeacherLessonRow temp = new TeacherLessonRow((String) less.get(FIELD_NAME), ((Long) less.get(LEVEL)).intValue(), less.get(FIELD_PRICE).toString());
                    lessons.add(temp);
                }
            }

        }
        adapter = new teacherProfileLessonsAdapter(lessons, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadImage();

        if (comm.isTeacher()) {
            ImageView_profile_page_check_or_cancel(comm.isUserZoom(), comm.isUserTeacherHome());
        } else {
            ImageView_profile_page_check_or_cancel(comm.isViewedUserZoom(), comm.isViewedUserTeacherHome());
        }

        // setStar();
        loadTeacherData();
        adapter = new teacherProfileLessonsAdapter(lessons, this);
        recyclerView.setAdapter(adapter);
//        if (comm.getViewedUserData(m_uid, true)) {
//        }
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
                            //                       ButtonStar.setTag("on");
                            //                      ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_btn_star_on));
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
                Picasso.get().load(uri.toString()).into(m_profile_pic);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), Globals.ERROR_LOADING_PICTURE, Toast.LENGTH_LONG).show();
            }
        });

    }

//    public void onToggleStar(View view) {
//        if (ButtonStar.getTag() == "on") {
//            ButtonStar.setTag("off");
//            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_btn_star_off));
//            comm.removeTeacherToFavourites(m_uid);
//
//        } else {
//            ButtonStar.setTag("on");
//            ButtonStar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite_btn_star_on));
//            comm.addTeacherToFavourites(m_uid);
//
//        }
//    }

    public void ImageView_profile_page_check_or_cancel(boolean zoom, boolean teacherHome) {

        ImageView imageView = findViewById(R.id.profile_page_image_view_check_or_cancel_at_my_place);
        if (teacherHome) {
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

        if (comm.isTeacher()) {
            m_teacherName.setText(comm.getUserName() + " " + comm.getUserSurname());
            m_teacherRating.setRating(Float.parseFloat(comm.getUserStarRating().toString()));
        } else {
            m_teacherName.setText(comm.getViewedUserName() + " " + comm.getViewedUserSurname());
            m_teacherRating.setRating(Float.parseFloat(comm.getViewedUserStarRating().toString()));
        }

        // Picasso.get().load(comm.getM_viewedUserImageURI()).into(m_profile_pic);
        recycleViewFill();
        adapter = new teacherProfileLessonsAdapter(lessons, this);
        recyclerView.setAdapter(adapter);

        /* comm.getDocRef(m_uid, COLLECTION_TEACHER).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

                        Integer level = lesson.getlevel();

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
        });*/
    }

    public void OnClick_profile_button_start_chat(View view) {

        Intent intent = new Intent(ProfilePageOfTeacherForStudent.this, ChatWindow.class);

        intent.putExtra(Globals.IS_TEACHER, false);

        if (!comm.isTeacher()) {
            intent.putExtra(Globals.STUDENTS, comm.getUid());
            intent.putExtra(Globals.TEACHERS, comm.getViewedUserUID());
        }
        Globals.currentTalkedWithUID = comm.getViewedUserUID();
        startActivity(intent);
    }

    public void OnClick_profile_button_check_location(View view) {

        Globals.locationOrSignUp = false;
        Intent intent = new Intent(getApplicationContext(), Maps_activity_get_location.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        super.onDestroy();
    }

    @Override
    protected void onStop() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        super.onStop();
    }

    public void OnClick_ProfilePage_AboutMe(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        TextView textView = popupWindow.getContentView().findViewById(R.id.TextView_PopupWindow);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        // dismiss the popup window when touched
        if(comm.isTeacher()) {
            textView.setText(comm.getUserBio());
        }
        else{
        textView.setText(comm.getViewedUserBio());}
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();

                return true;
            }
        });
    }
}
