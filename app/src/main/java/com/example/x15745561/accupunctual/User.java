package com.example.x15745561.accupunctual;

public class User {
    private String username;
    private String userClockStatus;
    private String userClockTime;

    public User(String username, String userClockStatus, String userClockTime) {
        this.username = username;
        this.userClockStatus = userClockStatus;
        this.userClockTime = userClockTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserClockStatus() {
        return userClockStatus;
    }

    public void setUserClockStatus(String userClockStatus) {
        this.userClockStatus = userClockStatus;
    }

    public String getUserClockTime() {
        return userClockTime;
    }

    public void setUserClockTime(String userClockTime) {
        this.userClockTime = userClockTime;
    }
}
