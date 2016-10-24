package com.softjourn.sj_coin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Data;

@Data
public class History extends RealmObject implements Parcelable {

    @SerializedName("name")
    private String name;
    @SerializedName("time")
    private String time;
    @SerializedName("price")
    private Integer price;

    protected History(Parcel in) {
        time = in.readString();
        name = in.readString();
        price = in.readInt();
    }

    public History(){

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
        dest.writeString(time);
        dest.writeString(name);
        dest.writeInt(price);
    }
}