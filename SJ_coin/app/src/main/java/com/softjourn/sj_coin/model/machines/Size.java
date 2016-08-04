package com.softjourn.sj_coin.model.machines;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Size {

    @SerializedName("rows")
    public Integer rows;

    @SerializedName("columns")
    public Integer columns;

}