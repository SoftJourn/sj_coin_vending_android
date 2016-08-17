package com.softjourn.sj_coin.api;

public class ApiManager {

    private static ApiManager instance;

    public ApiManager(){
    }

    public static ApiManager getInstance() {
        instance = new ApiManager();

        if (instance == null) {
            return new ApiManager();
        } else {
            return instance;
        }
    }

    public ApiProvider getOauthApiProvider() {
        return new OAuthApiClient();
    }

    public ApiProvider getVendingProcessApiProvider(){
        return new VendingProcessApiClient();
    }

    public ApiProvider getCoinsApiProvider(){
        return new CoinsApiClient();
    }
}
