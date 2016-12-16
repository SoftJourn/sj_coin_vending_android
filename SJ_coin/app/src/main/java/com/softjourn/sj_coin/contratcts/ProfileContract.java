package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.api.models.History;
import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;

import java.util.List;

public interface ProfileContract {

    interface View extends BaseView {

        void showBalance(String amount);

        void setUserName(String userName);

        void setData(List<History> history);

        void onCreateErrorDialog(String message);

        void logOut();

    }

    interface Presenter extends BasePresenter {

        void getAccount();

        /**
         * get History of purchases from server
         * and sets it as List to History adapter
         */
        void showHistory();

        void cancelRunningRequest();
    }
}
