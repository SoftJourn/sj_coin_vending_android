package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;
import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

public interface VendingContract {

    interface View extends BaseView{

        void showProgress(String message);

        void hideProgress();

        void navigateToBuyProduct(Product product);

        void navigateToFragments();

        void setSortedData(List<Product> product);

        void loadUserBalance();

        void updateBalanceAmount(String amount);

        void changeFavoriteIcon();

        void loadData(List<Product> data);
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

        void sortByName(String productsCategory, boolean isSortingForward);

        void sortByPrice(String productsCategory, boolean isSortingForward);

        void getBalance();

        void removeFromFavorite(String id);

        void getLocalFavorites();
    }
}
