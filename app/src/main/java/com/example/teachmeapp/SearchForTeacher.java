package com.example.teachmeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.Helpers.Lesson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_STUDENTHOME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_TEACHERHOME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import static com.example.teachmeapp.Helpers.Globals.SEARCH_FOR_TEACHER_VIEW;

public class SearchForTeacher extends HamburgerMenu {

    private EditText editTextKeyword;
    private ChipGroup chipGroup;
    private Button buttonAdd;
    private Button buttonShow;


    boolean foundTeacherFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_teacher);

        this.editTextKeyword = (EditText) this.findViewById(R.id.editText_keyword);
        this.chipGroup = (ChipGroup) this.findViewById(R.id.chipGroup);
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

                this.chipGroup.addView(newChip);

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
                ChipTagSearchedArray[i] = s;
            }
            if (TempStringArray1 != null && TempStringArray1.length > 0) {
                CallViewAdapter(SEARCH_FOR_TEACHER_VIEW);
            } else {
                Toast.makeText(this, "Sorry No Teachers Found", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please add and pick a Tag", Toast.LENGTH_LONG).show();

        }
    }
}