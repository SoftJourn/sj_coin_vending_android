package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

public interface VendingContract {

    interface View extends BaseView{

        void showProgress(String message);

        void hideProgress();

        void navigateToBuyProduct(Product product);

        void navigateToFragments();

        void loadUserBalance();

        void updateBalanceAmount(String amount);

        void createContainer(String categoryName);

        void showMachinesSelector(List<Machines> machines);

        void loadProductList();

        void getMachinesList();
    }

    interface Presenter extends BasePresenter{

        void isMachineSet();

        void getFeaturedProductsList(String machineID);

        void getFavoritesList();

        void getMachinesList();

        void getBalance();

        void getCategoriesFromDB();
    }
}
