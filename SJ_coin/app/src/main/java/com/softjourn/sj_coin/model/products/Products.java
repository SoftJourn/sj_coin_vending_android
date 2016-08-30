package com.softjourn.sj_coin.model.products;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Products implements Parcelable{

    @SerializedName("My lastPurchases")
    public List<MyLastPurchase> myLastPurchases = new ArrayList<MyLastPurchase>();
    @SerializedName("Drink")
    public List<Drink> drink = new ArrayList<Drink>();
    @SerializedName("Snack")
    public List<Snack> snack = new ArrayList<Snack>();
    @SerializedName("New products")
    public List<NewProduct> newProducts = new ArrayList<NewProduct>();
    @SerializedName("Best sellers")
    public List<BestSeller> bestSellers = new ArrayList<BestSeller>();

    protected Products(Parcel in) {
        myLastPurchases = in.createTypedArrayList(MyLastPurchase.CREATOR);
        drink = in.createTypedArrayList(Drink.CREATOR);
        snack = in.createTypedArrayList(Snack.CREATOR);
        newProducts = in.createTypedArrayList(NewProduct.CREATOR);
        bestSellers = in.createTypedArrayList(BestSeller.CREATOR);
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(myLastPurchases);
        dest.writeTypedList(drink);
        dest.writeTypedList(snack);
        dest.writeTypedList(newProducts);
        dest.writeTypedList(bestSellers);
    }
}
