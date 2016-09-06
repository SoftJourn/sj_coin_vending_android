package com.softjourn.sj_coin.MVPmodels;


import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.auth.OAuthApiProvider;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.callbacks.OnCallEvent;
import com.softjourn.sj_coin.callbacks.OnLoginCallEvent;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.utils.Constants;
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
                    mEventBus.post(new OnTokenRefreshed(Constants.TOKEN_NOT_REFRESHED));
                } else {
                    Utils.storeSessionInfo(response.body());
                    mEventBus.post(new OnTokenRefreshed(Constants.TOKEN_REFRESHED));
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                mEventBus.post(new OnTokenRefreshed(Constants.TOKEN_NOT_REFRESHED));
            }
        };
        mApiProvider.makeRefreshToken(refreshToken, Constants.GRANT_TYPE_REFRESH_TOKEN, callback);
    }



    public void makeLoginCall(String userName, String password) {

        createApiManager();

        Callback<Session> callback = new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnLoginCallEvent(Constants.CALL_FAILED));
                } else {
                    Utils.storeSessionInfo(response.body());
                    mEventBus.post(new OnLoginCallEvent(Constants.CALL_SUCCEED));
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                mEventBus.post(new OnCallEvent(Constants.CALL_FAILED));
            }
        };
        mApiProvider.makeLoginRequest(userName, password, Constants.GRANT_TYPE_PASSWORD, callback);
    }

    public void createApiManager() {
        mApiProvider = ApiManager.getInstance().getOauthApiProvider();
    }
}