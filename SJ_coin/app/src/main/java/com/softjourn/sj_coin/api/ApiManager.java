package com.softjourn.sj_coin.api;

import com.softjourn.sj_coin.api.auth.OAuthApiClient;
import com.softjourn.sj_coin.api.auth.OAuthApiProvider;
import com.softjourn.sj_coin.api.coins.CoinsApiClient;
import com.softjourn.sj_coin.api.vending.VendingApiProvider;
import com.softjourn.sj_coin.api.vending.VendingProcessApiClient;

public class ApiManager {

    private static ApiManager mApiManager;
    private static OAuthApiClient mAuthApiClient;
    private static CoinsApiClient mCoinsApiClient;
    private static VendingApiProvider mVendingApiProvider;

    public static ApiManager getInstance() {
        if (mApiManager == null){
            mApiManager = new ApiManager();
            mAuthApiClient = new OAuthApiClient();
            mCoinsApiClient = new CoinsApiClient();
            mVendingApiProvider = new VendingProcessApiClient();
        }
        return mApiManager;
    }

    public OAuthApiProvider getOauthApiProvider() {
        return mAuthApiClient;
    }

    public VendingApiProvider getVendingProcessApiProvider() {
        return mVendingApiProvider;
    }

    public CoinsApiClient getCoinsApiProvider() {
        return mCoinsApiClient;
    }
}
