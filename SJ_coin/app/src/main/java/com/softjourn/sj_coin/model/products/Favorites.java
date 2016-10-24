package com.softjourn.sj_coin.model.products;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Data;

@Data
public class Favorites extends RealmObject {

    @SerializedName("id")
    public Integer id;

    @SerializedName("price")
    private Integer price;

    @SerializedName("name")
    private String name;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("description")
    private String description;
}
