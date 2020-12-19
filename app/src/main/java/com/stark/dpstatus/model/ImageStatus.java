package com.stark.dpstatus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageStatus implements Parcelable {
    public static final String FIREBASE_TRENDING_STATUS_PATH = "imageStatus";
    public static final String FIREBASE_IMAGE_STATUS_PATH = "imageStatus";
    public static final Creator<ImageStatus> CREATOR = new Creator<ImageStatus>() {
        @Override
        public ImageStatus createFromParcel(Parcel in) {
            return new ImageStatus(in);
        }

        @Override
        public ImageStatus[] newArray(int size) {
            return new ImageStatus[size];
        }
    };
    private int id;
    private String image;

    public ImageStatus(String image) {
        this.image = image;
    }


    public ImageStatus(int id, String image) {
        this.id = id;
        this.image = image;
    }

    public ImageStatus() {
    }

    protected ImageStatus(Parcel in) {
        id = in.readInt();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image);
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
}
