package com.softjourn.sj_coin.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Ad1 on 29.07.2016.
 */
@Data
public class Session {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("expires_in")
    private String expireIn;

}
