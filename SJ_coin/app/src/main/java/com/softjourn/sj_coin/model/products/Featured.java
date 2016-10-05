package com.softjourn.sj_coin.model.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.softjourn.sj_coin.realm.realmTypes.RealmInteger;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

@Data
public class Featured extends RealmObject implements Parcelable {

    @SerializedName("lastAdded")
    private RealmList<RealmInteger> lastAdded = new RealmList<>();

    @SerializedName("bestSellers")
    private RealmList<RealmInteger> bestSellers = new RealmList<>();

    @SerializedName("categories")
    private RealmList<Categories> categories = new RealmList<>();

    public Featured(){}

    protected Featured(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<Featured> CREATOR = new Creator<Featured>() {
        @Override
        public Featured createFromParcel(Parcel in) {
            return new Featured(in);
        }

        @Override
        public Featured[] newArray(int size) {
            return new Featured[size];
        }
    };
}