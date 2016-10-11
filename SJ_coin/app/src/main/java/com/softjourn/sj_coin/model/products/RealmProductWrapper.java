package com.softjourn.sj_coin.model.products;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class RealmProductWrapper {
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

    public RealmProductWrapper(Product product){
        this.id = product.getId();
        this.price = product.getPrice();
        this.name = product.getName();
        this.imageUrl = product.getImageUrl();
        this.description = product.getDescription();
    }
}
