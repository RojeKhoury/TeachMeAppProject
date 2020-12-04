package com.EducatedGuess.teachmeapp.Helpers;

import java.util.Map;

public class Schedule {
    private Map<String, BookedLesson> lessons;

    public Schedule(Map<String, BookedLesson> lessons) {
        this.lessons = lessons;
    }

    public Schedule(){}

    public Map<String, BookedLesson> getLessons()
    {
        return lessons;
    }

}
