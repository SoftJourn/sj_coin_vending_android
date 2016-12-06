package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BaseView;

public interface PurchaseContract {

    interface View extends BaseView{
        void activateProgressBar();
    }
    interface Presenter {
        void buyProduct(String id);

        void buyAfterRefresh();

        void onDestroy();
    }
}
