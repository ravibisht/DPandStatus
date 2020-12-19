package com.stark.dpstatus.model;

public class TextStatusCategory {

    private String id;
    private String categoryName;
    private String categoryImage;
    private String fontColor = "#000000";
    private String backgroundColor = "#ffffff";


    public TextStatusCategory(String id, String categoryName, String image) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryImage = image;
    }

    public TextStatusCategory(String id, String categoryName, String categoryImage, String fontColor, String backgroundColor) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.fontColor = fontColor;
        this.backgroundColor = backgroundColor;
    }

    public TextStatusCategory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
