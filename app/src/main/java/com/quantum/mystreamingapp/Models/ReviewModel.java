package com.quantum.mystreamingapp.Models;

public class ReviewModel {


    private String usre_name;
    private String rating;
    private String date;
    private String review_content,image;

    public ReviewModel(String usre_name, String rating, String date, String review_content, String image) {
        this.usre_name = usre_name;
        this.rating = rating;
        this.date = date;
        this.review_content = review_content;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsre_name() {
        return usre_name;
    }

    public void setUsre_name(String usre_name) {
        this.usre_name = usre_name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }


}
