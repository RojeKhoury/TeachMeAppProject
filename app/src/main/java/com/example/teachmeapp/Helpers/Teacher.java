package com.example.teachmeapp.Helpers;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private ArrayList<Integer> rating;
    private List<UserLesson> classes;
    private List<Comment> comments;
    private String profilePic;

    public Teacher() {
    }

    public Teacher(String name, String surname, String phone, ArrayList<Integer> rating, List<UserLesson> classes, List<Comment> comments, String picture, String email) {
        // [START_EXCLUDE]
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.rating = rating;
        this.classes = classes;
        this.profilePic = picture;
        this.comments = comments;
        this.email = email;
        // [END_EXCLUDE]
    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public String getpicture() {
        return profilePic;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }


    public ArrayList<Integer> rating() {
        return rating;
    }

    public List<UserLesson> getLessons() {
        return classes;
    }

    public List getComments()
    {
        return comments;
    }
}
