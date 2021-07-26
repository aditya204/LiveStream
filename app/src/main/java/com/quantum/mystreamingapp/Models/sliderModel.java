package com.quantum.mystreamingapp.Models;

public class sliderModel {

    private String banner;
    private String tags;
    private String background;

    public sliderModel(String banner, String tags, String background) {
        this.banner = banner;
        this.tags=tags;
        this.background=background;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
