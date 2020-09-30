package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherInfoPage extends AppCompatActivity {

    boolean EnableChangesFlag = false;
    boolean ApplyChangesFlag = false;
    ImageView imageView;
    RadioButton radioButtonStudentPlace;
    RadioButton radioButtonTeacherPlace;
    RadioButton radioButtonZoom;
    EditText editTextName;
    EditText editTextTextPersonName;
    EditText editTextTextEmailAddress;
    EditText editTextPhone;
    EditText editTextCity;
    EditText editTextCountry;
    EditText editTextBiography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info_page);

        imageView = findViewById(R.id.imageView);
        radioButtonStudentPlace = findViewById(R.id.radioButtonStudentPlaceMyInfo);
        radioButtonTeacherPlace = findViewById(R.id.radioButtonTeacherPlaceMyInfo);
        radioButtonZoom = findViewById(R.id.radioButtonZoomMyInfo);
        editTextName = findViewById(R.id.editTextName);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextCity = findViewById(R.id.editTextCity);
        editTextCountry = findViewById(R.id.editTextCountry);
        editTextBiography = findViewById(R.id.editTextBiography);
    }

    public void ChangePicture(View view) {
    }

    public void ChangeLocation(View view) {
    }

    public void EnableChangesAndApplyThem(View view) {
        if (EnableChangesFlag) {
            if (ApplyChangesFlag){
                imageView.getDrawable();
                radioButtonStudentPlace.isChecked();
                radioButtonTeacherPlace.isChecked();
                radioButtonZoom.isChecked();
                editTextName.getText();
                editTextTextPersonName.getText();
                editTextTextEmailAddress.getText();
                editTextPhone.getText();
                editTextCity.getText();
                editTextCountry.getText();
                editTextBiography.getText();
            }
            EnableChangesFlag = false;
        } else {
            EnableChangesFlag = true;
        }
        radioButtonStudentPlace.setEnabled(EnableChangesFlag);
        radioButtonTeacherPlace.setEnabled(EnableChangesFlag);
        radioButtonZoom.setEnabled(EnableChangesFlag);
        editTextName.setEnabled(EnableChangesFlag);
        editTextTextPersonName.setEnabled(EnableChangesFlag);
        editTextTextEmailAddress.setEnabled(EnableChangesFlag);
        editTextPhone.setEnabled(EnableChangesFlag);
        editTextCity.setEnabled(EnableChangesFlag);
        editTextCountry.setEnabled(EnableChangesFlag);
        editTextBiography.setEnabled(EnableChangesFlag);
        ApplyChangesFlag = true;
    }
}