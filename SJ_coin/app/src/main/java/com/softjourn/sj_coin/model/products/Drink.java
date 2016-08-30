package com.softjourn.sj_coin.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Drink implements Parcelable{

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

    @SerializedName("position")
     public Position position;

    protected Drink(Parcel in) {
        id = in.readInt();
        price = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        category = in.readString();
        position = in.readParcelable(Position.class.getClassLoader());
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
        parcel.writeParcelable(this.position,i);
    }

    public static final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {
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
