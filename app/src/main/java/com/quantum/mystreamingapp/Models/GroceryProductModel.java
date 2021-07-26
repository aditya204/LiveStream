package com.quantum.mystreamingapp.Models;

import java.util.ArrayList;
import java.util.Date;

public class GroceryProductModel {

    String name,offertype,offerAmount,price,rating,reviewCount,id,tag_list;
    long stock;
    String image;
    Date date;
    boolean is_timmer;
    private ArrayList<String> tags;

    public GroceryProductModel(String image, String name, String offertype, String offerAmount, String price, String rating, String reviewCount, long Stock, String id, String tag_list,Date date,boolean is_timmer) {
        this.image=image;
        this.name = name;
        this.id=id;
        this.offertype = offertype;
        this.offerAmount = offerAmount;
        this.price = price;
        this.rating = rating;
        this.reviewCount=reviewCount;
        this.stock=Stock;
        this.tag_list=tag_list;
        this.date=date;
        this.is_timmer=is_timmer;

    }

    public boolean isIs_timmer() {
        return is_timmer;
    }

    public void setIs_timmer(boolean is_timmer) {
        this.is_timmer = is_timmer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTag_list() {
        return tag_list;
    }

    public void setTag_list(String tag_list) {
        this.tag_list = tag_list;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
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

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getOffertype() {
        return offertype;
    }

    public void setOffertype(String offertype) {
        this.offertype = offertype;
    }

    public String getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(String offerAmount) {
        this.offerAmount = offerAmount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }
}
