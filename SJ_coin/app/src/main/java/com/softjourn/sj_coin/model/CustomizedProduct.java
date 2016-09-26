package com.softjourn.sj_coin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CustomizedProduct implements Parcelable {
    @SerializedName("id")
    public Integer id;

    @SerializedName("price")
    public Integer price;

    @SerializedName("name")
    public String name;

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("description")
    public String description;

    @SerializedName("category")
    public String category;

    public CustomizedProduct(){}

    protected CustomizedProduct(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        category = in.readString();
    }

    public static final Creator<CustomizedProduct> CREATOR = new Creator<CustomizedProduct>() {
        @Override
        public CustomizedProduct createFromParcel(Parcel in) {
            return new CustomizedProduct(in);
        }

        @Override
        public CustomizedProduct[] newArray(int size) {
            return new CustomizedProduct[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeString(category);
    }
}
