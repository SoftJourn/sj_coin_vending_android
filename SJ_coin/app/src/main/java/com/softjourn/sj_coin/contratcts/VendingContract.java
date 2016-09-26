package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.LastAdded;
import com.softjourn.sj_coin.model.products.Snack;

import java.util.List;

public interface VendingContract {

    interface View extends BaseView{

        void showProgress(String message);

        void hideProgress();

        void loadData(List<? extends CustomizedProduct> drinks,List<? extends CustomizedProduct> snacks);

        void navigateToBuyProduct(CustomizedProduct product);

        void navigateToFragments();

        void setSortedData(List<? extends CustomizedProduct> product);

        void loadUserBalance();

        void updateBalanceAmount(String amount);

        void changeFavoriteIcon();

        void loadData(List<? extends CustomizedProduct> data);
    }

    interface Presenter extends BasePresenter{

        void getFeaturedProductsList(String machineID);

        void getLocalFeaturedProductsList();

        void getLocalLastAddedProducts();

        void getLocalBestSellers();

        void getLocalSnacks();

        void getLocalDrinks();

        void getProductList(String machineID);

        void getLocalProductList();

        boolean checkExpirationDate();

        void buyProduct(String id);

        void addToFavorite(int id);

        void sortByName(List<? extends CustomizedProduct> product, boolean isSortingForward);

        void sortByPrice(List<? extends CustomizedProduct> product, boolean isSortingForward);

        void getBalance();

        void removeFromFavorite(String id);

        void getLocalFavorites();
    }
}
