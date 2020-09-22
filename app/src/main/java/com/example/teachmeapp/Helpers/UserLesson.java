package com.example.teachmeapp.Helpers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserLesson {

    private String name;
    private Double price;
    private Integer level;

    public UserLesson() {
    }

    public UserLesson(String name, Double price, Integer level) {
        // [START_EXCLUDE]
        this.name = name;
        this.price = price;
        this.level = level;
        // [END_EXCLUDE]
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getlevel() {
        return level;
    }

}
