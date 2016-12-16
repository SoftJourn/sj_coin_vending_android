package com.softjourn.sj_coin.api.models.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Product implements Parcelable{

    @SerializedName("id")
    private Integer id;

    @SerializedName("price")
    private Integer price;

    @SerializedName("name")
    private String name;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private Category category;


    public Product(){}

    public Product(Favorites favorites){
        this.id = favorites.getId();
        this.price = favorites.getPrice();
        this.name = favorites.getName();
        this.imageUrl = favorites.getImageUrl();
        this.description = favorites.getDescription();
    }

    protected Product(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        category = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeParcelable(this.category,flags);
    }
}