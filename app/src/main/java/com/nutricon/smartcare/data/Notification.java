package com.nutricon.smartcare.data;

public class Notification {
    String image;
    String title;
    String description;
    Boolean status;
    String date;

    public Notification(String image, String title,String description, Boolean status, String date) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

}
