package com.example.easyticketsdesk.Entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Category {
    private Long categoryId;
    private String categoryName;

    public Category(){

    }

    public Category(Long categoryId, String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category(JSONObject jsonObject) throws JSONException {
        this.categoryId = jsonObject.getLong("id");
        this.categoryName = jsonObject.getString("name");
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
