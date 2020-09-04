package com.example.teachmeapp.Helpers;

import java.util.ArrayList;
import java.util.List;

public class Comment {
    String id;
    String comment;

    public Comment() {
    }

    public Comment(String id, String comment) {
        // [START_EXCLUDE]
        this.id = id;
        this.comment = comment;
        // [END_EXCLUDE]
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }
}
