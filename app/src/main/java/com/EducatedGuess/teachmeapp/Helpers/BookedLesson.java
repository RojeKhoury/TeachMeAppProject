package com.EducatedGuess.teachmeapp.Helpers;

import com.google.firebase.Timestamp;

import java.time.LocalDateTime;

public class BookedLesson {

    public void setLesson(UserLesson lesson) {
        this.lesson = lesson;
    }

    public void setTimeStart(LocalDateTime timeStart) {
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
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private String teacherName;
    private String teacherUID;
    private String  studentName;
    private String  studentUID;
    private Boolean zoom;
    private Boolean teachersHome;
    private Boolean studentsHome;
    private Boolean teacherRequest;
    private Boolean rejected;

    public Boolean getRejected() {
        return rejected;
    }
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

    public void setTimeEnd(Timestamp LocalDateTime) {
        this.timeEnd = timeEnd;
    }

    public BookedLesson()
    {}

    public BookedLesson(UserLesson lesson, LocalDateTime timeStart, LocalDateTime timeEnd, String teacherName, String teacherUID, String studentName, String studentUID, boolean zoom, boolean teachersHome, boolean studentsHome, Boolean teacherRequest)
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
        this.rejected = false;
    }

    public UserLesson getLesson()
    {
        return lesson;
    }

    public LocalDateTime getTimeStart()
    {
        return timeStart;
    }

    public LocalDateTime getTimeEnd()
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
