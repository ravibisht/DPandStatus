package com.stark.dpstatus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TextStatus implements Parcelable {

    public static final String FIREBASE_TEXT_STATUS_PATH = "textStatus";
    public static final Creator<TextStatus> CREATOR = new Creator<TextStatus>() {
        @Override
        public TextStatus createFromParcel(Parcel in) {
            return new TextStatus(in);
        }

        @Override
        public TextStatus[] newArray(int size) {
            return new TextStatus[size];
        }
    };
    private String id;
    private String textStatus;
    private String backgroundColor;
    private String fontColor;
    private int categoryId;

    public TextStatus(String textStatus) {
        this.textStatus = textStatus;
    }


    public TextStatus(String id, String textStatus, String backgroundColor, String fontColor, int categoryId) {
        this.id = id;
        this.textStatus = textStatus;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
        this.categoryId = categoryId;
    }

    public TextStatus(String textStatus, String backgroundColor, String fontColor, int categoryId) {
        this.textStatus = textStatus;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
        this.categoryId = categoryId;
    }

    public TextStatus() {
    }

    protected TextStatus(Parcel in) {
        id = in.readString();
        textStatus = in.readString();
        backgroundColor = in.readString();
        fontColor = in.readString();
        categoryId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(textStatus);
        dest.writeString(backgroundColor);
        dest.writeString(fontColor);
        dest.writeInt(categoryId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextStatus() {
        return textStatus;
    }

    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
