package com.example.teachmeapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BabySitter implements Parcelable {

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

    public int getmPrice() {
        return mPrice;
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

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public void setmBio(String mBio) {
        this.mBio = mBio;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
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

    public List<Boolean> getmWorkingWeekDaysList() {
        return mWorkingWeekDaysList;
    }

    public void setmWorkingWeekDaysList(ArrayList<Boolean> mWorkingWeekDaysList) {
        this.mWorkingWeekDaysList = mWorkingWeekDaysList;
    }

    public boolean ismHasCar() {
        return mHasCar;
    }

    public void setmHasCar(boolean mHasCar) {
        this.mHasCar = mHasCar;
    }

    public Long getmDateOfBirth() {
        return mDateOfBirth;
    }

    public void setmDateOfBirth(Long mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    private String mFName;
    private String mLName;
    private Long mDateOfBirth;
    private String mPhoneNumber;
    private String mEmail;
    private String mAddress;
    private String mPassword;
    private int mPrice;
    private String mBio;
    private String mGender;
    private int mRatingsCount;
    private float mRatingsSum;
    private float mRating;
    private String mUid;
    private boolean mHasCar;
    private ArrayList<Boolean> mWorkingWeekDaysList;

    public BabySitter(Parcel source)
    {
        Log.e("Test", "BabySitter(Parcel source) <<");
        mFName =  source.readString();
        mLName =  source.readString();
        mDateOfBirth =  source.readLong();
        mPhoneNumber =  source.readString();
        mEmail =  source.readString();
        mAddress =  source.readString();
        mPassword =  source.readString();
        mPrice =  source.readInt();
        mBio = source.readString();
        mGender = source.readString();
        mRatingsCount = source.readInt();
        mRatingsSum = source.readFloat();
        mRating = source.readFloat();
        mUid = source.readString();
        mWorkingWeekDaysList = source.readArrayList(Boolean.class.getClassLoader());
        mHasCar = source.readByte() != 0;
    }
    public BabySitter()
    {

    }
    public BabySitter(String i_FName,String i_LName, Long i_DateOfBirth, String i_PhoneNumber,String i_Email ,String i_Address, String i_Password, int i_Price, String i_Bio, String i_Gender, int i_RatingsCount, float i_RatingsSum, String i_Uid , boolean i_HasCar)
    {
        Log.e("Test", "BabySitter(String i_FName,String i_LName, String i_DateOfBirth, String i_PhoneNumber,String i_Email ,String i_Address, String i_Password, int i_Price, String i_Bio, String i_Gender, int i_RatingsCount, float i_RatingsSum, String i_Uid) <<");
        mFName = i_FName;
        mLName = i_LName;
        mDateOfBirth = i_DateOfBirth;
        mPhoneNumber = i_PhoneNumber;
        mEmail = i_Email;
        mAddress = i_Address;
        mPassword = i_Password;
        mPrice = i_Price;
        mBio = i_Bio;
        mGender = i_Gender;
        mRatingsCount = i_RatingsCount;
        mRatingsSum = i_RatingsSum;
        mRating = 0;
        mUid = i_Uid;
        mWorkingWeekDaysList = new ArrayList<Boolean>();
        mWorkingWeekDaysList.add(true);
        mWorkingWeekDaysList.add(true);
        mWorkingWeekDaysList.add(true);
        mWorkingWeekDaysList.add(true);
        mWorkingWeekDaysList.add(true);
        mWorkingWeekDaysList.add(true);
        mWorkingWeekDaysList.add(true);
        mHasCar = i_HasCar;
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
        parcel.writeLong(mDateOfBirth);
        parcel.writeString(mPhoneNumber);
        parcel.writeString(mEmail);
        parcel.writeString(mAddress);
        parcel.writeString(mPassword);
        parcel.writeInt(mPrice);
        parcel.writeString(mBio);
        parcel.writeString(mGender);
        parcel.writeInt(mRatingsCount);
        parcel.writeFloat(mRatingsSum);
        parcel.writeFloat(mRating);
        parcel.writeString(mUid);
        parcel.writeList(mWorkingWeekDaysList);
        parcel.writeByte((byte) (mHasCar ? 1 : 0));
        Log.e("Test", "writeToParcel(Parcel parcel, int i) >>");
    }

    public static  final Parcelable.Creator<BabySitter> CREATOR = new Creator<BabySitter>(){
        @Override
        public BabySitter createFromParcel(Parcel source) { return new BabySitter(source);}
        @Override
        public BabySitter[] newArray(int size) {return  new BabySitter[size];}
    };
}
