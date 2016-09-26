package com.softjourn.sj_coin.api;

import com.softjourn.sj_coin.model.Amount;
import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.model.accountInfo.Account;
import com.softjourn.sj_coin.model.accountInfo.Balance;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Favorites;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.model.products.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    /**
     * OAuth Server endpoints
     */
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


    /**
     * Vending Server endpoints
     */
    @GET("v1/machines")
    Call<List<Machines>> getMachines();

    @GET("v1/machines/{id}")
    Call<Machines> getConcreteMachine(@Path("id") String machineID);

    @GET("v1/machines/{machineID}/features")
    Call<Products> getFeaturedProductsList(@Path("machineID") String machineID);

    @GET("v1/machines/{machineID}/products")
    Call<List<Product>> getProductsList(@Path("machineID") String machineID);

    @POST ("v1/machines/3/products/{id}")
    Call<Amount> buyProductByID(@Path("id") String id);

    @GET ("v1/favorites")
    Call<List<Favorites>> getListFavorites();

    @POST ("v1/favorites/{id}")
    Call<Void> addProductToFavorites(@Path("id") int id);

    @DELETE ("v1/favorites/{id}")
    Call<Void> removeFromFavorites(@Path("id") String id);


    /**
     * Coin server endpoints.
     */
    @GET("api/v1/account")
    Call<Account> getBalance();

    @GET("api/v1/amount")
    Call<Balance> getAmount();
}
