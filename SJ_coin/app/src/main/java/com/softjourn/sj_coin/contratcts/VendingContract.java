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

        void loadData(List<Drink> drinks,List<Snack> snacks);

        void loadLastAddedData(List<LastAdded> data);

        void loadBestSellerData(List<BestSeller> data);

        void loadSnackData(List<Snack> data);

        void loadDrinkData(List<Drink> data);

        void navigateToBuyProduct(CustomizedProduct product);

        void navigateToFragments();

        void setSortedData(List<CustomizedProduct> product);

        void loadUserBalance();

        void updateBalanceAmount(String amount);

        void changeFavoriteIcon();

        void loadFavorites(List<CustomizedProduct> data);
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

        void addToFavorite(String id);

        void sortByName(List<CustomizedProduct> product, boolean isSortingForward);

        void sortByPrice(List<CustomizedProduct> product, boolean isSortingForward);

        void getBalance();

        void removeFromFavorite(String id);

        void getLocalFavorites();
    }
}
