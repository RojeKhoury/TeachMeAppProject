package com.example.teachmeapp.Helpers;

import java.util.List;

public class Student {

    private String name;
    private String surname;
    private String phone;
    private List<Lesson> classes;
    private String profilePic;
    private String email;


    public Student() {
    }

    public Student(String name, String surname, String phone, List<Lesson> classes, String picture, String email) {
        // [START_EXCLUDE]
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.classes = classes;
        this.profilePic = picture;
        this.email = email;
        // [END_EXCLUDE]
    }

    public String getName() {
        return name;
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

    public List<Lesson> getLessons() {
        return classes;
    }

}
