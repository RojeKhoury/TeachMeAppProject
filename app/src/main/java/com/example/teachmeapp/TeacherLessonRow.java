package com.example.teachmeapp;

public class TeacherLessonRow {

    private String m_subject;
    private String m_level;
    private String m_price;

    public TeacherLessonRow(String m_subject, String m_level, String m_price) {
        this.m_subject = m_subject;
        this.m_level = m_level;
        this.m_price = m_price;
    }

    public String getM_subject() {
        return m_subject;
    }

    public String getM_level() {
        return m_level;
    }

    public String getM_price() {
        return m_price;
    }

}
