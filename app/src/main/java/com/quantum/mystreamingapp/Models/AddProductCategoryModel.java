package com.quantum.mystreamingapp.Models;

public class AddProductCategoryModel {
    String category;

    public AddProductCategoryModel(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
