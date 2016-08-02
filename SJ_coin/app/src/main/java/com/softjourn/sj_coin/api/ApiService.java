package com.softjourn.sj_coin.api;

import com.softjourn.sj_coin.model.Machines.Machine;
import com.softjourn.sj_coin.model.Session;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ad1 on 29.07.2016.
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("oauth/token")
    Call<Session> getAccessToken(
            @Field("username") String login,
            @Field("password") String password,
            @Field("grant_type") String grantType);


    @GET("v1/machines/{id}")
    Call<Machine> getMachine(@Path("id") String id);
}
