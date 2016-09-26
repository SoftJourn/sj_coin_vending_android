package com.softjourn.sj_coin.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public class Session {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("expires_in")
    private String expireIn;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getExpireIn() {
        return expireIn;
    }
}
