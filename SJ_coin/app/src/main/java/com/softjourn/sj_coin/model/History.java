package com.softjourn.sj_coin.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public class History implements Parcelable{

    @SerializedName("date")
    private String date;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private String price;

    public History(){
    }

    protected History(Parcel in) {
        date = in.readString();
        name = in.readString();
        price = in.readString();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(name);
        dest.writeString(price);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
