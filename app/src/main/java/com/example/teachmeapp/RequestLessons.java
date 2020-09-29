package com.example.teachmeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.model.Request;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class RequestLessons extends HamburgerMenu {

    TextView textViewTeacherName;
    TextView textViewPrice;
    private TextView subject, level;


    private Button confirmRequest;

    Spinner editTextTimeStart;
    Spinner editTextTimeEnd;

    private RadioGroup typeSelection;
    RadioButton radioButtonFaceToFace;
    RadioButton radioButtonZoom;
    CalendarView calendarView;

    Timestamp startTime, endTime;
    private Calendar dater = Calendar.getInstance();

    private int currentYear, currentMonth, currentDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_lessons);

        //TODO i need data from teacher what he teachs and so on subjects,levels,prices
        textViewTeacherName = findViewById(R.id.TeacherName);
        textViewPrice = findViewById(R.id.textViewPrice);
        subject = findViewById(R.id.txtSubject);
        level = findViewById(R.id.txtLevel);

        confirmRequest = findViewById(R.id.buttonRequestLesson);

        editTextTimeStart = findViewById(R.id.editTextTimeStart);
        editTextTimeEnd = findViewById(R.id.editTextTimeEnd);
        typeSelection = findViewById(R.id.radioGroup2);
        radioButtonFaceToFace = findViewById(R.id.radioButtonFaceToFace);
        radioButtonZoom = findViewById(R.id.radioButtonZoomGetLesson);
        calendarView = findViewById(R.id.calendarView);

        setUpLessonData();

        calendarView.setDate(dater.getTime().getTime());

        startTime = new Timestamp(new java.util.Date(calendarView.getDate()));
        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        calendar.setTime(startTime.toDate());

        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, final int year, final int month, final int day) {

                if ((day < calendar.get(Calendar.DAY_OF_MONTH))
                        && ((((month + 1) > calendar.get(Calendar.MONTH) + 1) && (year >= calendar.get(Calendar.YEAR)))
                || (((month + 1) < calendar.get(Calendar.MONTH) + 1) && (year > calendar.get(Calendar.YEAR))))){

                    currentYear = year;
                    currentMonth = month + 1;
                    currentDay = day;

                } else if ((day >= calendar.get(Calendar.DAY_OF_MONTH))
                        && ((month + 1) >= calendar.get(Calendar.MONTH) + 1) && (year >= calendar.get(Calendar.YEAR))) {
                    currentYear = year;
                    currentMonth = month + 1;
                    currentDay = day;

                } else {
                    Toast.makeText(RequestLessons.this, "You can't select past date!", Toast.LENGTH_LONG).show();
                    calendarView.setDate(dater.getTime().getTime());
                }


            }
        });

        confirmRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Onclick_RequestLesson();
            }
        });
    }

    private void setUpLessonData(){

        if (Globals.getLessonBoolean){

            textViewTeacherName.setText(Globals.getLessonCurrentObj.getM_teacherName() + " " + Globals.getLessonCurrentObj.getM_surname());
            textViewPrice.setText(String.valueOf(Globals.getLessonCurrentObj.getM_price()));
            subject.setText(Globals.getLessonCurrentObj.getM_subject());
            level.setText(Globals.LEVELS_ARRAY[Integer.valueOf(Globals.getLessonCurrentObj.getM_level())]);

        } else {

            textViewTeacherName.setText(comm.getViewedUserName() + " " + comm.getViewedUserSurname());
            textViewPrice.setText(String.valueOf(Globals.getLessonCurrentObjTeacherProfile.getM_price()));
            subject.setText(Globals.getLessonCurrentObjTeacherProfile.getM_subject());
            level.setText(Globals.getLessonCurrentObjTeacherProfile.getM_level());

        }

        if (!comm.isViewedUserZoom()){
            radioButtonZoom.setVisibility(View.INVISIBLE);
        }

        if (!comm.isViewedUserTeacherHome()){
            radioButtonFaceToFace.setVisibility(View.INVISIBLE);
        }

    }


    public void Onclick_RequestLesson() {
        //TODO put data into requesting lesson from student to teacher

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        java.util.Date inTime = null, outTime = null;
        try {
            inTime = sdf.parse(editTextTimeStart.getSelectedItem().toString());
            outTime = sdf.parse(editTextTimeEnd.getSelectedItem().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (inTime.compareTo(outTime) >= 0){
            Toast.makeText(RequestLessons.this, "You must choose Start Time less than End Time!", Toast.LENGTH_LONG).show();
        } else {

            LocalDateTime lessonTimeStart = LocalDateTime.of(currentYear, currentMonth, currentDay,
                    Integer.parseInt(String.valueOf(editTextTimeStart.getSelectedItem().toString().charAt(0)) +
                            String.valueOf(editTextTimeStart.getSelectedItem().toString().charAt(1))),
                    Integer.parseInt(String.valueOf(editTextTimeStart.getSelectedItem().toString().charAt(3)) +
                            String.valueOf(editTextTimeStart.getSelectedItem().toString().charAt(4))));

            LocalDateTime lessonTimeEnd = LocalDateTime.of(currentYear, currentMonth, currentDay,
                    Integer.parseInt(String.valueOf(editTextTimeEnd.getSelectedItem().toString().toString().charAt(0)) +
                            String.valueOf(editTextTimeEnd.getSelectedItem().toString().charAt(1))),
                    Integer.parseInt(String.valueOf(editTextTimeEnd.getSelectedItem().toString().charAt(3)) +
                            String.valueOf(editTextTimeEnd.getSelectedItem().toString().charAt(4))));

            if (typeSelection.getCheckedRadioButtonId() == -1){
                Toast.makeText(RequestLessons.this, "Please select meeting's type!", Toast.LENGTH_LONG).show();
            } else {

                if (typeSelection.getCheckedRadioButtonId() == R.id.radioButtonZoomGetLesson){

                    if (Globals.getLessonBoolean){

                        Request request = new Request(Globals.comm.getUid(),
                                Globals.getLessonCurrentObj.getM_teacherUID(),
                                Globals.getLessonCurrentObj.getM_teacherName() + " " + Globals.getLessonCurrentObj.getM_surname(),
                                Globals.comm.getUserName() + " " + Globals.comm.getUserSurname(),
                                lessonTimeStart.toString(),
                                lessonTimeEnd.toString(),
                                true,
                                false,
                                Globals.getLessonCurrentObj.getM_subject(),
                                String.valueOf(Globals.getLessonCurrentObj.getM_price()),
                                Globals.LEVELS_ARRAY[Integer.valueOf(Globals.getLessonCurrentObj.getM_level())],
                                true,
                                false,
                                false,
                                lessonTimeStart.toString().split("T")[0]);

                        insertRequestIntoDatabase(request);

                    } else {

                        Request request = new Request(Globals.comm.getUid(),
                                Globals.comm.getViewedUserUID(),
                                comm.getViewedUserName() + " " + comm.getViewedUserSurname(),
                                Globals.comm.getUserName() + " " + Globals.comm.getUserSurname(),
                                lessonTimeStart.toString(),
                                lessonTimeEnd.toString(),
                                true,
                                false,
                                Globals.getLessonCurrentObjTeacherProfile.getM_subject(),
                                String.valueOf(Globals.getLessonCurrentObjTeacherProfile.getM_price()),
                                Globals.getLessonCurrentObjTeacherProfile.getM_level(),
                                true,
                                false,
                                false,
                                lessonTimeStart.toString().split("T")[0]);

                        insertRequestIntoDatabase(request);

                    }

                } else {

                    if (Globals.getLessonBoolean){

                        Request request = new Request(Globals.comm.getUid(),
                                Globals.getLessonCurrentObj.getM_teacherUID(),
                                Globals.getLessonCurrentObj.getM_teacherName() + " " + Globals.getLessonCurrentObj.getM_surname(),
                                Globals.comm.getUserName() + " " + Globals.comm.getUserSurname(),
                                lessonTimeStart.toString(),
                                lessonTimeEnd.toString(),
                                false,
                                true,
                                Globals.getLessonCurrentObj.getM_subject(),
                                String.valueOf(Globals.getLessonCurrentObj.getM_price()),
                                Globals.LEVELS_ARRAY[Integer.valueOf(Globals.getLessonCurrentObj.getM_level())],
                                true,
                                false,
                                false,
                                lessonTimeStart.toString().split("T")[0]);

                        insertRequestIntoDatabase(request);

                    } else {

                        Request request = new Request(Globals.comm.getUid(),
                                Globals.comm.getViewedUserUID(),
                                comm.getViewedUserName() + " " + comm.getViewedUserSurname(),
                                Globals.comm.getUserName() + " " + Globals.comm.getUserSurname(),
                                lessonTimeStart.toString(),
                                lessonTimeEnd.toString(),
                                false,
                                true,
                                Globals.getLessonCurrentObjTeacherProfile.getM_subject(),
                                String.valueOf(Globals.getLessonCurrentObjTeacherProfile.getM_price()),
                                Globals.getLessonCurrentObjTeacherProfile.getM_level(),
                                true,
                                false,
                                false,
                                lessonTimeStart.toString().split("T")[0]);

                        insertRequestIntoDatabase(request);

                    }

                }
            }
        }
    }

    private void insertRequestIntoDatabase(Request request){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Request").document()
                .set(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RequestLessons.this, "Request is received Successfully.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RequestLessons.this, "Error with submitting your request. Try again!", Toast.LENGTH_LONG).show();
                    }
                });

    }
}