package com.softjourn.sj_coin.api;

import android.text.TextUtils;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.api.models.Session;
import com.softjourn.sj_coin.mvpmodels.LoginModel;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by omartynets on 12.12.2016.
 * Interceptor created for automated Token Refreshing.
 */

public class TokenAuthenticator implements Interceptor {

    private LoginModel mModel = new LoginModel();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (TextUtils.isEmpty(Preferences.retrieveStringObject(Const.ACCESS_TOKEN))) {
            logout();
        }

        //Build new request
        Request.Builder builder = request.newBuilder();

        String token = Preferences.retrieveStringObject(Const.ACCESS_TOKEN); //save token of this request for future
        setAuthHeader(builder, token); //write current token to request

        request = builder.build(); //overwrite old request
        Response response = chain.proceed(request); //perform request, here original request will be executed

        if (response.code() == 401) { //if unauthorized
            String currentToken = Preferences.retrieveStringObject(Const.ACCESS_TOKEN); //get currently stored token

            if (currentToken != null && currentToken.equals(token)) { //compare current token with token that was stored before, if it was not updated - do update
                String code = refreshToken(); //refresh token
            }

            if (Preferences.retrieveStringObject(Const.ACCESS_TOKEN) != null) { //retry requires new auth token,
                setAuthHeader(builder, Preferences.retrieveStringObject(Const.ACCESS_TOKEN)); //set auth token to updated
                request = builder.build();
                return chain.proceed(request); //repeat request with new token
            }
        }
        return response;
    }

    private void logout() {
        Utils.clearUsersData();
        Navigation.goToLoginActivity(App.getContext());
    }

    private void setAuthHeader(Request.Builder builder, String token) {
        if (token != null) //Add Auth token to each request if authorized
            builder.header(Const.HEADER_AUTHORIZATION_KEY, String.format("Bearer %s", token));
    }

    private String refreshToken() {
        Session session = mModel.makeRefreshToken(Preferences.retrieveStringObject(Const.REFRESH_TOKEN));
        if (session != null) {
            return session.getAccessToken();
        } else {
            logout();
            return "";
        }
    }
}
