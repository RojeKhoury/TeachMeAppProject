package com.example.teachmeapp.Helpers;

import com.google.firebase.Timestamp;

public class BookedLesson {
    UserLesson lesson;
    Timestamp timeStart;
    Timestamp timeEnd;
    String  teacherStudentName;
    String  teacherStudentUID;
    boolean zoom;
    boolean teachersHome;
    boolean studentsHome;

    public BookedLesson()
    {}

    public BookedLesson(UserLesson lesson, Timestamp timeStart, Timestamp timeEnd, String name, String uid, boolean zoom, boolean teachersHome, boolean studentsHome)
    {
        this.lesson = lesson;
        this.timeStart = timeStart;
        teacherStudentName = name;
        teacherStudentUID = uid;
        this.timeEnd = timeEnd;
        this.zoom = zoom;
        this.teachersHome = teachersHome;
        this.studentsHome = studentsHome;
    }

    public UserLesson getLesson()
    {
        return lesson;
    }

    public Timestamp getTimeStart()
    {
        return timeStart;
    }

    public String getTeacherStudentName() {
        return teacherStudentName;
    }

    public String getTeacherStudentUID() {
        return teacherStudentUID;
    }

    public boolean isStudentsHome() {
        return studentsHome;
    }

    public boolean isTeachersHome() {
        return teachersHome;
    }

    public boolean isZoom() {
        return zoom;
    }
}
