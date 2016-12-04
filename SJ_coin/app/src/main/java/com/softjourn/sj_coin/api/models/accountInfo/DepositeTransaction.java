package com.softjourn.sj_coin.api.models.accountInfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by omartynets on 02.12.2016.
 */

@Data
public class DepositeTransaction implements Parcelable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("account")
    private String account;

    @SerializedName("destination")
    private Object destination;

    @SerializedName("amount")
    private Object amount;

    @SerializedName("comment")
    private String comment;

    @SerializedName("created")
    private String created;

    @SerializedName("status")
    private String status;

    @SerializedName("remain")
    private Integer remain;

    @SerializedName("error")
    private Object error;

    protected DepositeTransaction(Parcel in) {
        account = in.readString();
        comment = in.readString();
        created = in.readString();
        status = in.readString();
    }

    public static final Creator<DepositeTransaction> CREATOR = new Creator<DepositeTransaction>() {
        @Override
        public DepositeTransaction createFromParcel(Parcel in) {
            return new DepositeTransaction(in);
        }

        @Override
        public DepositeTransaction[] newArray(int size) {
            return new DepositeTransaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(account);
        parcel.writeString(comment);
        parcel.writeString(created);
        parcel.writeString(status);
    }
}
