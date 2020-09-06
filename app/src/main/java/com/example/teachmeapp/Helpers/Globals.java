package com.example.teachmeapp.Helpers;

public class Globals
{
    public static final String PROFILE_PIC_STORAGE_PATH = "images/profilePictures";
    public static final Integer QUALITY = 50;
    public static final String EMAIL_ALERT_TITLE = "Enter email";
    public static final String EMAIL_ALERT_TEXT = "What is your email BUDDY!";

    public static final String FAILED_TO_UPLOAD_IMAGE = "failed to upload image";

    public static final int AT_MY_PLACE = 0;
    public static final int AT_YOUR_PLACE = 1;
    public static final int ZOOM = 2;

    public static final String COLLECTION_TEACHER = "Teachers";
    public static final String COLLECTION_STUDENT = "Students";
    public static final String COLLECTION_LESSON = "lessons";

    public static final String FIELD_TEACHER_FOR_LESSON = "teachers";
    public static final String FIELD_LESSONS = "lessons";
    public static final String FIELD_COMMENTS = "comments";

    public static final String FIELD_NAME = "name";
    public static final String FIELD_SURNAME = "surname";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_picture = "picture";

    public static final String FIELD_ZOOM = "zoom";
    public static final String FIELD_MYPLACE = "my place";
    public static final String FIELD_YOURPLACE = "your place";


    public static communicationWithDatabase comm = new communicationWithDatabase();
}
