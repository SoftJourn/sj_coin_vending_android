package com.softjourn.sj_coin.model.products;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.softjourn.sj_coin.model.CustomizedProduct;

import lombok.Data;

@Data
public class MyLastPurchase extends CustomizedProduct implements Parcelable{
    @SerializedName("time")
    String time;

    public String getTime() {
        return time;
    }

    protected MyLastPurchase(Parcel in) {
        super(in);
        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
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
