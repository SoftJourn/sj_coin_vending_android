package com.softjourn.sj_coin.model.Machines;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Ad1 on 29.07.2016.
 */
@Data
public class Field {

    @SerializedName("id")
    private Integer id;

    @SerializedName("internalId")
    private String internalId;

    @SerializedName("position")
    private Integer position;

    @SerializedName("count")
    private Integer count;

    @SerializedName("product")
    private Product product;
}
