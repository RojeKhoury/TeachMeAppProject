package com.example.teachmeapp.Helpers;

import com.google.firebase.Timestamp;

public class BookedLesson {

    private UserLesson lesson;
    private Timestamp timeStart;
    private Timestamp timeEnd;
    private String teacherName;
    private String teacherUID;
    private String  studentName;
    private String  studentUID;
    private Boolean zoom;
    private Boolean teachersHome;
    private Boolean studentsHome;
    private Boolean teacherRequest;

    public BookedLesson()
    {}

    public BookedLesson(UserLesson lesson, Timestamp timeStart, Timestamp timeEnd, String teacherName, String teacherUID, String studentName, String studentUID, boolean zoom, boolean teachersHome, boolean studentsHome, Boolean teacherRequest)
    {
        this.lesson = lesson;
        this.timeStart = timeStart;
        this.teacherName = teacherName;
        this.teacherUID = teacherUID;
        this.timeEnd = timeEnd;
        this.zoom = zoom;
        this.teachersHome = teachersHome;
        this.studentsHome = studentsHome;
        this.studentName = studentName;
        this.studentUID = studentUID;
        this.teacherRequest = teacherRequest;
    }

    public UserLesson getLesson()
    {
        return lesson;
    }

    public Timestamp getTimeStart()
    {
        return timeStart;
    }

    public Timestamp getTimeEnd()
    {
        return timeEnd;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherUID() {
        return teacherUID;
    }

    public Boolean isStudentsHome() {
        return studentsHome;
    }

    public Boolean isTeachersHome() {
        return teachersHome;
    }

    public Boolean isZoom() {
        return zoom;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentUID() {
        return studentUID;
    }
}
