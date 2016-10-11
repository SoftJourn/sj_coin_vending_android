package com.softjourn.sj_coin.model.products;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;


@Data
public class Categories extends RealmObject {

    @SerializedName("name")
    private String name;

    @SerializedName("products")
    private RealmList<Product> products;

}
