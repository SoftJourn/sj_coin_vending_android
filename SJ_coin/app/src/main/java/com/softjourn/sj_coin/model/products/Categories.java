package com.softjourn.sj_coin.model.products;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;


@Data
public class Categories{

    @SerializedName("name")
    private String name;

    @SerializedName("products")
    private List<Product> products;

}
