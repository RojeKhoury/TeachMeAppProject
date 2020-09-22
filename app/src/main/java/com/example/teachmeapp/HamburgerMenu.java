package com.example.teachmeapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.teachmeapp.Helpers.Globals.comm;

public class HamburgerMenu extends Activity {


    public String[] TempStringArray1;
    public String[] TempStringArray2;
    public String[] TempStringArray3;
    public String[] TempStringArray4;
    public Double[] TempRatingArray1;
    public String[] TempUIDArray;

    public int ChipTagSearchedArraySize;
    String[] ChipTagSearchedArray;

    public Uri[] TempImageArray;
    public String SingleUID;

    public ArrayList<String> arrayListString1;
    public ArrayList<String> arrayListString2;
    public ArrayList<String> arrayListString3;
    public ArrayList<String> arrayListString4;
    public ArrayList<Uri> arrayListUri1;
    public ArrayList<Double> arrayListDouble1;
    public ArrayList<String> arrayListUID;

    public HamburgerMenu() {
        TempStringArray1 = new String[0];
        TempStringArray2 = new String[0];
        TempStringArray3 = new String[0];
        TempStringArray4 = new String[0];
        ChipTagSearchedArray = new String[ChipTagSearchedArraySize];

        TempRatingArray1 = new Double[0];
        TempImageArray = new Uri[0];
        TempUIDArray = new String[0];

        arrayListString1 = new ArrayList<>();
        arrayListString2 = new ArrayList<>();
        arrayListString3 = new ArrayList<>();
        arrayListString4 = new ArrayList<>();
        arrayListUri1 = new ArrayList<>();
        arrayListDouble1 = new ArrayList<>();
        arrayListUID = new ArrayList<>();

    }

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
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finishAndRemoveTask();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void CallViewAdapter(int RecyclerViewName) {
//        AdapterCardViewList adapterCardViewList = null;
//        RecyclerView recyclerView = null;
//
//        switch (RecyclerViewName) {
//            case SEARCH_RESULT_FOR_SCHDULE:
//                recyclerView = findViewById(R.id.recyclerViewSearchResult);
//                if (arrayListString1.isEmpty()) {
//                    Toast.makeText(this, "No Schedules Appointed", Toast.LENGTH_SHORT).show();
//                } else {
//                    adapterCardViewList = new AdapterCardViewList(RecyclerViewName, this, arrayListString1, arrayListString2, arrayListString3,
//                            null, null, null, null);
//                }
//                break;
//
//            case SEARCH_FOR_TEACHER_VIEW:/*
//                if (ChipTagSearchedArray.length > 0) {
//                    ClearArrays();
//                    recyclerView = findViewById(R.id.recyclerViewSearchResult);
//                    CheckBox zoom = findViewById(R.id.checkbox_zoom);
//                    CheckBox teacherPlace = findViewById(R.id.checkbox_at_teacher_place);
//                    CheckBox studentPlace = findViewById(R.id.checkbox_at_student_place);
//                    Spinner spinner = findViewById(R.id.spinner_for_education_level);
//                    String EducationLevel = spinner.getSelectedItem().toString();
//
//                    if (arrayListString1.isEmpty()) {
//                        Toast.makeText(this, "No Teachers Found", Toast.LENGTH_SHORT).show();
//                    } else {
//                        adapterCardViewList = new AdapterCardViewList(RecyclerViewName, this, arrayListString1, arrayListString2, arrayListString3,
//                                null, arrayListUri1, arrayListDouble1, arrayListUID);
//                    }
//
//                }    */
//                break;
//
//           /* case LESSONS_FOR_TEACHER_VIEW:
//                recyclerView = findViewById(R.id.recyclerView_MyLessons);
//
//                ClearArrays();
//                ArrayList<UserLesson> lessons = comm.MapToArray(comm.getUserLessons());
//                if (lessons != null) {
//                    i = 0;
//                    for (UserLesson lesson : lessons) {
//
//                        TempStringArray1[i] = lesson.getName();
//                        TempStringArray2[i] = lesson.getPrice().toString();
//                        TempStringArray3[i] = lesson.getlevel();
//                        i += 1;
//                    }
//                } else {
//                    Toast.makeText(this, "No lessons yet", Toast.LENGTH_SHORT).show();
//                }
//
//                CombineArrays();
//
//                if (arrayListString1.isEmpty()) {
//                    Toast.makeText(this, "Add Lessons Please", Toast.LENGTH_SHORT).show();
//                } else {
//                    adapterCardViewList = new AdapterCardViewList(RecyclerViewName, this, arrayListString1, arrayListString2, arrayListString3,
//                            null, null, null, null);
//                }
//                break;*/
//
//            case HISTORY_OF_LESSONS_VIEW:
//                recyclerView = findViewById(R.id.recyclerViewHistory);
//                //TODO do a list of History from database implimented in schedule (may remove)
//                if (arrayListString1.isEmpty()) {
//                    Toast.makeText(this, "Add Lessons Please", Toast.LENGTH_SHORT).show();
//                } else {
//                    adapterCardViewList = new AdapterCardViewList(RecyclerViewName, this, arrayListString1, arrayListString2, arrayListString3,
//                            null, null, null, null);
//                }
//                break;
//            case TEACHER_PENDING_REQUESTS_VIEW:
//                recyclerView = findViewById(R.id.recyclerViewPendingRequestTeacher);
//                les = comm.MapToArrayBookedLessons(comm.getUserPendingLessons().getLessons());
//                ClearArrays();
//                //TODO @abed I gave you the pending requests in les
//                for(BookedLesson lesson : les)
//                {
//                    lesson.getLesson().getName();
//                }
//                CombineArrays();
//                if (arrayListString1.isEmpty()) {
//                    Toast.makeText(this, "No pending request", Toast.LENGTH_SHORT).show();
//                } else {
//                    adapterCardViewList = new AdapterCardViewList(RecyclerViewName, this, arrayListString1, arrayListString2, arrayListString3,
//                            null, null, null, arrayListUID);
//                }
//                break;
//            case STUDENT_PENDING_REQUESTS_VIEW://TODO @abed I gave you the pending requests in les
//                les = comm.MapToArrayBookedLessons(comm.getUserPendingLessons().getLessons());
//                for(BookedLesson lesson : les)
//                {
//                    lesson.getLesson().getName();
//                }
//                recyclerView = findViewById(R.id.recyclerViewPendingRequestStudent);
//                if (arrayListString1.isEmpty()) {
//                    Toast.makeText(this, "No pending request", Toast.LENGTH_SHORT).show();
//                } else {
//                    adapterCardViewList = new AdapterCardViewList(RecyclerViewName, this, arrayListString1, arrayListString2, arrayListString3,
//                            arrayListString4, null, null, null);
//                }
//                break;
//            case PROFILE_PAGE_OF_SPECIFIC_TEACHER:
//                recyclerView = findViewById(R.id.Recycler_View_TeacherProfile_LessonsOffered);
//
//                ClearArrays();
//                comm.getViewedUserData(SingleUID, !comm.isTeacher());
//                Map<String, UserLesson> temp = comm.getViewedUserLessons();
//
//                for (Map.Entry lesson : temp.entrySet()) {
//                    TempStringArray1[i] = ((UserLesson) lesson.getValue()).getName();
//                    TempStringArray2[i] = ((UserLesson) lesson.getValue()).getPrice().toString();
//                    TempStringArray3[i] = ((UserLesson) lesson.getValue()).getlevel();
//                }
//
//                CombineArrays();
//
//                if (arrayListString1.isEmpty()) {
//                    Toast.makeText(this, "No lessons offered", Toast.LENGTH_SHORT).show();
//                } else {
//                    adapterCardViewList = new AdapterCardViewList(RecyclerViewName, this, arrayListString1, arrayListString2, arrayListString3,
//                            null, null, null, null);
//                }
//                break;
//
//            default:
//                break;
//
//        }
//        if (!arrayListString1.isEmpty()) {
//            assert recyclerView != null;
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(adapterCardViewList);
//        }
//
    }


    public void CombineArrays() {
        Collections.addAll(arrayListString1, TempStringArray1);
        Collections.addAll(arrayListString2, TempStringArray2);
        Collections.addAll(arrayListString3, TempStringArray3);
        Collections.addAll(arrayListString4, TempStringArray4);
        Collections.addAll(arrayListUri1, TempImageArray);
        Collections.addAll(arrayListDouble1, TempRatingArray1);
        Collections.addAll(arrayListUID, TempUIDArray);

    }
//
//    public void ClearArrays() {
//        arrayListString1.clear();
//        arrayListString2.clear();
//        arrayListString3.clear();
//        arrayListString4.clear();
//        arrayListUri1.clear();
//        arrayListDouble1.clear();
//    }
}