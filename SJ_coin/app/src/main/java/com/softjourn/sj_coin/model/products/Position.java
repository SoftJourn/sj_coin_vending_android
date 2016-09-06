package com.softjourn.sj_coin.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Position implements Parcelable {

    public static final Creator<Position> CREATOR = new Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel in) {
            return new Position(in);
        }

        @Override
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };
    @SerializedName("row")
    public Integer row;
    @SerializedName("column")
    public Integer column;
    @SerializedName("cellName")
    public String cellName;

    protected Position(Parcel in) {
        cellName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cellName);
        parcel.writeInt(column);
        parcel.writeInt(row);
    }
}