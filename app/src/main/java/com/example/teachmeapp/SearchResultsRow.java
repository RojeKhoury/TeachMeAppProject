package com.example.teachmeapp;

import android.net.Uri;

public class SearchResultsRow  {


    private String m_teacherUID;
    private String m_teacherName;

    private Uri m_imageURI;

    private String m_teacherCity;

    private Boolean m_zoom;
    private Boolean m_teachersHome;
    private Boolean m_studentsHome;
    private String m_subject;

    private String m_surname;
    private String m_level;
    private Double m_price;

    private Double m_rating;
    public SearchResultsRow(String m_surname, String m_teacherCity, Double m_rating, String m_teacherUID, String m_teacherName, Boolean m_zoom, Boolean m_teachersHome, Boolean m_studentsHome, String m_subject, Double m_price, String m_level) {

        this.m_teacherUID = m_teacherUID;
        this.m_teacherName = m_teacherName;
        this.m_zoom = m_zoom;
        this.m_teachersHome = m_teachersHome;
        this.m_studentsHome = m_studentsHome;
        this.m_subject = m_subject;
        this.m_price = m_price;
        this.m_level = m_level;
        this.m_rating = m_rating;
        this.m_teacherCity = m_teacherCity;
        this.m_surname = m_surname;
    }

    public String getM_teacherCity() {
        return m_teacherCity;
    }

    public Double getM_rating() {
        return m_rating;
    }

    public Uri getM_imageURI() {
        return m_imageURI;
    }

    public String getM_teacherUID() {
        return m_teacherUID;
    }

    public String getM_surname() {
        return m_surname;
    }

    public String getM_teacherName() {
        return m_teacherName;
    }

    public Boolean getM_zoom() {
        return m_zoom;
    }

    public Boolean getM_teachersHome() {
        return m_teachersHome;
    }

    public Boolean getM_studentsHome() {
        return m_studentsHome;
    }

    public String getM_subject() {
        return m_subject;
    }

    public Double getM_price() {
        return m_price;
    }

    public String getM_level() {
        return m_level;
    }



}