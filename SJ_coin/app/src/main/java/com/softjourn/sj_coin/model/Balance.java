package com.softjourn.sj_coin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Ad1 on 10.08.2016.
 */
@Data
public class Balance implements Parcelable{

    public static final Creator<Balance> CREATOR = new Creator<Balance>() {
        @Override
        public Balance createFromParcel(Parcel in) {
            return new Balance(in);
        }

        @Override
        public Balance[] newArray(int size) {
            return new Balance[size];
        }
    };
    @SerializedName("amount")
    private String amount;

    protected Balance(Parcel in) {
        amount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
