package com.example.teachmeapp.Helpers;

import com.example.teachmeapp.SearchResultsRow;
import com.example.teachmeapp.TeacherLessonRow;
import com.example.teachmeapp.model.Request;

import java.util.Map;

public class Globals
{
    public static final String PROFILE_PIC_STORAGE_PATH = "images/profilePictures";
    public static final Integer QUALITY = 50;
    public static final String EMAIL_ALERT_TITLE = "Enter email";
    public static final String EMAIL_ALERT_TEXT = "What is your email BUDDY!";

    public static final String NAME_ALERT_TITLE = "Enter Name";
    public static final String NAME_ALERT_TEXT ="Enter your Name and Last Name";

    public static final String BIO_ALERT_TITLE = "Enter Bio";
    public static final String BIO_ALERT_TEXT ="Enter your Bio";

    public static final String PHONE_ALERT_TITLE = "Enter Phone";
    public static final String PHONE_ALERT_TEXT = "Enter your New Phone Number";

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
    public static final int SEARCH_RESULT_FOR_SCHDULE = 1;
    public static final int SEARCH_FOR_TEACHER_VIEW = 2;
    public static final int LESSONS_FOR_TEACHER_VIEW = 3;
    public static final int HISTORY_OF_LESSONS_VIEW = 4;
    public static final int TEACHER_PENDING_REQUESTS_VIEW = 5;
    public static final int STUDENT_PENDING_REQUESTS_VIEW = 6;
    public static final int PROFILE_PAGE_OF_SPECIFIC_TEACHER = 7;
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
    public static final String FIELD_SCHEDULE = "schedule";
    public static final String PENDING_LESSONS = "pendingLessonRequests";
    public static final String FIELD_TEACHERS = "teachers";
    public static final String FIELD_PRICE = "price";
    public static final String LANGUAGES = "languages";
    public static final String RATING_COUNT = "numberOFRatings";
    public static final String RATINGS = "rating";
    public static final String FAVOURITES = "favourites";
    public static final String STUDENTS ="STUDENTS" ;
    public static final String TEACHERS ="TEACHERS" ;
    public static final String TALKEDWITH ="TalkedWith";
    public static final String BOOKED_LESSON = "BookedLesson";
    public static final String IS_TEACHER="IsTeacher";

    //New DB
    public static final String STUDENT_USER="Students";
    public static final String TEACHER_USER="Teachers";
    public static final String SUBJECT = "name";
    public static final String LEVEL = "level";
    public static final String TEACHER_UID = "teacherUID";
    public static final String STUDENT_NAME = "studentName";
    public static final String TIME_START = "timeStart";
    public static final String TIME_END = "timeEnd";
    public static final String TEACHER_NAME = "teacherName";
    public static final String STUDENTS_HOME = "studentsHome";
    public static final String TEACHERS_HOME = "teachersHome";
    public static final String FIELD_LEVEL = "level";
    public static final String FIELD_LESSON_TOPIC_LIST = "lessonList";
    public static final String[] LEVELS_ARRAY = {"Elementary", "Middle-School", "High-School", "College and above"};
    public static final Object STUDENT_ID = "m_studentUID";

    public static communicationWithDatabase comm = new communicationWithDatabase();

    public static boolean getLessonBoolean = false;
    public static SearchResultsRow getLessonCurrentObj = null;
    public static TeacherLessonRow getLessonCurrentObjTeacherProfile = null;
    public static Request currentRequest = null;
    public static boolean locationOrSignUp = true;
    public static String currentTalkedWithUID = "";


}
