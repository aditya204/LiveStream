package com.quantum.mystreamingapp.Models;

public class dealsofthedayModel {

    private String image;
    private String title,description,price,id,tag;

    public dealsofthedayModel(String image, String title, String description, String price, String id, String tag) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.price = price;
        this.id=id;
        this.tag=tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
