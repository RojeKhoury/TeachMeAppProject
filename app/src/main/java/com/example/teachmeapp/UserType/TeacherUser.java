package com.example.teachmeapp.UserType;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TeacherUser implements Parcelable {

    public String getmFName() {
        return mFName;
    }

    public String getmLName() {
        return mLName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmBio() {
        return mBio;
    }

    public void setmFName(String mFName) {
        this.mFName = mFName;
    }

    public void setmLName(String mLName) {
        this.mLName = mLName;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setmBio(String mBio) {
        this.mBio = mBio;
    }


    public int getmRatingsCount() {
        return mRatingsCount;
    }

    public void setmRatingsCount(int mRatingsCount) {
        this.mRatingsCount = mRatingsCount;
    }

    public float getmRatingsSum() {
        return mRatingsSum;
    }

    public void setmRatingsSum(float mRatingsSum) {
        this.mRatingsSum = mRatingsSum;
    }

    public float getmRating() {
        if(mRatingsCount==0) {
            return 0;
        }
        else {
            return (mRatingsSum / mRatingsCount);
        }
    }

    public void setmRating(float mRating) {
        this.mRating = mRating;
    }

    public void IncrementRatingCount()
    {
        mRatingsCount++;
    }

    public String getmUid() {
        return mUid;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }


    private String mFName;
    private String mLName;
    private String mPhoneNumber;
    private String mEmail;
    private String mAddress;
    private String mPassword;
    private String mBio;
    private int mRatingsCount;
    private float mRatingsSum;
    private float mRating;
    private String mUid;

    public TeacherUser(Parcel source)
    {
        Log.e("Test", "BabySitter(Parcel source) <<");
        mFName =  source.readString();
        mLName =  source.readString();
        mPhoneNumber =  source.readString();
        mEmail =  source.readString();
        mAddress =  source.readString();
        mPassword =  source.readString();
        mBio = source.readString();
        mRatingsCount = source.readInt();
        mRatingsSum = source.readFloat();
        mRating = source.readFloat();
        mUid = source.readString();
    }
    public TeacherUser()
    {

    }
    public TeacherUser(String i_FName, String i_LName,  String i_PhoneNumber, String i_Email , String i_Password, String i_Bio,int i_RatingsCount, float i_RatingsSum, String i_Uid )
    {
        Log.e("Test", "<<");
        mFName = i_FName;
        mLName = i_LName;
        mPhoneNumber = i_PhoneNumber;
        mEmail = i_Email;
        mPassword = i_Password;
        mBio = i_Bio;
        mRatingsCount = i_RatingsCount;
        mRatingsSum = i_RatingsSum;
        mRating = 0;
        mUid = i_Uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        Log.e("Test", "writeToParcel(Parcel parcel, int i) <<");
        parcel.writeString(mFName);
        parcel.writeString(mLName);
        parcel.writeString(mPhoneNumber);
        parcel.writeString(mEmail);
        parcel.writeString(mAddress);
        parcel.writeString(mPassword);
        parcel.writeString(mBio);
        parcel.writeInt(mRatingsCount);
        parcel.writeFloat(mRatingsSum);
        parcel.writeFloat(mRating);
        parcel.writeString(mUid);
        Log.e("Test", "writeToParcel(Parcel parcel, int i) >>");
    }

    public static  final Parcelable.Creator<TeacherUser> CREATOR = new Creator<TeacherUser>(){
        @Override
        public TeacherUser createFromParcel(Parcel source) { return new TeacherUser(source);}
        @Override
        public TeacherUser[] newArray(int size) {return  new TeacherUser[size];}
    };
}
