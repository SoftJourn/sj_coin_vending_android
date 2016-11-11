package com.softjourn.sj_coin.MVPmodels;


import android.util.Log;

import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.auth.OAuthApiProvider;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.callbacks.OnLoginCallEvent;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.callbacks.OnTokenRevoked;
import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel extends BaseModel{

    private OAuthApiProvider mApiProvider;

    public void makeRefreshToken(String refreshToken) {
        createApiManager();

        Callback<Session> callback = new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnTokenRefreshed(Const.TOKEN_NOT_REFRESHED));
                } else {
                    Utils.storeSessionInfo(response.body());
                    mEventBus.post(new OnTokenRefreshed(Const.TOKEN_REFRESHED));
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                mEventBus.post(new OnTokenRefreshed(Const.TOKEN_NOT_REFRESHED));

            }
        };
        mApiProvider.makeRefreshToken(refreshToken, Const.GRANT_TYPE_REFRESH_TOKEN, callback);
    }



    public void makeLoginCall(String userName, String password) {

        createApiManager();

        Callback<Session> callback = new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnLoginCallEvent(Const.CALL_FAILED));
                } else {
                    Utils.storeSessionInfo(response.body());
                    mEventBus.post(new OnLoginCallEvent(Const.CALL_SUCCEED));
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                mEventBus.post(new OnLoginCallEvent(Const.CALL_FAILED));
            }
        };
        mApiProvider.makeLoginRequest(userName, password, Const.GRANT_TYPE_PASSWORD, callback);
    }

    public void revokeRefreshToken(String refreshToken) {
        createApiManager();

        Callback<Void> callback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnTokenRevoked(false));
                    Log.d("Tag", ""+response.code());
                } else {
                    mEventBus.post(new OnTokenRevoked(true));
                    Log.d("Tag", ""+response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mEventBus.post(new OnTokenRevoked(false));
                Log.d("Tag", t.toString());
            }
        };
        mApiProvider.makeRevokeRefreshToken(refreshToken, callback);
    }

    private void createApiManager() {
        mApiProvider = ApiManager.getInstance().getOauthApiProvider();
    }
}
