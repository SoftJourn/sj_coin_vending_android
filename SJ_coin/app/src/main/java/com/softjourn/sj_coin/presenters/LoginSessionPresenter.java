package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.callbacks.OnLogin;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Preferences;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSessionPresenter extends BasePresenter implements ILoginSessionPresenter, Constants {

    private String mEmail;
    private String mPassword;
    private String mRefreshToken;

    public LoginSessionPresenter(String email, String password) {
        this.mEmail = email;
        this.mPassword = password;
    }

    public LoginSessionPresenter(String refreshToken) {
        this.mRefreshToken = refreshToken;
    }

    @Override
    public void callAccessTokenViaRefresh() {
        createApiManager(OAUTH, URL_AUTH_SERVICE);

        Callback<Session> callback = new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                    mEventBus.post(new OnTokenRefreshed(TOKEN_NOT_REFRESHED));
                } else {
                    Preferences.storeObject(ACCESS_TOKEN, response.body().getAccessToken());
                    Preferences.storeObject(REFRESH_TOKEN, response.body().getRefreshToken());
                    Preferences.storeObject(EXPIRATION_DATE, String.valueOf((new Date().getTime() / 1000) + Long.parseLong(response.body().getExpireIn())));
                    mEventBus.post(new OnTokenRefreshed(TOKEN_REFRESHED));
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                mEventBus.post(new OnTokenRefreshed(TOKEN_NOT_REFRESHED));
            }
        };
        mApiProvider.makeRefreshToken(mRefreshToken, GRANT_TYPE_REFRESH_TOKEN, callback);
    }

    @Override
    public void callLogin() {

        createApiManager(OAUTH, URL_AUTH_SERVICE);

        Callback<Session> callback = new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                    mEventBus.post(new OnLogin(CALL_FAILED));
                } else {
                    try {
                        Preferences.storeObject(ACCESS_TOKEN, response.body().getAccessToken());
                        Preferences.storeObject(REFRESH_TOKEN, response.body().getRefreshToken());
                        Preferences.storeObject(EXPIRATION_DATE, String.valueOf((new Date().getTime() / 1000) + Long.parseLong(response.body().getExpireIn())));

                        mEventBus.post(new OnLogin(CALL_SUCCEED));
                    } catch (NullPointerException e) {
                        mEventBus.post(new OnLogin(CALL_FAILED));
                    }
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                mEventBus.post(new OnLogin(CALL_FAILED));
            }
        };
        mApiProvider.makeLoginRequest(mEmail, mPassword, GRANT_TYPE_PASSWORD, callback);
    }
}
