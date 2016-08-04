package com.softjourn.sj_coin.model.machines;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Machines {

    @SerializedName("id")
    public Integer id;

    @SerializedName("name")
    public String name;

    @SerializedName("size")
    public Size size;
}
