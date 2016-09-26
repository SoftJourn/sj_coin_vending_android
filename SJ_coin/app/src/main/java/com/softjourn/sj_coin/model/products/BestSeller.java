package com.softjourn.sj_coin.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.softjourn.sj_coin.model.CustomizedProduct;

import lombok.Data;

public class BestSeller extends CustomizedProduct implements Parcelable{

    protected BestSeller(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BestSeller> CREATOR = new Creator<BestSeller>() {
        @Override
        public BestSeller createFromParcel(Parcel in) {
            return new BestSeller(in);
        }

        @Override
        public BestSeller[] newArray(int size) {
            return new BestSeller[size];
        }
    };
}
