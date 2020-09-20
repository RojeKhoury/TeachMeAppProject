package com.example.teachmeapp.model;

public class Message {
    String mUserUid,mMessage;
    long mMessgeTime;

    public Message() {
    }

    public Message(String UserUid, String Message,long time) {
        this.mUserUid = mUserUid;
        this.mMessage = mMessage;
        this.mMessgeTime = time;
    }
    public long getmMessgeTime() { return mMessgeTime; }

    public void setmMessgeTime(long mMessgeTime) { this.mMessgeTime = mMessgeTime; }

    public void setmMessage(String mMessage) { this.mMessage = mMessage; }

    public String getmUserUid() { return mUserUid; }

    public void setmUserUid(String mUserUid) { this.mUserUid= mUserUid; }

    public String getmMessage() { return mMessage; }
}
