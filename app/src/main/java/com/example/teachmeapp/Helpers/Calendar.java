package com.example.teachmeapp.Helpers;

import java.util.ArrayList;

public class Calendar {
    private ArrayList<BookedLesson> schedule;

    public void Calendar(){}

    public void Calendar(ArrayList<BookedLesson> schedule)
    {
        this.schedule = schedule;
    }

    public ArrayList<BookedLesson> getSchedule()
    {
        return schedule;
    }

}
