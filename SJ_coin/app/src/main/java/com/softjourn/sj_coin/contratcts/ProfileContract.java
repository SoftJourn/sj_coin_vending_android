package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;
import com.softjourn.sj_coin.model.Balance;

public interface ProfileContract {

    interface View extends BaseView {

        void showBalance(Balance balance);

        void setUserName(String message);

    }

    interface Presenter extends BasePresenter {

        void getBalance();

        boolean checkExpirationDate();

        void getUserName();
    }

    interface Model {

        void makeBalanceCall();

        String parseUserNameFromToken();
    }
}
