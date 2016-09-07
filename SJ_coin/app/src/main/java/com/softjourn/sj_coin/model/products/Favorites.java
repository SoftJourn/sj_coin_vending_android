package com.softjourn.sj_coin.model.products;


import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Favorites {

    @SerializedName("id")
    public Integer id;
}
