package com.EducatedGuess.teachmeapp;

import java.time.LocalDateTime;

public class StudentPendingRequestRow {

    private String m_teacherUID;

    public String getM_studentUID() {
        return m_studentUID;
    }

    private String m_studentUID;

    public StudentPendingRequestRow(String m_studentUID, String m_teacherUID, String m_studentName, LocalDateTime m_timeStart,
                                    LocalDateTime m_timeEnd, String m_teacherName, Boolean m_zoom, Boolean m_teachersHome,
                                    Boolean m_studentsHome, String m_subject, Double m_price, String m_level) {
        this.m_teacherUID = m_teacherUID;
        this.m_studentName = m_studentName;
        this.m_timeStart = m_timeStart;
        this.m_timeEnd = m_timeEnd;
        this.m_teacherName = m_teacherName;
        this.m_zoom = m_zoom;
        this.m_teachersHome = m_teachersHome;
        this.m_studentsHome = m_studentsHome;
        this.m_subject = m_subject;
        this.m_price = m_price;
        this.m_level = m_level;
        this.m_studentUID = m_studentUID;

    }

    public String getM_studentName() {
        return m_studentName;
    }

    private String m_studentName;

    private LocalDateTime m_timeStart;
    private LocalDateTime m_timeEnd;

    private String m_teacherName;

    private Boolean m_zoom;
    private Boolean m_teachersHome;
    private Boolean m_studentsHome;

    private String m_subject;
    private Double m_price;
    private String m_level;

    public String getM_teacherUID() {
        return m_teacherUID;
    }

    public LocalDateTime getM_timeStart() {
        return m_timeStart;
    }

    public LocalDateTime getM_timeEnd() {
        return m_timeEnd;
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