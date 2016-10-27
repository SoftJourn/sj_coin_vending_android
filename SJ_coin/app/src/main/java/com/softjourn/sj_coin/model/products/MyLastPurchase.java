package com.softjourn.sj_coin.model.products;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import lombok.Data;

@Data
public class MyLastPurchase implements Parcelable, RealmModel{
    @SerializedName("time")
    String time;

    @SerializedName("id")
    private Integer id;

    @SerializedName("price")
    private Integer price;

    @SerializedName("name")
    private String name;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private Category category;

    public MyLastPurchase(){}

    protected MyLastPurchase(Parcel in) {

        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyLastPurchase> CREATOR = new Creator<MyLastPurchase>() {
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