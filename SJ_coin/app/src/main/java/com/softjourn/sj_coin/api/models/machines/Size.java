package com.softjourn.sj_coin.api.models.machines;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Size implements Parcelable {

    public static final Creator<Size> CREATOR = new Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel in) {
            return new Size(in);
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };
    @SerializedName("rows")
    private Integer rows;
    @SerializedName("columns")
    private Integer columns;

    Size(Parcel in) {
        rows = in.readInt();
        columns = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(rows);
        parcel.writeInt(columns);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}