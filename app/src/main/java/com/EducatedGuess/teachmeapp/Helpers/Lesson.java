package com.EducatedGuess.teachmeapp.Helpers;

import java.util.ArrayList;

public class Lesson {

    private String name;
    private ArrayList<String> Teachers;

    public Lesson() {
    }

    public Lesson(String name, ArrayList<String> teachers) {
        // [START_EXCLUDE]
        this.name = name;
        this.Teachers = teachers;
        // [END_EXCLUDE]
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getTeachers() {
        return Teachers;
    }
}
