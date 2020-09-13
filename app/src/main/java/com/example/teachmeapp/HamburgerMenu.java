package com.example.teachmeapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AdapterCardViewList;
import com.example.teachmeapp.Helpers.Globals;
import com.example.teachmeapp.Helpers.Lesson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static com.example.teachmeapp.Helpers.Globals.COLLECTION_TEACHER;
import static com.example.teachmeapp.Helpers.Globals.FIELD_LESSONS;
import static com.example.teachmeapp.Helpers.Globals.FIELD_STUDENTHOME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_TEACHERHOME;
import static com.example.teachmeapp.Helpers.Globals.FIELD_ZOOM;
import static com.example.teachmeapp.Helpers.Globals.LESSONS_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.SEARCH_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class HamburgerMenu extends Activity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docref;
    String TempStringArray1[];
    String TempStringArray2[];
    String TempStringArray3[];
    Double TempRatingArray1[] = {};

    String ChipTagSearchedArray[];

    Uri TempImageArray[];

    ArrayList<String> arrayListString1 = new ArrayList<>();
    ArrayList<String> arrayListString2 = new ArrayList<>();
    ArrayList<String> arrayListString3 = new ArrayList<>();
    ArrayList<Uri> arrayListUri1 = new ArrayList<>();
    ArrayList<Double> arrayListDouble1 = new ArrayList<>();

    @Override
    public void onCreateContextMenu(@Nullable ContextMenu menu, @Nullable View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.hamburger_menu, menu);


    }

    public void HamburgerMenuOpen(@Nullable View v) {

        final PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.hamburger_menu, popup.getMenu());

        if (comm.isTeacher()) {
            popup.getMenu().removeItem(R.id.HamburgerMenuHomePageStudent);
            if (this.getLocalClassName().equals(HomePageTeacher.class.getSimpleName())) {
                popup.getMenu().removeItem(R.id.HamburgerMenuHomePageTeacher);
            } else if (this.getLocalClassName().equals(ProfilePageOfTeacherForStudent.class.getSimpleName())) {
                popup.getMenu().removeItem(R.id.HamburgerMenuProfilePage);
//            } else if (this.getLocalClassName().equals(HamburgerMenuUpdateInfo.class.getSimpleName())) {
//                popup.getMenu().removeItem(R.id.HamburgerMenuUpdateInfo);
            } else if (this.getLocalClassName().equals(HomePageStudent.class.getSimpleName())) {
                popup.getMenu().removeItem(R.id.HamburgerMenuProfilePage);
            } else if (this.getLocalClassName().equals(EditTeacherInfo.class.getSimpleName())) {
                popup.getMenu().removeItem(R.id.HamburgerMenuUpdateInfo);
            }
        } else {
            popup.getMenu().removeItem(R.id.HamburgerMenuUpdateInfo);
            popup.getMenu().removeItem(R.id.HamburgerMenuHomePageTeacher);
            popup.getMenu().removeItem(R.id.HamburgerMenuUpdateInfo);
            if (this.getLocalClassName().equals(HomePageStudent.class.getSimpleName())) {
                popup.getMenu().removeItem(R.id.HamburgerMenuHomePageStudent);
            } else if (this.getLocalClassName().equals(ProfilePageOfTeacherForStudent.class.getSimpleName())) {
                popup.getMenu().removeItem(R.id.HamburgerMenuProfilePage);
            }
        }
        if (this.getLocalClassName().equals(Schedule.class.getSimpleName())) {
            popup.getMenu().removeItem(R.id.HamburgerMenuSchedule);
        }
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                HamburgerMenu.this.onOptionsItemSelected(item);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@Nullable MenuItem item) {
        Intent intent;
        assert item != null;
        switch (item.getItemId()) {
            case R.id.HamburgerMenuHomePageStudent:
                intent = new Intent(getApplicationContext(), HomePageStudent.class);
                startActivity(intent);
                return true;
            case R.id.HamburgerMenuHomePageTeacher:
                intent = new Intent(getApplicationContext(), HomePageTeacher.class);
                startActivity(intent);
                return true;
            case R.id.HamburgerMenuUpdateInfo:
                intent = new Intent(getApplicationContext(), EditTeacherInfo.class);
                startActivity(intent);
                return true;
            case R.id.HamburgerMenuProfilePage:
                intent = new Intent(getApplicationContext(), ProfilePageOfTeacherForStudent.class);
                startActivity(intent);
                return true;
            case R.id.HamburgerMenuSchedule:
                intent = new Intent(getApplicationContext(), Schedule.class);
                startActivity(intent);
                return true;
            case R.id.HamburgerMenuLogOut:
                comm.signOut();
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void CallViewAdapter(int RecyclerViewName) {
        AdapterCardViewList adapterCardViewList = null;
        RecyclerView recyclerView = null;

        arrayListString1.clear();
        arrayListString2.clear();
        arrayListString3.clear();
        arrayListUri1.clear();
        arrayListDouble1.clear();

        switch (RecyclerViewName) {
            case SEARCH_RESULT:
                recyclerView = findViewById(R.id.recyclerView);
                //TODO do a search for results from database
                adapterCardViewList = new AdapterCardViewList(SEARCH_RESULT, this, arrayListString1, arrayListString2, arrayListString3,
                        null, null);
                break;
            case SEARCH_FOR_TEACHER_VIEW:
                recyclerView = findViewById(R.id.recyclerViewSearchResult);

                CheckBox zoom = findViewById(R.id.checkbox_zoom);
                CheckBox teacherPlace = findViewById(R.id.checkbox_at_teacher_place);
                CheckBox studentPlace = findViewById(R.id.checkbox_at_student_place);
                Spinner spinner = findViewById(R.id.spinner_for_education_level);
                String EducationLevel = spinner.getContext().toString();

                for (int i = 0; i < ChipTagSearchedArray.length; i++) {
                    searchForTeachers(ChipTagSearchedArray[i], EducationLevel, zoom.isChecked(), teacherPlace.isChecked(), studentPlace.isChecked(), 1000);
                }

                adapterCardViewList = new AdapterCardViewList(SEARCH_FOR_TEACHER_VIEW, this, arrayListString1, arrayListString2, arrayListString3,
                        arrayListUri1, arrayListDouble1);
                break;
            case LESSONS_FOR_TEACHER_VIEW:
                recyclerView = findViewById(R.id.recyclerView_MyLessons);
                //TODO do a list of teachers from database

                adapterCardViewList = new AdapterCardViewList(LESSONS_FOR_TEACHER_VIEW, this, arrayListString1, arrayListString2, arrayListString3,
                        null, null);
                break;
            default:
                break;

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterCardViewList);
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

                        TempStringArray1 = new String[task.getResult().size()];
                        TempStringArray2 = new String[task.getResult().size()];
                        TempStringArray3 = new String[task.getResult().size()];
                        TempRatingArray1 = new Double[task.getResult().size()];
                        TempImageArray = new Uri[task.getResult().size()];
                        int i = 0;

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            ArrayList<HashMap<String, Lesson>> maps = new ArrayList<>();
                            maps = (ArrayList<HashMap<String, Lesson>>) document.get(FIELD_LESSONS);

                            for (Object map : maps.toArray()) {

                                if (((HashMap) map).containsKey(subject) && ((float) ((HashMap) ((HashMap) map).get(subject)).get("price")) <= price) {

                                    TempStringArray1[i] = document.getString("name");
                                    TempStringArray3[i] = ((HashMap) ((HashMap) map).get(subject)).get("price").toString();
                                    TempStringArray2[i] = ((HashMap) map).get("city").toString();
                                    TempRatingArray1[i] = document.getDouble(Globals.FIELD_RATING);
                                    String uid = document.getString(Globals.FIELD_UID);
                                    TempImageArray[i] = comm.profileImagePicRef(uid).getDownloadUrl().getResult();
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

                        TempStringArray1 = new String[task.getResult().size()];
                        TempStringArray2 = new String[task.getResult().size()];
                        TempStringArray3 = new String[task.getResult().size()];
                        TempRatingArray1 = new Double[task.getResult().size()];
                        TempImageArray = new Uri[task.getResult().size()];
                        int i = 0;

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            ArrayList<HashMap<String, Lesson>> maps = new ArrayList<>();
                            maps = (ArrayList<HashMap<String, Lesson>>) document.get(FIELD_LESSONS);

                            for (Object map : maps.toArray()) {

                                if (((HashMap) map).containsKey(subject) && ((float) ((HashMap) ((HashMap) map).get(subject)).get("price")) <= price) {

                                    TempStringArray1[i] = document.getString("name");
                                    TempStringArray3[i] = ((HashMap) ((HashMap) map).get(subject)).get("price").toString();
                                    TempStringArray2[i] = ((HashMap) map).get("city").toString();
                                    TempRatingArray1[i] = document.getDouble(Globals.FIELD_RATING);
                                    String uid = document.getString(Globals.FIELD_UID);
                                    TempImageArray[i] = comm.profileImagePicRef(uid).getDownloadUrl().getResult();
                                    i += 1;
                                    break;
                                }
                            }

                        }
                    }
                }
            });
        }
        //TODO add a flag if found any teachers or no so not to add empty arrays
        Collections.addAll(arrayListString1, TempStringArray1);
        Collections.addAll(arrayListString2, TempStringArray2);
        Collections.addAll(arrayListString3, TempStringArray3);
        Collections.addAll(arrayListUri1, TempImageArray);
        Collections.addAll(arrayListDouble1, TempRatingArray1);
    }

}