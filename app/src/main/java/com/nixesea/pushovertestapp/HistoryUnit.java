package com.nixesea.pushovertestapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class HistoryUnit {

    @PrimaryKey(autoGenerate = true)
    long id;
    String userID;
    String message;
    String date;

    public HistoryUnit(String userID, String message, String date) {
        this.userID = userID;
        this.message = message;
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
