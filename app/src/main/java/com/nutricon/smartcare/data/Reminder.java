package com.nutricon.smartcare.data;

public class Reminder {
    String title, date, time;
    Boolean active;

    public Reminder(String title, String date, String time, Boolean active) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
