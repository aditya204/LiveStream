package com.quantum.mystreamingapp.Models;

import java.util.Date;

public class HomeProductModel {

    String product_nmae,bid_price,image,product_id;
    Date date;


    public HomeProductModel(String product_nmae, String bid_price, Date date,String image,String product_id) {

        this.product_nmae = product_nmae;
        this.bid_price = bid_price;
        this.date = date;

        this.image=image;
        this.product_id=product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProduct_nmae() {
        return product_nmae;
    }

    public void setProduct_nmae(String product_nmae) {
        this.product_nmae = product_nmae;
    }


    public String getBid_price() {
        return bid_price;
    }

    public void setBid_price(String bid_price) {
        this.bid_price = bid_price;
    }


}
