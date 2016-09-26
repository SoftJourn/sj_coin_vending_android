package com.softjourn.sj_coin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andriy Ksenych on 16.09.2016.
 */
public class Amount implements Parcelable{
    @SerializedName("amount")
    int amount;

    public int getAmount() {
        return amount;
    }

    protected Amount(Parcel in) {
        amount = in.readInt();
    }

    public static final Creator<Amount> CREATOR = new Creator<Amount>() {
        @Override
        public Amount createFromParcel(Parcel in) {
            return new Amount(in);
        }

        @Override
        public Amount[] newArray(int size) {
            return new Amount[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
    }
}
