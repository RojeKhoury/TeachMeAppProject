package com.example.teachmeapp.Helpers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserLesson {
    private String name;
    private float price;

    public UserLesson() {
    }

    public UserLesson(String name, Float price) {
        // [START_EXCLUDE]
        this.name = name;
        this.price = price;
        // [END_EXCLUDE]
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

}
