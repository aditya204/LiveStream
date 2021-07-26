package com.quantum.mystreamingapp.Models;

public class NotificationModelClass {
    String image,header,time,order_id,product_name,description,product_id,id;
    boolean is_readed,isSeller;

    public NotificationModelClass(String image, String header, String time, String order_id, String product_name, String description, String product_id, String id, boolean is_readed, boolean isSeller) {
        this.image = image;
        this.header = header;
        this.time = time;
        this.order_id = order_id;
        this.product_name = product_name;
        this.description = description;
        this.product_id = product_id;
        this.id = id;
        this.is_readed = is_readed;
        this.isSeller = isSeller;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    public boolean isIs_readed() {
        return is_readed;
    }

    public void setIs_readed(boolean is_readed) {
        this.is_readed = is_readed;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
