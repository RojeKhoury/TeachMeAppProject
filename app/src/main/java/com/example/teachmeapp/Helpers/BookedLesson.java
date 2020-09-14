package com.example.teachmeapp.Helpers;

import com.google.firebase.Timestamp;

public class BookedLesson {
    UserLesson lesson;
    Timestamp time;
    String  teacherStudentName;
    String  teacherStudentUID;

    public BookedLesson()
    {}

    public BookedLesson(UserLesson lesson, Timestamp time, String name, String uid)
    {
        this.lesson = lesson;
        this.time = time;
        teacherStudentName = name;
        teacherStudentUID = uid;
    }

    public UserLesson getLesson()
    {
        return lesson;
    }

    public Timestamp getTime()
    {
        return time;
    }

    public String getTeacherStudentName() {
        return teacherStudentName;
    }

    public String getTeacherStudentUID() {
        return teacherStudentUID;
    }
}
