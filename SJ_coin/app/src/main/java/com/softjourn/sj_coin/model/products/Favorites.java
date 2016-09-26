package com.softjourn.sj_coin.model.products;


import com.google.gson.annotations.SerializedName;

import lombok.Data;

public class Favorites {

    @SerializedName("id")
    public Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
