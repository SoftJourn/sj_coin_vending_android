package com.softjourn.sj_coin.model.machines;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Machines implements Parcelable {


    @SerializedName("id")
    public Integer id;
    @SerializedName("name")
    public String name;
    @SerializedName("size")
    public Size size;

    private Machines(Parcel in) {
        name = in.readString();
        size = in.readParcelable(Size.class.getClassLoader());
    }

    public static final Creator<Machines> CREATOR = new Creator<Machines>() {
        @Override
        public Machines createFromParcel(Parcel in) {
            return new Machines(in);
        }

        @Override
        public Machines[] newArray(int size) {
            return new Machines[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeParcelable(this.size,i);
    }
}
