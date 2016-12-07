package com.softjourn.sj_coin.contratcts;

import android.content.Context;

import com.softjourn.sj_coin.base.BaseView;

public interface PurchaseContract {

    interface View extends BaseView{

    }
    interface Presenter {
        void buyProduct(String id, Context context);

        void buyAfterRefresh();

        void onDestroy();
    }
}
