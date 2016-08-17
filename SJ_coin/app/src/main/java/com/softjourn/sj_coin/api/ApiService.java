package com.softjourn.sj_coin.api;

import com.softjourn.sj_coin.model.Balance;
import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

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

    @FormUrlEncoded
    @POST("oauth/token")
    Call<Session> getAccessTokenViaRefreshToken(
            @Field("refresh_token") String refreshToken,
            @Field("grant_type") String grantType);

    @GET("v1/machines")
    Call<List<Machines>> getMachines();

    @GET("v1/machines/{id}")
    Call<Machines> getConcreteMachine(@Path("id") String machineID);

    @GET("v1/machines/{machineID}/products")
    Call<List<Product>> getProductsList(@Path("machineID") String machineID);

    @GET("api/v1/amount")
    Call<Balance> getBalance();
}
