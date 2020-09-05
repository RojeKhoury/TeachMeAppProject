package com.example.teachmeapp.Helpers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserLesson {
    private String name;
    private float price;
    private List<Integer> rating;
    private List<Comment> comments;

    public UserLesson() {
    }

    public UserLesson(String name, ArrayList<Integer> rating, List<Comment> comments, Float price) {
        // [START_EXCLUDE]
        this.name = name;
        this.rating = rating;
        this.comments = comments;
        this.price = price;
        // [END_EXCLUDE]
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public List getComments() {
        return comments;
    }

    public List getRating() {
        return rating;
    }

}
