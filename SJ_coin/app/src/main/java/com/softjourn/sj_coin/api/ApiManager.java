package com.softjourn.sj_coin.api;

import com.softjourn.sj_coin.api.auth.OAuthApiClient;
import com.softjourn.sj_coin.api.auth.OAuthApiProvider;
import com.softjourn.sj_coin.api.coins.CoinsApiClient;
import com.softjourn.sj_coin.api.vending.VendingApiProvider;
import com.softjourn.sj_coin.api.vending.VendingProcessApiClient;

public class ApiManager {

    public ApiManager() {
    }

    public static ApiManager getInstance() {
        return new ApiManager();
    }

    public OAuthApiProvider getOauthApiProvider() {
        return new OAuthApiClient();
    }

    public VendingApiProvider getVendingProcessApiProvider() {
        return new VendingProcessApiClient();
    }

    public CoinsApiClient getCoinsApiProvider() {
        return new CoinsApiClient();
    }
}
