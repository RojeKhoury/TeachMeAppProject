package com.example.teachmeapp;

import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

        if (comm.isTeacher()){
            popup.getMenu().removeItem(R.id.HamburgerMenuHomePageStudent);
            if (this.getLocalClassName().equals(HomePageTeacher.class.getSimpleName())) {
                popup.getMenu().removeItem(R.id.HamburgerMenuHomePageTeacher);
            } else if (this.getLocalClassName().equals(ProfilePageOfTeacherForStudent.class.getSimpleName())) {
                popup.getMenu().removeItem(R.id.HamburgerMenuProfilePage);
//            } else if (this.getLocalClassName().equals(HamburgerMenuUpdateInfo.class.getSimpleName())) {
//                popup.getMenu().removeItem(R.id.HamburgerMenuUpdateInfo);
            } else if (this.getLocalClassName().equals(HomePageStudent.class.getSimpleName())) {
                popup.getMenu().removeItem(R.id.HamburgerMenuProfilePage);
            }else if (this.getLocalClassName().equals(EditTeacherInfo.class.getSimpleName())) {
                popup.getMenu().removeItem(R.id.HamburgerMenuUpdateInfo);
            }
        }else{
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
                intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                comm.signOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}