package com.softjourn.sj_coin.api.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Session {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("expires_in")
    private String expireIn;
}
