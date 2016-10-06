package com.softjourn.sj_coin.model.products;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Data;

@Data
public class Favorites extends RealmObject {

    @SerializedName("id")
    public Integer id;
}
