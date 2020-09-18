package com.example.teachmeapp.Helpers;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teacher {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private Map<String, UserLesson> lessons;
    private List<Comment> comments;
    private boolean zoom;
    private boolean studentHome;
    private boolean teacherHome;
    private String uid;
    private String bio;
    private String city;
    private String country;
    private String address;
    private double rating;
    private int numberOFRatings;
    private LatLng location;
    private Calendar schedule;
    private Calendar pendingLessonRequests;
    private ArrayList<String> languages;

    public Teacher() {
    }

    public Teacher(String name, String surname, String phone, List<Comment> comments, String email, String uid, ArrayList<String> language) {
        // [START_EXCLUDE]
        Map<String, UserLesson> temp = new HashMap<>();

        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.rating = 0;
        this.numberOFRatings = 0;
        this.lessons = temp;
        this.comments = comments;
        this.email = email;
        this.zoom = true;
        this.studentHome = false;
        this.teacherHome = false;
        this.bio = "this my bio";
        this.uid = uid;
        this.city = "";
        this.country = "";
        this.address = "";
        this.location = null;
        this.schedule = new Calendar();
        this.pendingLessonRequests = new Calendar();
        this.languages = language;
        // [END_EXCLUDE]
    }

    public Calendar getSchedule() {
        return schedule;
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

    public double getRating() {
        return rating;
    }

    public int getNumberOFRatings() {
        return numberOFRatings;
    }

    public String getUid() {
        return uid;
    }

    public Map<String, UserLesson> getLessons() {
        return lessons;
    }

    public String getCity(){return city;}

    public String getCountry(){return country;}

    public String getAddress(){return address;}

    public LatLng getLocation(){return location;}

    public ArrayList<String> getLanguages(){return languages;}
}
