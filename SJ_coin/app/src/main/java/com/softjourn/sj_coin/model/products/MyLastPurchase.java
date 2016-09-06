package com.softjourn.sj_coin.model.products;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MyLastPurchase implements Parcelable{
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

    protected MyLastPurchase(Parcel in) {
        id = in.readInt();
        price = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        category = in.readString();
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
        parcel.writeString(description);
        parcel.writeString(category);
    }

    public static final Parcelable.Creator<MyLastPurchase> CREATOR = new Parcelable.Creator<MyLastPurchase>() {
        @Override
        public MyLastPurchase createFromParcel(Parcel in) {
            return new MyLastPurchase(in);
        }

        @Override
        public MyLastPurchase[] newArray(int size) {
            return new MyLastPurchase[size];
        }
    };
}
