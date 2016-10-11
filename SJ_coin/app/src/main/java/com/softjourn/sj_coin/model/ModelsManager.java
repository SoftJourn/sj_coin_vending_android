package com.softjourn.sj_coin.model;

import android.support.annotation.Nullable;

import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class ModelsManager {

    public static History getHistoryModel(@Nullable MyLastPurchase myLastPurchaseModel) {
        if (myLastPurchaseModel != null) {
            History history = new History();
            history.setDate(TimeUtils.getPrettyTime(myLastPurchaseModel.getTime()));
            history.setName(myLastPurchaseModel.getName());
            history.setPrice(String.valueOf(myLastPurchaseModel.getPrice()));
            return history;
        } else {
            return null;
        }
    }

    public static List<History> getHistoryList(@Nullable List<MyLastPurchase> list){
        if (list != null){
            List<History> historyList = new ArrayList<>();
            for(MyLastPurchase myLastPurchase: list){
                historyList.add(getHistoryModel(myLastPurchase));
            }
            return historyList;
        } else {
            return null;
        }
    }
}
