package com.softjourn.sj_coin.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.softjourn.sj_coin.model.CustomizedProduct;

public class LastAdded extends CustomizedProduct implements Parcelable{

    protected LastAdded(Parcel in) {
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

    public static final Creator<LastAdded> CREATOR = new Creator<LastAdded>() {
        @Override
        public LastAdded createFromParcel(Parcel in) {
            return new LastAdded(in);
        }

        @Override
        public LastAdded[] newArray(int size) {
            return new LastAdded[size];
        }
    };
}
