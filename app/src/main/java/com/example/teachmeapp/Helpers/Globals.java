package com.example.teachmeapp.Helpers;

public class Globals
{
    public static final String PROFILE_PIC_STORAGE_PATH = "images/profilePictures";
    public static final Integer QUALITY = 50;
    public static final String EMAIL_ALERT_TITLE = "Enter email";
    public static final String EMAIL_ALERT_TEXT = "What is your email BUDDY!";

    public static final String NAME_ALERT_TITLE = "Enter Name";
    public static final String NAME_ALERT_TEXT ="Enter your name and last name";

    public static final String BIO_ALERT_TITLE = "Enter Bio";
    public static final String BIO_ALERT_TEXT ="Enter your Bio";

    public static final String PHONE_ALERT_TITLE = "Enter phone!";
    public static final String PHONE_ALERT_TEXT = "Enter your new phone number";

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
    public static final String FIELD_BIO = "bio";
    public static final String FIELD_RATING = "rating";


    public static final String FIELD_ZOOM = "zoom";
    public static final String FIELD_TEACHERHOME = "teacherHome";
    public static final String FIELD_STUDENTHOME = "studentHome";
    public static final String ERROR_LOADING_PICTURE = "Profile picture could not be loaded";
    public static final int SEARCH_RESULT = 1;
    public static final int SEARCH_FOR_TEACHER_VIEW = 2;
    public static final int LESSONS_FOR_TEACHER_VIEW = 3;
    public static final int HISTORY_OF_LESSONS_VIEW = 4;
    public static final int TEACHER_PENDING_REQUESTS_VIEW = 5;
    public static final int STUDENT_PENDING_REQUESTS_VIEW = 6;
    public static final String FIELD_UID = "uid";

    public static final int MAPS_CHOOSE_LOCATION = 0;
    public static final int MAPS_REGULAR = 1;
    public static final String LOCATION = "location";
    public static final String LONGITUDE = "longitude";
    public static final String LALTITUDE = "laltitude";
    public static final String CITY = "city";
    public static final String COUNTRY = "country";
    public static final String ADDRESS = "address";
    public static final String ADD_NEW_LESSON = "add a new lesson!";

    public static communicationWithDatabase comm = new communicationWithDatabase();
}
