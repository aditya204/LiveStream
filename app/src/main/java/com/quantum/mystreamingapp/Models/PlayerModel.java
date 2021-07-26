package com.quantum.mystreamingapp.Models;

public class PlayerModel {
    String url,image,user_name,time,profile_pic,product_id;


    public PlayerModel(String url, String image, String user_name, String time, String profile_pic,String product_id) {
        this.url = url;
        this.image = image;
        this.user_name = user_name;
        this.time = time;
        this.profile_pic = profile_pic;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
