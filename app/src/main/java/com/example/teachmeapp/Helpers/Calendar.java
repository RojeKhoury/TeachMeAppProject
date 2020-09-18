package com.example.teachmeapp.Helpers;

import java.util.ArrayList;
import java.util.Map;

public class Calendar {

    private Map<String, BookedLesson> schedule;

    public void Calendar(){}

    public void Calendar(Map<String, BookedLesson> schedule)
    {
        this.schedule = schedule;
    }

    public Map<String, BookedLesson> getSchedule()
    {
        return schedule;
    }

}
