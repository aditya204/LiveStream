package com.quantum.mystreamingapp.Models;

public class SplashSliderModel {

    String image,heading,description;
    int img;

    public SplashSliderModel(String image, String heading, String description,int img) {
        this.image = image;
        this.heading = heading;
        this.description = description;
        this.img=img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }
}
