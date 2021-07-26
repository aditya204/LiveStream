package com.quantum.mystreamingapp.Models;

public class grocery_cart_product_Model {

    private String name,description,price,cutprice,product_id,offer,itemcount,owner_id;
    String image;
    private long in_stock;
    private  int inde;
    boolean is_timmer;



    public grocery_cart_product_Model(String name, String description, String price, String cutprice, String product_id, String itemcount, String offer, String image, long in_stock, int inde,String owner_id,boolean is_timmer) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.cutprice = cutprice;
        this.product_id = product_id;
        this.itemcount=itemcount;
        this.offer = offer;
        this.image = image;
        this.in_stock=in_stock;
        this.inde=inde;
        this.owner_id=owner_id;
        this.is_timmer=is_timmer;
    }

    public boolean isIs_timmer() {
        return is_timmer;
    }

    public void setIs_timmer(boolean is_timmer) {
        this.is_timmer = is_timmer;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public int getInde() {
        return inde;
    }

    public long getIn_stock() {
        return in_stock;
    }

    public void setIn_stock(long in_stock) {
        this.in_stock = in_stock;
    }

    public String getItemcount() {
        return itemcount;
    }

    public void setItemcount(String itemcount) {
        this.itemcount = itemcount;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCutprice() {
        return cutprice;
    }

    public void setCutprice(String cutprice) {
        this.cutprice = cutprice;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
