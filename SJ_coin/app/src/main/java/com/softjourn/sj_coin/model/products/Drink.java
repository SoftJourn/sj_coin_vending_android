package com.softjourn.sj_coin.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.softjourn.sj_coin.model.CustomizedProduct;

import lombok.Data;

@Data
public class Drink extends CustomizedProduct implements Parcelable{

    @SerializedName("position")
     public Position position;

    protected Drink(Parcel in) {
        super(in);
        position = in.readParcelable(Position.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(position, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };
}
