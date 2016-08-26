package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;
import com.softjourn.sj_coin.model.Account;

public interface ProfileContract {

    interface View extends BaseView {

        void showBalance(Account account);

        void setUserName(String userName);

        void setPhoto(Account account);

    }

    interface Presenter extends BasePresenter {

        void getAccount();

        boolean checkExpirationDate();

    }

    interface Model {

        void makeAccountCall();

    }
}
