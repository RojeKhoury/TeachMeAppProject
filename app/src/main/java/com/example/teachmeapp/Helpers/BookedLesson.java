package com.example.teachmeapp.Helpers;

import com.google.firebase.Timestamp;

public class BookedLesson {

    public void setLesson(UserLesson lesson) {
        this.lesson = lesson;
    }

    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

    public void setTeacherUID(String teacherUID) {
        this.teacherUID = teacherUID;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setZoom(Boolean zoom) {
        this.zoom = zoom;
    }

    public void setStudentsHome(Boolean studentsHome) {
        this.studentsHome = studentsHome;
    }

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

    public void setTeacherRequest(Boolean teacherRequest) {
        this.teacherRequest = teacherRequest;
    }

    public void setTeachersHome(Boolean teachersHome) {
        this.teachersHome = teachersHome;
    }

    public void setStudentUID(String studentUID) {
        this.studentUID = studentUID;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTimeEnd(Timestamp timeEnd) {
        this.timeEnd = timeEnd;
    }

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
