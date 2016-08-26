package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;
import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

public interface VendingContract {

    interface View extends BaseView{

        void showProgress(String message);

        void hideProgress();

        void loadData(List<Product> data);

        void navigateToBuyProduct(Product product);

    }

    interface Presenter extends BasePresenter{

        void getProductList(String machineID);

        void getLocalProductList();

        boolean checkExpirationDate();

        void buyProduct(String id);
    }

    interface Model{

        void buyProductByID(String id);

        void callMachinesList();

        void callConcreteMachine(String machineID);

        void callProductsList(String machineID);

        List<Product> loadLocalProductList();
    }
}
