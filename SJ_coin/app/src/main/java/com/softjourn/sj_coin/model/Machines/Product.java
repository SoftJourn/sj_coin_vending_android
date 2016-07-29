package com.softjourn.sj_coin.model.Machines;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Ad1 on 29.07.2016.
 */
@Data
public class Product {
    @SerializedName("id")
    public Integer id;

    @SerializedName("price")
    public Double price;

    @SerializedName("name")
    public String name;

    @SerializedName("imageUrl")
    public String imageUrl;
}
