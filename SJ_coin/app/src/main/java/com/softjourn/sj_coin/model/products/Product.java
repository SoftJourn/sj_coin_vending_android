package com.softjourn.sj_coin.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Product implements Parcelable {

    @SerializedName("id")
    public Integer id;
    @SerializedName("price")
    public Integer price;
    @SerializedName("name")
    public String name;
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("position")
    public Position position;

    protected Product(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        position = in.readParcelable(Position.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(price);
        parcel.writeString(name);
        parcel.writeString(imageUrl);
        parcel.writeParcelable(this.position,i);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}