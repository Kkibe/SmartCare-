package com.nutricon.smartcare.data;

public class Info {
    String title;
    String image;
    String description;
    String id;

    public Info(String title, String image, String description, String id) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
