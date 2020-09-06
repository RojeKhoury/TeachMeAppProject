package com.example.teachmeapp.Helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Teacher {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private ArrayList<Integer> rating;
    private Map<String ,UserLesson> classes;
    private List<Comment> comments;
    private boolean zoom;
    private boolean studentHome;
    private boolean teacherHome;
    private String bio;

    public Teacher() {
    }

    public Teacher(String name, String surname, String phone, ArrayList<Integer> rating, List<UserLesson> classes, List<Comment> comments, String email) {
        // [START_EXCLUDE]
        Map<String, UserLesson> temp = new HashMap<String, UserLesson>();

        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.rating = rating;
        this.classes = temp;
        this.comments = comments;
        this.email = email;
        this.zoom = true;
        this.studentHome = false;
        this.teacherHome = false;
        this.bio = "this my bio";
        // [END_EXCLUDE]
    }

    public String getName() {
        return name;
    }

    public boolean getZoom() {
        return zoom;
    }

    public boolean getStudentHome() {
        return studentHome;
    }

    public boolean getTeacherHome() {
        return teacherHome;
    }

    public String getBio() {
        return bio;
    }

    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public ArrayList<Integer> getRating() {
        return rating;
    }

    public Map<String ,UserLesson> getLessons() {
        return classes;
    }

}
