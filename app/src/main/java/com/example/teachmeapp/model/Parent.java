package com.example.teachmeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parent implements Parcelable {

    private String mFname,mLName, mPhoneNumber, mEmail,mAddress, mPassword, mUid;
    private Long mDateOfBirth;

    public Long getmDateOfBirth() {
        return mDateOfBirth;
    }

    public void setmDateOfBirth(Long mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    public String getmUid() {
        return mUid;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }

    public String getmEmail() { return mEmail; }

    public String getmFname() {
        return mFname;
    }

    public void setmFname(String mFname) {
        this.mFname = mFname;
    }

    public String getmLName() {
        return mLName;
    }

    public void setmLName(String mLName) {
        this.mLName = mLName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public  Parent(){}

    public  Parent(Parcel source)
    {
        this.mFname = source.readString();
        this.mLName = source.readString();
        this.mDateOfBirth = source.readLong();
        this.mPhoneNumber = source.readString();
        this.mEmail = source.readString();
        this.mAddress = source.readString();
        this.mPassword = source.readString();
        this.mUid = source.readString();
    }

    public Parent(String i_Fname,String i_LName, Long i_DateOfBirth, String i_PhoneNumber,String i_Email ,String i_Address, String i_Password, String i_Uid)
    {
        mFname = i_Fname;
        mLName = i_LName;
        mDateOfBirth = i_DateOfBirth;
        mPhoneNumber = i_PhoneNumber;
        mEmail = i_Email;
        mAddress = i_Address;
        mPassword = i_Password;
        mUid = i_Uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(mFname);
        parcel.writeString(mLName);
        parcel.writeLong(mDateOfBirth);
        parcel.writeString(mPhoneNumber);
        parcel.writeString(mEmail);
        parcel.writeString(mAddress);
        parcel.writeString(mPassword);
        parcel.writeString(mUid);
    }

    public static  final Parcelable.Creator<Parent> CREATOR = new Parcelable.Creator<Parent>(){
        @Override
        public Parent createFromParcel(Parcel source) { return new Parent(source);}
        @Override
        public Parent[] newArray(int size) {return  new Parent[size];}
    };
}
