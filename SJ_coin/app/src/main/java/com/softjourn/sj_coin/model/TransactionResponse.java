package com.softjourn.sj_coin.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TransactionResponse {

    @SerializedName("code")
    private int responseCode;

}
