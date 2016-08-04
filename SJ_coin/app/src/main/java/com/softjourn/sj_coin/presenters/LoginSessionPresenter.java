package com.softjourn.sj_coin.presenters;

import android.util.Log;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.callbacks.OnLogin;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ad1 on 02.08.2016.
 */
public class LoginSessionPresenter extends BasePresenter implements ILoginSessionPresenter, Constants {

    private String mEmail;
    private String mPassword;

    public LoginSessionPresenter(String email, String password) {
        this.mEmail = email;
        this.mPassword = password;
    }

    @Override
    public void callLogin() {

        createApiManager(OAUTH, URL_AUTH_SERVICE);

        Callback<Session> callback = new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (!response.isSuccessful()) {
                    Log.d("Tag",""+response.code());
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                    mEventBus.post(new OnLogin(CALL_FAILED));
                } else {

                    Preferences.storeObject(ACCESS_TOKEN, response.body().getAccessToken());
                    Preferences.storeObject(REFRESH_TOKEN, response.body().getRefreshToken());

                    mEventBus.post(new OnLogin(CALL_SUCCEED));
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                Log.d("Tag",t.getMessage());
                mEventBus.post(new OnLogin(CALL_FAILED));
            }
        };
        mApiProvider.makeLoginRequest(mEmail, mPassword, GRANT_TYPE, callback);
    }
}
