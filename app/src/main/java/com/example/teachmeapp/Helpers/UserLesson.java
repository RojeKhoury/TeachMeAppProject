package com.example.teachmeapp.Helpers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserLesson {

    private String name;
    private float price;
    private String level;

    public UserLesson() {
    }

    public UserLesson(String name, Float price, String level) {
        // [START_EXCLUDE]
        this.name = name;
        this.price = price;
        this.level = level;
        // [END_EXCLUDE]
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getlevel() {
        return level;
    }

}
