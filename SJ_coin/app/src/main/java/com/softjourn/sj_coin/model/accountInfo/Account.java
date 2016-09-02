package com.softjourn.sj_coin.model.accountInfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Account implements Parcelable{

    @SerializedName("amount")
    private String amount;

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    @SerializedName("surname")
    private String surname;

    protected Account(Parcel in) {
        amount = in.readString();
        image = in.readString();
        name = in.readString();
        surname = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(amount);
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeString(surname);
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
