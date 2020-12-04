package com.EducatedGuess.teachmeapp.Helpers;

public class Comment {
    public void setId(String id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String id;
    private String comment;
    float rating;

    public void setRating(float rating) {
        this.rating = rating;
    }

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
