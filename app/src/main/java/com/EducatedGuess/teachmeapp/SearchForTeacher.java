package com.EducatedGuess.teachmeapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.EducatedGuess.teachmeapp.Adapter.SearchForTeacherAdapter;
import com.EducatedGuess.teachmeapp.Helpers.UserLesson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.EducatedGuess.teachmeapp.Helpers.Globals.CITY;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.COUNTRY;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.FIELD_LESSON_TOPIC_LIST;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.FIELD_LEVEL;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.FIELD_NAME;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.FIELD_PRICE;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.FIELD_STUDENTHOME;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.FIELD_SURNAME;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.FIELD_TEACHERHOME;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.FIELD_UID;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.LEVEL;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.RATINGS;
import static com.EducatedGuess.teachmeapp.Helpers.Globals.comm;

public class SearchForTeacher extends HamburgerMenu {


    private EditText editTextKeyword, editTextPrice;
    private RadioGroup radioGroupMeeting;
    private ChipGroup chipGroup;
    private Button search;
    Spinner levelSpinner;
    Long EducationLevel;
    private TextView emptyListView;
    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    private List<SearchResultsRow> teachers;

    boolean foundTeacherFlag = false;
    private boolean zoomMeetingChecked = false;
    private boolean faceMeetingChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_teacher);

        teachers = new ArrayList<>();

        this.editTextKeyword = (EditText) this.findViewById(R.id.editText_keyword);
        editTextPrice = findViewById(R.id.edit_text_price);
        this.search = (Button) findViewById(R.id.searchForTeachersShowResultsButton);
        emptyListView = findViewById(R.id.emptyView);
        radioGroupMeeting = findViewById(R.id.group_filter_meeting);
        levelSpinner = findViewById(R.id.spinner_for_education_level);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchResult);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teachers.clear();
                OnClick_SearchForTeacher_Button_ShowSelections();
            }
        });

        adapter = new SearchForTeacherAdapter(teachers, this);
        recyclerView.setAdapter(adapter);
    }

    // User close a Chip.
    private void handleChipCloseIconClicked(Chip chip) {
        ChipGroup parent = (ChipGroup) chip.getParent();
        parent.removeView(chip);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    public void addNewChip(View view) {
//        try {
//            LayoutInflater inflater = LayoutInflater.from(this);
//            Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip_entry, this.chipGroup, false);
//
//            String tag = editText.getText().toString();
//            if (tag.trim().isEmpty()) {
//                Toast.makeText(this, "Add a tag first", Toast.LENGTH_SHORT).show();
//            } else {
//                newChip.setText(tag);
//
//                this.chipGroup.addView(newChip);
//
//                newChip.setOnCloseIconClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        handleChipCloseIconClicked((Chip) v);
//                    }
//                });
//                this.editTextKeyword.setText("");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//    }

    public void OnClick_SearchForTeacher_Button_ShowSelections() {
//        teachers.clear();
//        int count = this.chipGroup.getCheckedChipIds().size();
//        if (count > 0) {
//            ChipTagSearchedArraySize = count;
//            ChipTagSearchedArray = new String[count];
//            String s = null;
//            for (int i = 0; i < count; i++) {
//
//                Chip child = (Chip) this.chipGroup.getChildAt(i);
//                if (!child.isChecked()) {
//                    continue;
//                }
//
//                /*if (s == null) {
//                    s = child.getText().toString();
//                } else {
//                    s += ", " + child.getText().toString();
//                }*/
//                ChipTagSearchedArray[i] = child.getText().toString();
//            }
            searchUsingTags();
//
//        } else {
//            Toast.makeText(this, "Please add and tick the Tags", Toast.LENGTH_LONG).show();
//
//        }
    }

    private void searchUsingTags() {
        EditText editTextKeyWord = findViewById(R.id.editText_keyword);

        EducationLevel = Long.valueOf(levelSpinner.getSelectedItemPosition());//levelSpinner.getSelectedItem().toString();

        if (radioGroupMeeting.getCheckedRadioButtonId() == -1) {
            Toast.makeText(SearchForTeacher.this, "Please select meeting type!", Toast.LENGTH_LONG).show();
        } else {

            if (radioGroupMeeting.getCheckedRadioButtonId() == R.id.q1_a1) {
                faceMeetingChecked = true;
                zoomMeetingChecked = false;
            } else {
                zoomMeetingChecked = true;
                faceMeetingChecked = false;
            }

            int price = 0;
            if (editTextPrice.getText().toString().isEmpty() || editTextPrice.getText().toString().equals(" ")) {
                editTextPrice.setError("Please insert your preferred max price!");
            } else {
                if (editTextKeyWord.getText().toString().isEmpty() || editTextKeyWord.getText().toString().equals(" ")) {

                    Toast.makeText(this, "Please enter a subject!", Toast.LENGTH_SHORT).show();
                } else {

                    price = Integer.parseInt(editTextPrice.getText().toString());
                    searchForTeachers(editTextKeyWord.getText().toString() + "_" + levelSpinner.getSelectedItem().toString().replace(" ", "")
                            , EducationLevel, zoomMeetingChecked,
                            faceMeetingChecked, price);

                    adapter = new SearchForTeacherAdapter(teachers, this);
                    recyclerView.setAdapter(adapter);                }
            }
        }
    }

    public void searchForTeachers(final String subject, final Long level, boolean zoom, boolean faceMeeting, final int price) {
        //here I am assuming that the data was collected so these are temporary values that need to be changed when the page is done
        //float maxPrice = 150;
        //.whereEqualTo(FIELD_ZOOM, false)
        final CollectionReference teacherRef = comm.db.collection(COLLECTION_TEACHER);

//.whereEqualTo(searchOptions[0], true).whereEqualTo(searchOptions[1], true).whereEqualTo(searchOptions[2], true).whereEqualTo(CITY, comm.getCity()).whereEqualTo(COUNTRY, comm.getCountry()).whereEqualTo(FIELD_LESSONS + "." + subject + "." + Globals.FIELD_NAME, subject)
        if (zoom) {
            teacherRef.whereArrayContains(FIELD_LESSON_TOPIC_LIST, subject).whereEqualTo(FIELD_ZOOM, true)
                    .whereEqualTo(COUNTRY, comm.getUserCountry())/*.whereLessThanOrEqualTo(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE, price)
                    .whereGreaterThanOrEqualTo(FIELD_LESSONS + "." + subject + "." + LEVEL, level)*/
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HashMap<String, UserLesson> maps = new HashMap<>();
                            maps = (HashMap<String, UserLesson>) document.get(FIELD_LESSONS);


                            if ((Double) document.get(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE) <= price &&
                                    ((Long) document.get(FIELD_LESSONS + "." + subject + "." + LEVEL)) == level &&
                                    !comm.getUid().equals(document.get(FIELD_UID).toString())) {


                                Double rating = (document.get(RATINGS) == null) ? 0 : Double.parseDouble(document.get(RATINGS).toString());
                                teachers.add(new SearchResultsRow((HashMap) document.get(FIELD_LESSONS), document.get(FIELD_SURNAME).toString(), document.get(CITY).toString(), rating,
                                        document.get(FIELD_UID).toString(), document.get(FIELD_NAME).toString(),
                                        (Boolean) document.get(FIELD_ZOOM), (Boolean) document.get(FIELD_TEACHERHOME),
                                        (Boolean) document.get(FIELD_STUDENTHOME),
                                        document.get(FIELD_LESSONS + "." + subject + "." + FIELD_NAME).toString(),
                                        (Double) document.get(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE),
                                        document.get(FIELD_LESSONS + "." + subject + "." + FIELD_LEVEL).toString()));
                            }
                        }
                        if (teachers.isEmpty()) {
                            emptyListView.setVisibility(View.VISIBLE);
                        } else {
                            emptyListView.setVisibility(View.GONE);
                        }
                        adapter = new SearchForTeacherAdapter(teachers, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        } else if (faceMeeting) {

            teacherRef.whereArrayContains(FIELD_LESSON_TOPIC_LIST, subject).whereEqualTo(FIELD_TEACHERHOME, true)
                    .whereEqualTo(COUNTRY, comm.getUserCountry()).whereEqualTo(CITY, comm.getUserCity())/*.whereLessThanOrEqualTo(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE, price)
                    .whereGreaterThanOrEqualTo(FIELD_LESSONS + "." + subject + "." + LEVEL, level)*/
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HashMap<String, UserLesson> maps = new HashMap<>();
                            maps = (HashMap<String, UserLesson>) document.get(FIELD_LESSONS);


                            if ((Double) document.get(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE) <= price
                                    && ((Long) document.get(FIELD_LESSONS + "." + subject + "." + LEVEL)) == level) {

                                Double rating = (document.get(RATINGS) == null) ? 0 : Double.parseDouble(document.get(RATINGS).toString());
                                teachers.add(new SearchResultsRow((HashMap) document.get(FIELD_LESSONS), document.get(FIELD_SURNAME).toString(), document.get(CITY).toString(), rating,
                                        document.get(FIELD_UID).toString(), document.get(FIELD_NAME).toString(),
                                        (Boolean) document.get(FIELD_ZOOM), (Boolean) document.get(FIELD_TEACHERHOME),
                                        (Boolean) document.get(FIELD_STUDENTHOME),
                                        document.get(FIELD_LESSONS + "." + subject + "." + FIELD_NAME).toString(),
                                        (Double) document.get(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE),
                                        document.get(FIELD_LESSONS + "." + subject + "." + FIELD_LEVEL).toString()));
                            }
                        }
                        if (teachers.isEmpty()) {
                            emptyListView.setVisibility(View.VISIBLE);
                        } else {
                            emptyListView.setVisibility(View.GONE);
                        }
                        adapter = new SearchForTeacherAdapter(teachers, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }
    }

    /*public void searchForTeachers(final String subject, String level, boolean zoom, boolean teachersPlace, boolean studentsPlace, final int price) {
        //here I am assuming that the data was collected so these are temporary values that need to be changed when the page is done
        //float maxPrice = 150;
        //.whereEqualTo(FIELD_ZOOM, false)
        CollectionReference teacherRef = comm.db.collection(COLLECTION_TEACHER);
        String[] searchOptions = new String[3];

        if (zoom) {
            searchOptions[0] = FIELD_ZOOM;

            if (teachersPlace)
                searchOptions[1] = FIELD_TEACHERHOME;
            else
                searchOptions[1] = FIELD_ZOOM;


            if (studentsPlace)
                searchOptions[2] = FIELD_STUDENTHOME;
            else
                searchOptions[2] = FIELD_ZOOM;

        } else {

            if (teachersPlace) {
                searchOptions[0] = FIELD_TEACHERHOME;
                searchOptions[1] = FIELD_TEACHERHOME;
            } else {
                searchOptions[0] = FIELD_STUDENTHOME;
                searchOptions[1] = FIELD_STUDENTHOME;
            }

            if (studentsPlace)
                searchOptions[2] = FIELD_STUDENTHOME;
            else
                searchOptions[2] = FIELD_TEACHERHOME;
        }
//.whereEqualTo(searchOptions[0], true).whereEqualTo(searchOptions[1], true).whereEqualTo(searchOptions[2], true).whereEqualTo(CITY, comm.getCity()).whereEqualTo(COUNTRY, comm.getCountry()).whereEqualTo(FIELD_LESSONS + "." + subject + "." + Globals.FIELD_NAME, subject)
        if (zoom || teachersPlace || studentsPlace && !zoom) {
            teacherRef.whereEqualTo(searchOptions[0], true).whereEqualTo(searchOptions[1], true).whereEqualTo(searchOptions[2], true)
                    .whereEqualTo(CITY, comm.getUserCity()).whereEqualTo(COUNTRY, comm.getUserCountry()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        TempStringArray1 = new String[task.getResult().size()];
                        TempStringArray2 = new String[task.getResult().size()];
                        TempStringArray3 = new String[task.getResult().size()];
                        TempRatingArray1 = new Double[task.getResult().size()];
                        TempImageArray = new Uri[task.getResult().size()];
                        TempUIDArray = new String[task.getResult().size()];
                        i = 0;

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            HashMap<String, UserLesson> maps = new HashMap<>();
                            maps = (HashMap<String, UserLesson>) document.get(FIELD_LESSONS);
                            if (maps.containsKey(subject)) {
                                if ((Double) document.get(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE) <= price) {
                                    TempStringArray1[i] = document.getString("name");
                                    TempStringArray3[i] = document.get(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE).toString();
                                    TempStringArray2[i] = document.get("city").toString();
                                    TempRatingArray1[i] = document.getDouble(Globals.FIELD_RATING);
                                    String uid = document.getString(Globals.FIELD_UID);
                                    TempUIDArray[i] = uid;
                                    comm.profileImagePicRef(uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            TempImageArray[i] = uri;
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });
                                    i += 1;
                                }
                            }
                        }
                        if (i > 0) {
                            CombineArrays();
                        }
                    }
                }

            });
        } else if (zoom || teachersPlace || studentsPlace) {
            teacherRef.whereEqualTo(searchOptions[0], true).whereEqualTo(searchOptions[1], true).whereEqualTo(searchOptions[2], true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        TempStringArray1 = new String[task.getResult().size()];
                        TempStringArray2 = new String[task.getResult().size()];
                        TempStringArray3 = new String[task.getResult().size()];
                        TempRatingArray1 = new Double[task.getResult().size()];
                        TempImageArray = new Uri[task.getResult().size()];
                        TempUIDArray = new String[task.getResult().size()];
                        i = 0;

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            HashMap<String, UserLesson> maps = new HashMap<>();
                            maps = (HashMap<String, UserLesson>) document.get(FIELD_LESSONS);
                            if (maps.containsKey(subject)) {
                                if ((Double) document.get(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE) <= price) {
                                    TempStringArray1[i] = document.getString("name");
                                    TempStringArray3[i] = document.get(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE).toString();
                                    TempStringArray2[i] = document.get("city").toString();
                                    TempRatingArray1[i] = document.getDouble(Globals.FIELD_RATING);
                                    String uid = document.getString(Globals.FIELD_UID);
                                    TempUIDArray[i] = uid;
                                    comm.profileImagePicRef(uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            TempImageArray[i] = uri;
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });
                                    i += 1;
                                }
                            }
                        }
                        if (i > 0) {
                            CombineArrays();
                        }
                    }
                }
            });
        } else {
            teacherRef.whereEqualTo(CITY, comm.getUserCity()).whereEqualTo(COUNTRY, comm.getUserCountry())
                    .whereArrayContains(Globals.LANGUAGES, "english").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        TempStringArray1 = new String[task.getResult().size()];
                        TempStringArray2 = new String[task.getResult().size()];
                        TempStringArray3 = new String[task.getResult().size()];
                        TempRatingArray1 = new Double[task.getResult().size()];
                        TempImageArray = new Uri[task.getResult().size()];
                        TempUIDArray = new String[task.getResult().size()];
                        i = 0;

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            HashMap<String, UserLesson> maps = new HashMap<>();
                            maps = (HashMap<String, UserLesson>) document.get(FIELD_LESSONS);
                            if (maps.containsKey(subject)) {
                                if ((Double) document.get(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE) <= price) {
                                    TempStringArray1[i] = document.getString("name");
                                    TempStringArray3[i] = document.get(FIELD_LESSONS + "." + subject + "." + FIELD_PRICE).toString();
                                    TempStringArray2[i] = document.get("city").toString();
                                    TempRatingArray1[i] = document.getDouble(Globals.FIELD_RATING);
                                    String uid = document.getString(Globals.FIELD_UID);
                                    TempUIDArray[i] = uid;
                                    comm.profileImagePicRef(uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            TempImageArray[i] = uri;
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });
                                    i += 1;
                                }
                            }
                        }
                        if (i > 0) {
                            CombineArrays();
                        }
                    }
                }
            });
        }

    }*/
}