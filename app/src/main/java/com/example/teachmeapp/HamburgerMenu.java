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

        if (this.getLocalClassName().equals(HomePageTeacher.class.getSimpleName())) {
            popup.getMenu().removeItem(R.id.HamburgerMenuHomePage);
        } else if (this.getLocalClassName().equals(ProfilePageOfTeacherForStudent.class.getSimpleName())) {
            popup.getMenu().removeItem(R.id.HamburgerMenuProfilePage);
        } else if (this.getLocalClassName().equals(ProfilePageOfTeacherForStudent.class.getSimpleName())) {
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
            case R.id.HamburgerMenuHomePage:
                intent = new Intent(getApplicationContext(), HomePageStudent.class);
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