package com.softjourn.sj_coin.api.models.accountInfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by omartynets on 02.12.2016.
 */

@Data
public class Cash implements Parcelable {

    @SerializedName("tokenContractAddress")
    public String tokenContractAddress;

    @SerializedName("offlineContractAddress")
    public String offlineContractAddress;

    @SerializedName("chequeHash")
    public String chequeHash;

    @SerializedName("amount")
    public Integer amount;

    public Cash() {
    }

    protected Cash(Parcel in) {
        tokenContractAddress = in.readString();
        offlineContractAddress = in.readString();
        chequeHash = in.readString();
    }

    public static final Creator<Cash> CREATOR = new Creator<Cash>() {
        @Override
        public Cash createFromParcel(Parcel in) {
            return new Cash(in);
        }

        @Override
        public Cash[] newArray(int size) {
            return new Cash[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tokenContractAddress);
        parcel.writeString(offlineContractAddress);
        parcel.writeString(chequeHash);
    }
}

