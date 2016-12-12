package com.softjourn.sj_coin.api.auth;

import com.softjourn.sj_coin.api.models.Session;

import retrofit2.Callback;

public interface OAuthApiProvider {

    void makeLoginRequest(String email, String password, String type, Callback<Session> callback);

    Session makeRefreshToken(String refreshToken, String type);

    void makeRevokeRefreshToken(String refreshToken, Callback<Void> callback);
}
