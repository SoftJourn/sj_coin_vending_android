package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;
import com.softjourn.sj_coin.model.History;
import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

public interface ProfileContract {

    interface View extends BaseView {

        void showBalance(String amount);

        void setUserName(String userName);

        void setData(List<History> history, List<Product> products);

    }

    interface Presenter extends BasePresenter {

        void getAccount();

        void showHistory();

        boolean checkExpirationDate();

    }
}
