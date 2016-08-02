package com.softjourn.sj_coin.api;

/**
 * Created by Ad1 on 02.08.2016.
 */
public class ApiManager {

    private static ApiManager instance;
    private static ApiProvider mApiProvider;


    public ApiManager(String inHeaders, String inURL){
        mApiProvider = new ApiClient(inHeaders,inURL);
    }

    public static ApiManager getInstance(String mHeaders,String mURL) {
        instance = new ApiManager(mHeaders,mURL);

        if (instance == null) {
            return new ApiManager(mHeaders,mURL);
        } else {
            return instance;
        }
    }

    public ApiProvider getApiProvider() {
        return mApiProvider;
    }
}
