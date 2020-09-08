package com.example.teachmeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.CollectionReference;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class SearchForTeacher extends HamburgerMenu {

    SearchView searchView;
    private EditText editTextKeyword;
    private ChipGroup chipGroup;
    private Button buttonAdd;
    private Button buttonShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_teacher);


        this.editTextKeyword = (EditText) this.findViewById(R.id.editText_keyword);
        this.chipGroup = (ChipGroup) this.findViewById(R.id.chipGroup);
        this.buttonShow = (Button) this.findViewById(R.id.button_show);

        this.buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelections();
            }
        });

    }

    public void searchForTeachers() {
        //here I am assuming that the data was collected so these are temporary values that need to be changed when the page is done
        String subject = "math";
        float maxPrice = 150;
        Boolean zoom = false;

        CollectionReference teacherRef = comm.db.collection(COLLECTION_TEACHER);
        teacherRef.whereArrayContains(FIELD_LESSONS, subject).whereEqualTo(FIELD_ZOOM, false);


    }

    private void addNewChip(String keyword) { // TODO add chips from data base

        try {
            LayoutInflater inflater = LayoutInflater.from(this);
            Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip_entry, this.chipGroup, false);
            newChip.setText(keyword);

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

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showSelections() {
        int count = this.chipGroup.getChildCount();

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
        }
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
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

}