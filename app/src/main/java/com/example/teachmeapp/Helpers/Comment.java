package com.example.teachmeapp.Helpers;

import java.util.ArrayList;
import java.util.List;

public class Comment {
    private String id;
    private String comment;
    float rating;

    public Comment() {
    }

    public Comment(String id, String comment, float rating) {
        // [START_EXCLUDE]
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        // [END_EXCLUDE]
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public float getRating(){return rating;}
}
