package com.quantum.mystreamingapp.Models;

public class NotificationAlertModel {
    String image,header,time,description,follower_id,id;
    boolean is_readed;

    public NotificationAlertModel(String image, String header, String time, String description, String follower_id, String id,boolean is_readed) {
        this.image = image;
        this.header = header;
        this.time = time;
        this.description = description;
        this.follower_id = follower_id;
        this.id = id;
        this.is_readed=is_readed;
    }

    public boolean isIs_readed() {
        return is_readed;
    }

    public void setIs_readed(boolean is_readed) {
        this.is_readed = is_readed;
    }

    public String getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(String follower_id) {
        this.follower_id = follower_id;
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
}
