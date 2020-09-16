package com.example.teachmeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.teachmeapp.Helpers.Globals;

import static com.example.teachmeapp.Helpers.Globals.LESSONS_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class TeacherLessonsAddOrRemove extends HamburgerMenu {

    EditText SubjectEditText;
    EditText PriceEditText;
    Spinner EducationSpinner;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lessons_add_or_remove);
        SubjectEditText = findViewById(R.id.EditTeacherLessonsSubject);
        PriceEditText = findViewById(R.id.EditTeacherLessonsPrice);
        EducationSpinner = findViewById(R.id.SpinnerTeacherLessonsEducationLevel);

        CallViewAdapter(LESSONS_FOR_TEACHER_VIEW);
    }

    public void OnClick_add_lessons_button(View view) {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(TeacherLessonsAddOrRemove.this);

        Context context = getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

// Add a TextView here for the "Title" label, as noted in the comments
        final EditText subjectBox = new EditText(context);
        subjectBox.setHint("Subject");
        layout.addView(subjectBox); // Notice this is an add method

// Add another TextView here for the "Description" label
        final EditText priceBox = new EditText(context);
        priceBox.setHint("Price");
        priceBox.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(priceBox); // Another add method

        final Spinner levelBox = new Spinner(context);
        String res;
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.EducationLevel, android.R.layout.simple_spinner_item);
        levelBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                level = (String) adapterView.getItemAtPosition(i);
            }
        });
        levelBox.setAdapter(adapter);
        layout.addView(levelBox);

        builder.setPositiveButton("Add lesson", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                comm.addCourse(subjectBox.getText().toString(), comm.getUid(), Float.parseFloat(priceBox.getText().toString()), level);
                Toast.makeText(TeacherLessonsAddOrRemove.this, "Lesson was added", Toast.LENGTH_LONG).show();
            }

        });
        builder.setNegativeButton("Dont add lesson", null);

        builder.setView(layout); // Again this is a set method, not add

        builder.show();
*/


        String subject = SubjectEditText.getText().toString();
        String price = PriceEditText.getText().toString();
        level = EducationSpinner.getSelectedItem().toString();
        if (subject!=null){
            if(price!=null){
                comm.addCourse(subject, comm.getUid(), Double.parseDouble(price), level);
            }else{
                Toast.makeText(this, "Please add a Price in $", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please add a Subject", Toast.LENGTH_SHORT).show();
        }
    }
}