package com.softjourn.sj_coin.api;

import com.softjourn.sj_coin.api.models.Amount;
import com.softjourn.sj_coin.api.models.History;
import com.softjourn.sj_coin.api.models.Session;
import com.softjourn.sj_coin.api.models.accountInfo.Account;
import com.softjourn.sj_coin.api.models.accountInfo.Balance;
import com.softjourn.sj_coin.api.models.accountInfo.Cash;
import com.softjourn.sj_coin.api.models.accountInfo.DepositeTransaction;
import com.softjourn.sj_coin.api.models.machines.Machines;
import com.softjourn.sj_coin.api.models.products.Favorites;
import com.softjourn.sj_coin.api.models.products.Featured;
import com.softjourn.sj_coin.api.models.products.Product;

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


    @FormUrlEncoded
    @POST("oauth/token/revoke")
    Call<Void> revokeRefreshToken(
            @Field("token_value") String refreshToken);

    /**
     * Vending Server endpoints
     */
    @GET("machines")
    Call<List<Machines>> getMachines();

    @GET("machines/{id}")
    Call<Machines> getConcreteMachine(@Path("id") String machineID);

    @GET("machines/{machineID}/features")
    Call<Featured> getFeaturedProductsList(@Path("machineID") String machineID);

    @GET("machines/{machineID}/products")
    Call<List<Product>> getProductsList(@Path("machineID") String machineID);

    @POST ("machines/{machineID}/products/{id}")
    Call<Amount> buyProductByID(@Path("machineID") String machineID, @Path("id") String id);

    @GET ("favorites")
    Call<List<Favorites>> getListFavorites();

    @POST ("favorites/{id}")
    Call<Void> addProductToFavorites(@Path("id") int id);

    @DELETE ("favorites/{id}")
    Call<Void> removeFromFavorites(@Path("id") String id);

    @GET ("machines/last")
    Call<List<History>> getPurchaseHistory();


    /**
     * Coin server endpoints.
     */
    @GET("account")
    Call<Account> getBalance();

    @GET("amount")
    Call<Balance> getAmount();

    @POST("deposit")
    Call<DepositeTransaction> putMoneyInWallet(@Body Cash scannedCode);
}
