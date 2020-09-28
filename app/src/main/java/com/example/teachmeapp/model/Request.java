package com.example.teachmeapp.model;

import android.os.Bundle;

import com.example.teachmeapp.Helpers.UserLesson;
import com.google.firebase.Timestamp;

import java.time.LocalDateTime;

import static com.example.teachmeapp.Helpers.Globals.STUDENT_PENDING_REQUESTS_VIEW;

public class Request {

    private String id;
    private String m_teacherUID;
    private String m_studentUID;

    private String m_teacherName;
    private  String m_studentName;

    private String m_subject;
    private String m_price;
    private String m_level;

    private String m_timeStart;
    private String m_timeEnd;
    private String m_dateStart;

    private Boolean m_zoom;
    private Boolean m_faceToFace;
    private Boolean isPending, isAccepting, isRejecting;


    public Request(){}

    public Request(String m_studentUID, String m_teacherUID, String m_teacherName, String m_studentName, String m_timeStart,
                                    String m_timeEnd, Boolean m_zoom, Boolean m_faceToFace,
                   String m_subject, String m_price, String m_level, Boolean isPending, Boolean isAccepting, Boolean isRejecting
    , String m_dateStart) {


        this.m_teacherUID = m_teacherUID;
        this.m_timeStart = m_timeStart;
        this.m_timeEnd = m_timeEnd;
        this.m_zoom = m_zoom;
        this.m_faceToFace = m_faceToFace;
        this.m_subject = m_subject;
        this.m_price = m_price;
        this.m_level = m_level;
        this.m_studentUID = m_studentUID;
        this.isPending = isPending;
        this.isAccepting = isAccepting;
        this.isRejecting = isRejecting;
        this.m_teacherName = m_teacherName;
        this.m_studentName = m_studentName;
        this.m_dateStart = m_dateStart;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setM_dateStart(String m_dateStart) {
        this.m_dateStart = m_dateStart;
    }

    public String getM_dateStart() {
        return m_dateStart;
    }

    public String getM_studentName() {
        return m_studentName;
    }

    public String getM_teacherName() {
        return m_teacherName;
    }

    public void setM_studentName(String m_studentName) {
        this.m_studentName = m_studentName;
    }

    public void setM_teacherName(String m_teacherName) {
        this.m_teacherName = m_teacherName;
    }

    public void setAccepting(Boolean accepting) {
        isAccepting = accepting;
    }

    public void setM_faceToFace(Boolean m_faceToFace) {
        this.m_faceToFace = m_faceToFace;
    }

    public void setM_level(String m_level) {
        this.m_level = m_level;
    }

    public void setM_price(String m_price) {
        this.m_price = m_price;
    }

    public void setM_studentUID(String m_studentUID) {
        this.m_studentUID = m_studentUID;
    }

    public void setM_subject(String m_subject) {
        this.m_subject = m_subject;
    }

    public void setM_teacherUID(String m_teacherUID) {
        this.m_teacherUID = m_teacherUID;
    }

    public void setM_timeEnd(String m_timeEnd) {
        this.m_timeEnd = m_timeEnd;
    }

    public void setM_timeStart(String m_timeStart) {
        this.m_timeStart = m_timeStart;
    }

    public void setM_zoom(Boolean m_zoom) {
        this.m_zoom = m_zoom;
    }

    public void setPending(Boolean pending) {
        isPending = pending;
    }

    public void setRejecting(Boolean rejecting) {
        isRejecting = rejecting;
    }

    public String getM_studentUID() {
        return m_studentUID;
    }

    public String getM_teacherUID() {
        return m_teacherUID;
    }

    public String getM_timeStart() {
        return m_timeStart;
    }

    public String getM_timeEnd() {
        return m_timeEnd;
    }

    public Boolean getM_zoom() {
        return m_zoom;
    }

    public Boolean getM_faceToFace() {
        return m_faceToFace;
    }

    public String getM_subject() {
        return m_subject;
    }

    public String getM_price() {
        return m_price;
    }

    public String getM_level() {
        return m_level;
    }

    public Boolean getPending() {
        return isPending;
    }

    public Boolean getAccepting() {
        return isAccepting;
    }

    public Boolean getRejecting() {
        return isRejecting;
    }
}