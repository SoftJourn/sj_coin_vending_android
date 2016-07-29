package com.softjourn.sj_coin.api;

import com.softjourn.sj_coin.model.Session;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Ad1 on 29.07.2016.
 */
public interface ApiInterface {

    @FormUrlEncoded
    @POST("oauth/token")
    Call<Session> getAccessToken(
            @Field("login") String login,
            @Field("password") String password,
            @Field("grant_type") String grantType);
}
