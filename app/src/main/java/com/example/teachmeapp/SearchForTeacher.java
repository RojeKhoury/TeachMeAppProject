package com.example.teachmeapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AdapterCardViewList;
import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.Helpers.Lesson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ThrowOnExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_STUDENTHOME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_TEACHERHOME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.example.teachmeapp.Helpers.Globals.SEARCH_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class SearchForTeacher extends HamburgerMenu {

    private EditText editTextKeyword;
    private ChipGroup chipGroup;
    private Button buttonAdd;
    private Button buttonShow;

    String s1[];
    String s2[];
    String s3[];
    double r1[];

    Uri images[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_teacher);


        this.editTextKeyword = (EditText) this.findViewById(R.id.editText_keyword);
        this.chipGroup = (ChipGroup) this.findViewById(R.id.chipGroup);


    }

    public void searchForTeachers(final String subject, String level, boolean zoom, boolean teachersPlace, boolean studentsPlace, final int price) {
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

        if (zoom || teachersPlace || studentsPlace) {
            teacherRef.whereEqualTo(searchOptions[0], true).whereEqualTo(searchOptions[1], true).whereEqualTo(searchOptions[2], true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        s1 = new String[task.getResult().size()];
                        s2 = new String[task.getResult().size()];
                        s3 = new String[task.getResult().size()];
                        r1 = new double[task.getResult().size()];
                        images = new Uri[task.getResult().size()];
                        int i = 0;

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            ArrayList<HashMap<String, Lesson>> maps = new ArrayList<>();
                            maps = (ArrayList<HashMap<String, Lesson>>) document.get(FIELD_LESSONS);

                            for (Object map : maps.toArray()) {

                                if (((HashMap) map).containsKey(subject) && ((float) ((HashMap) ((HashMap) map).get(subject)).get("price")) <= price) {

                                    s1[i] = document.getString("name");
                                    s3[i] = ((HashMap) ((HashMap) map).get(subject)).get("price").toString();
                                    s2[i] = ((HashMap) map).get("city").toString();
                                    r1[i] = document.getDouble(Globals.FIELD_RATING);
                                    String uid = document.getString(Globals.FIELD_UID);
                                    images[i] = comm.profileImagePicRef(uid).getDownloadUrl().getResult();
                                    i += 1;
                                    break;
                                }
                            }

                        }
                    }
                }
            });
        } else {
            teacherRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        s1 = new String[task.getResult().size()];
                        s2 = new String[task.getResult().size()];
                        s3 = new String[task.getResult().size()];
                        r1 = new double[task.getResult().size()];
                        images = new Uri[task.getResult().size()];
                        int i = 0;

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            ArrayList<HashMap<String, Lesson>> maps = new ArrayList<>();
                            maps = (ArrayList<HashMap<String, Lesson>>) document.get(FIELD_LESSONS);

                            for (Object map : maps.toArray()) {

                                if (((HashMap) map).containsKey(subject) && ((float) ((HashMap) ((HashMap) map).get(subject)).get("price")) <= price) {

                                    s1[i] = document.getString("name");
                                    s3[i] = ((HashMap) ((HashMap) map).get(subject)).get("price").toString();
                                    s2[i] = ((HashMap) map).get("city").toString();
                                    r1[i] = document.getDouble(Globals.FIELD_RATING);
                                    String uid = document.getString(Globals.FIELD_UID);
                                    images[i] = comm.profileImagePicRef(uid).getDownloadUrl().getResult();
                                    i += 1;
                                    break;
                                }
                            }

                        }
                    }
                }
            });
        }

    }

    // User close a Chip.
    private void handleChipCloseIconClicked(Chip chip) {
        ChipGroup parent = (ChipGroup) chip.getParent();
        parent.removeView(chip);
    }

    // Chip Checked Changed
    private void handleChipCheckChanged(Chip chip, boolean isChecked) {
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

    public void searchForResults(String search) {
        //here make an api call to get the results, complete the code here

    }

    //just call this when you get the search result from the api with your custom model and where ever it is applicable.


    public void OnClick_SearchForTeacher_CheckBox_AtStudentPlace(View view) {
    }

    public void OnClick_SearchForTeacher_CheckBox_AtTeacherPlace(View view) {
    }

    public void OnClick_SearchForTeacher_CheckBox_Zoom(View view) {
    }


    public void addNewChip(View view) {
        try {
            LayoutInflater inflater = LayoutInflater.from(this);
            Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip_entry, this.chipGroup, false);
            EditText editText = findViewById(R.id.editText_keyword);
            String tag = editText.getText().toString();
            if (tag.trim().isEmpty()) {
                Toast.makeText(this, "Add a tag first", Toast.LENGTH_SHORT).show();
            } else {
                newChip.setText(tag);


                //
                // Other methods:
                //
                // newChip.setCloseIconVisible(true);
                // newChip.setCloseIconResource(R.drawable.your_icon);
                // newChip.setChipIconResource(R.drawable.your_icon);
                // newChip.setChipBackgroundColorResource(R.color.red);
                // newChip.setTextAppearanceResource(R.style.ChipTextStyle);
                // newChip.setElevation(15);

                this.chipGroup.addView(newChip);

                // Set Listener for the Chip:
                newChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        handleChipCheckChanged((Chip) buttonView, isChecked);
                    }
                });

                newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleChipCloseIconClicked((Chip) v);
                    }
                });
                this.editTextKeyword.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void OnClick_SearchForTeacher_Button_ShowSelections(View view) {
        CheckBox zoom = findViewById(R.id.checkbox_zoom);
        CheckBox teacherPlace = findViewById(R.id.checkbox_at_teacher_place);
        CheckBox studentPlace = findViewById(R.id.checkbox_at_student_place);
        Spinner spinner = findViewById(R.id.spinner_for_education_level);
        String EducationLevel = spinner.getContext().toString();

        int count = this.chipGroup.getChildCount();
        if (count > 0) {
            String s = null;
            for (int i = 0; i < count; i++) {
                Chip child = (Chip) this.chipGroup.getChildAt(i);

                if (!child.isChecked()) {
                    continue;
                }

                if (s == null) {
                    s = child.getText().toString();
                } else {
                    s += ", " + child.getText().toString();
                }


                searchForTeachers(s, EducationLevel, zoom.isChecked(), teacherPlace.isChecked(), studentPlace.isChecked(), 1000);

                if(s1!=null&&s1.length>0){
                    CallViewAdapter(SEARCH_FOR_TEACHER_VIEW, this, s1, s2, s3, null, images, r1);
                }
                else {
                    Toast.makeText(this, "Sorry No Teachers Found", Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            Toast.makeText(this, "Please add and pick a Tag", Toast.LENGTH_LONG).show();

        }


    }
}