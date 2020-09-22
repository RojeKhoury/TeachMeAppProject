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

    public ArrayList<String> getLessonList() {
        return lessonList;
    }

    public void setLessonList(ArrayList<String> lessonList) {
        this.lessonList = lessonList;
    }

    private ArrayList<String> lessonList;
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
    private Schedule schedule;
    private Schedule pendingLessonRequests;
    private ArrayList<String> languages;


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLessons(Map<String, UserLesson> lessons) {
        this.lessons = lessons;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }

    public void setStudentHome(boolean studentHome) {
        this.studentHome = studentHome;
    }

    public void setTeacherHome(boolean teacherHome) {
        this.teacherHome = teacherHome;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setNumberOFRatings(int numberOFRatings) {
        this.numberOFRatings = numberOFRatings;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setPendingLessonRequests(Schedule pendingLessonRequests) {
        this.pendingLessonRequests = pendingLessonRequests;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public Teacher() {
    }

    public Teacher(String name, String surname, String phone, List<Comment> comments, String email, String uid, ArrayList<String> language) {
        // [START_EXCLUDE]
        Map<String, UserLesson> temp = new HashMap<>();
        Map<String, BookedLesson> temp2 = new HashMap<>();

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
        this.schedule = new Schedule(temp2);
        this.pendingLessonRequests = new Schedule();
        this.languages = language;
        this.lessonList = new ArrayList<>();
        // [END_EXCLUDE]
    }

    public Schedule getSchedule() {
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
