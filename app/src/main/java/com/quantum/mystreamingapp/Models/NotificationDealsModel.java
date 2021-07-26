package com.quantum.mystreamingapp.Models;

public class NotificationDealsModel {
    String image,header,time,description,seller_id,id,product_id;
    boolean is_readed,is_timmer;

    public NotificationDealsModel(String image, String header, String time, String description, String seller_id, String id, boolean is_readed, boolean is_timmer,String product_id) {
        this.image = image;
        this.header = header;
        this.time = time;
        this.description = description;
        this.seller_id = seller_id;
        this.id = id;
        this.is_readed = is_readed;
        this.is_timmer = is_timmer;
        this.product_id=product_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIs_readed() {
        return is_readed;
    }

    public void setIs_readed(boolean is_readed) {
        this.is_readed = is_readed;
    }

    public boolean isIs_timmer() {
        return is_timmer;
    }

    public void setIs_timmer(boolean is_timmer) {
        this.is_timmer = is_timmer;
    }
}
