package com.example.teachmeapp.UserType;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentUser implements Parcelable {

    private String mFname,mLName, mPhoneNumber, mEmail,mAddress, mPassword, mUid;


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

    public StudentUser(){}

    public StudentUser(Parcel source)
    {
        this.mFname = source.readString();
        this.mLName = source.readString();
        this.mPhoneNumber = source.readString();
        this.mEmail = source.readString();
        this.mAddress = source.readString();
        this.mPassword = source.readString();
        this.mUid = source.readString();
    }

    public StudentUser(String i_Fname, String i_LName, String i_PhoneNumber, String i_Email ,  String i_Password, String i_Uid)
    {
        mFname = i_Fname;
        mLName = i_LName;

        mPhoneNumber = i_PhoneNumber;
        mEmail = i_Email;
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

        parcel.writeString(mPhoneNumber);
        parcel.writeString(mEmail);
        parcel.writeString(mAddress);
        parcel.writeString(mPassword);
        parcel.writeString(mUid);
    }

    public static  final Parcelable.Creator<StudentUser> CREATOR = new Parcelable.Creator<StudentUser>(){
        @Override
        public StudentUser createFromParcel(Parcel source) { return new StudentUser(source);}
        @Override
        public StudentUser[] newArray(int size) {return  new StudentUser[size];}
    };
}
