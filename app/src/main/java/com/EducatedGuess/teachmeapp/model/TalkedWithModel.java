package com.EducatedGuess.teachmeapp.model;

public class TalkedWithModel {

    private String uid;
    private long timeStamp;

    public TalkedWithModel(){}

    public TalkedWithModel(String uid, long timeStamp){
        this.uid = uid;
        this.timeStamp = timeStamp;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
