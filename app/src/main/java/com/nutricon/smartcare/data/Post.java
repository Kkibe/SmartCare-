package com.nutricon.smartcare.data;

public class Post {
    String id;
    String username;
    String title;
    String description;
    String date;
    String image;

    public Post(
            String id,
            String username,
            String title,
            String description,
            String date,
            String image) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }
}
