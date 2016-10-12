package com.softjourn.sj_coin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Data;

@Data
public class History extends RealmObject implements Parcelable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("time")
    private String time;

    protected History(Parcel in) {
        time = in.readString();
        id = in.readInt();
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
        dest.writeInt(id);
    }
}