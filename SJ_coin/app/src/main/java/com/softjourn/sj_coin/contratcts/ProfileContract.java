package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;
import com.softjourn.sj_coin.model.History;
import com.softjourn.sj_coin.model.accountInfo.Account;

import java.util.List;

public interface ProfileContract {

    interface View extends BaseView {

        void showBalance(Account account);

        void setUserName(String userName);

        void setData(List<History> data);

    }

    interface Presenter extends BasePresenter {

        void getAccount();

        void getBalance();

        void getHistory();

        boolean checkExpirationDate();

    }

    interface Model {

        void makeAccountCall();

        void makeBalanceCall();

        List<History> loadHistory();

    }
}
