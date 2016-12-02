package com.softjourn.sj_coin.api.models.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Featured implements Parcelable {

    @SerializedName("lastAdded")
    private List<Integer> lastAdded = new ArrayList<>();

    @SerializedName("bestSellers")
    private List<Integer> bestSellers = new ArrayList<>();

    @SerializedName("categories")
    private List<Categories> categories = new ArrayList<>();

    public Featured(){}

    protected Featured(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<Featured> CREATOR = new Creator<Featured>() {
        @Override
        public Featured createFromParcel(Parcel in) {
            return new Featured(in);
        }

        @Override
        public Featured[] newArray(int size) {
            return new Featured[size];
        }
    };
}