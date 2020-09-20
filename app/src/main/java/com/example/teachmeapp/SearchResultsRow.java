package com.example.teachmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SearchResultsRow  {


    private String m_teacherUID;
    private String m_teacherName;

    private Boolean m_zoom;
    private Boolean m_teachersHome;
    private Boolean m_studentsHome;

    private String m_name;
    private Double m_price;
    private String m_level;

    public SearchResultsRow(String m_teacherUID, String m_teacherName, Boolean m_zoom, Boolean m_teachersHome, Boolean m_studentsHome, String m_name, Double m_price, String m_level) {
        this.m_teacherUID = m_teacherUID;
        this.m_teacherName = m_teacherName;
        this.m_zoom = m_zoom;
        this.m_teachersHome = m_teachersHome;
        this.m_studentsHome = m_studentsHome;
        this.m_name = m_name;
        this.m_price = m_price;
        this.m_level = m_level;
    }

    public String getM_teacherUID() {
        return m_teacherUID;
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

    public String getM_name() {
        return m_name;
    }

    public Double getM_price() {
        return m_price;
    }

    public String getM_level() {
        return m_level;
    }



}