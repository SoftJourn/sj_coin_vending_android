package com.softjourn.sj_coin.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.NewProduct;
import com.softjourn.sj_coin.model.products.Snack;

public class CustomizedProduct implements Parcelable {

    private int id;
    private int price;
    private String name;
    private String imageUrl;
    private String description;
    private String category;

    public CustomizedProduct(){}

    public CustomizedProduct(NewProduct product){
        this.id = product.id;
        this.description = product.description;
        this.name = product.name;
        this.price = product.price;
        this.imageUrl = product.imageUrl;
        this.category = product.category;
    }

    public CustomizedProduct(MyLastPurchase product){
        this.id = product.id;
        this.description = product.description;
        this.name = product.name;
        this.price = product.price;
        this.imageUrl = product.imageUrl;
        this.category = product.category;
    }

    public CustomizedProduct(BestSeller product){
        this.id = product.id;
        this.description = product.description;
        this.name = product.name;
        this.price = product.price;
        this.imageUrl = product.imageUrl;
        this.category = product.category;
    }

    public CustomizedProduct(Snack product){
        this.id = product.id;
        this.description = product.description;
        this.name = product.name;
        this.price = product.price;
        this.imageUrl = product.imageUrl;
        this.category = product.category;
    }

    public CustomizedProduct(Drink product){
        this.id = product.id;
        this.description = product.description;
        this.name = product.name;
        this.price = product.price;
        this.imageUrl = product.imageUrl;
        this.category = product.category;
    }



    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    protected CustomizedProduct(Parcel in) {
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
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(price);
        parcel.writeString(name);
        parcel.writeString(imageUrl);
        parcel.writeString(description);
        parcel.writeString(category);
    }

    public static final Creator<CustomizedProduct> CREATOR = new Creator<CustomizedProduct>() {
        @Override
        public CustomizedProduct createFromParcel(Parcel in) {
            return new CustomizedProduct(in);
        }

        @Override
        public CustomizedProduct[] newArray(int size) {
            return new CustomizedProduct[size];
        }
    };
}
