package com.softjourn.sj_coin.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LastAdded implements Parcelable{
    @SerializedName("id")
    public Integer id;

    @SerializedName("price")
    public Integer price;

    @SerializedName("name")
    public String name;

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("description")
    public String description;

    @SerializedName("category")
    public String category;

    protected LastAdded(Parcel in) {
        id = in.readInt();
        price = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        category = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(price);
        parcel.writeString(name);
        parcel.writeString(imageUrl);
        parcel.writeString(description);
        parcel.writeString(category);
    }

    public static final Parcelable.Creator<LastAdded> CREATOR = new Parcelable.Creator<LastAdded>() {
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
