package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;

public interface LoginContract {

    interface View extends BaseView{

        void setUsernameError();

        void setPasswordError();

        void navigateToMain();
    }

    interface Presenter extends BasePresenter{

        void login(String userName, String password);
    }
}
