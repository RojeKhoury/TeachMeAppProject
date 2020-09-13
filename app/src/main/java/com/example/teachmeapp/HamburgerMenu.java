package com.example.teachmeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachmeapp.Adapter.AdapterCardViewList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.teachmeapp.Helpers.Globals.LESSONS_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.SEARCH_FOR_TEACHER_VIEW;
import static com.example.teachmeapp.Helpers.Globals.SEARCH_RESULT;
import static com.example.teachmeapp.Helpers.Globals.comm;

public class HamburgerMenu extends Activity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docref;

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

    public void CallViewAdapter(int RecyclerViewName, Context ct, String s1[], String s2[], String s3[], Button b1, Uri i1[], double r1[]) {
        AdapterCardViewList adapterCardViewList = null;
        RecyclerView recyclerView = null;

        switch (RecyclerViewName) {
            case SEARCH_RESULT:
                recyclerView = findViewById(R.id.recyclerView);
                adapterCardViewList = new AdapterCardViewList(SEARCH_RESULT, ct, s1, s2, s3, b1, null, null);
                break;
            case SEARCH_FOR_TEACHER_VIEW:
                recyclerView = findViewById(R.id.recyclerViewSearchResult);

                adapterCardViewList = new AdapterCardViewList(SEARCH_FOR_TEACHER_VIEW, ct, s1, s2, s3, null, i1, r1);
                break;
            case LESSONS_FOR_TEACHER_VIEW:
                recyclerView = findViewById(R.id.recyclerView_MyLessons);

                adapterCardViewList = new AdapterCardViewList(LESSONS_FOR_TEACHER_VIEW, ct, s1, s2, s3, b1, null, null);
                break;
            default:
                break;

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterCardViewList);
    }
}