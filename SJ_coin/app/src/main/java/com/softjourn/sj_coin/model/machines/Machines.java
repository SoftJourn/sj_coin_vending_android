package com.softjourn.sj_coin.model.machines;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public class Machines implements Parcelable {

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

    @SerializedName("id")
    public Integer id;
    @SerializedName("name")
    public String name;
    @SerializedName("size")
    public Size size;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Size getSize() {
        return size;
    }

    protected Machines(Parcel in) {
        name = in.readString();
        id = in.readInt();
        size = in.readParcelable(Size.class.getClassLoader());
    }

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
