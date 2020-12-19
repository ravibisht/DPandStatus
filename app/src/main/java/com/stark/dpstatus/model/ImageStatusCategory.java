package com.stark.dpstatus.model;

public class ImageStatusCategory {

    public static final String FIREBASE_IMAGE_STATUS_CATEGORY_PATH = "imageCategory";

    private int id;
    private String image;
    private String categoryName;

    public ImageStatusCategory(String image, String categoryName) {
        this.image = image;
        this.categoryName = categoryName;
    }

    public ImageStatusCategory(int id, String image, String categoryName) {
        this.id = id;
        this.image = image;
        this.categoryName = categoryName;
    }

    public ImageStatusCategory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ImageStatusCategory{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
