package com.softjourn.sj_coin.api.auth;

import com.softjourn.sj_coin.model.Session;

import retrofit2.Callback;

public interface OAuthApiProvider {

    void makeLoginRequest(String email, String password, String type, Callback<Session> callback);

    void makeRefreshToken(String refreshToken, String type, Callback<Session> callback);

    void makeRevokeRefreshToken(String refreshToken, Callback<Void> callback);
}
