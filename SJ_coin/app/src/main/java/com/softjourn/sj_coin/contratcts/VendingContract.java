package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.NewProduct;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.model.products.Products;
import com.softjourn.sj_coin.model.products.Snack;

import java.util.List;

public interface VendingContract {

    interface View extends BaseView{

        void showProgress(String message);

        void hideProgress();

        void loadData(List<Drink> drinks,List<Snack> snacks);

        void loadNewProductsData(List<NewProduct> data);

        void loadBestSellerData(List<BestSeller> data);

        void loadMyLastPurchaseData(List<MyLastPurchase> data);

        void loadSnackData(List<Snack> data);

        void loadDrinkData(List<Drink> data);

        void navigateToBuyProduct(CustomizedProduct product);

        void navigateToFragments();

        void setSortedData(List<CustomizedProduct> product);

    }

    interface Presenter extends BasePresenter{

        void getFeaturedProductsList(String machineID);

        void getLocalFeaturedProductsList();

        void getLocalNewProducts();

        void getLocalBestSellers();

        void getLocalMyLastPurchase();

        void getLocalSnacks();

        void getLocalDrinks();

        void getProductList(String machineID);

        void getLocalProductList();

        boolean checkExpirationDate();

        void buyProduct(String id);

        void sortByName(List<CustomizedProduct> product, boolean isSortingForward);

        void sortByPrice(List<CustomizedProduct> product, boolean isSortingForward);
    }

    interface Model{

        void buyProductByID(String id);

        void callMachinesList();

        void callConcreteMachine(String machineID);

        void callFeaturedProductsList(String machineID);

        void callProductsList(String machineID);

        List<Product> loadLocalProductList();

        Products loadLocalFeaturedProductList();

        List<BestSeller> loadBestSellers();

        List<NewProduct> loadNewProduct();

        List<MyLastPurchase> loadMyLastPurchase();

        List<Drink> loadDrink();

        List<Snack> loadSnack();

        List<CustomizedProduct> sortByName(List<CustomizedProduct> product, boolean isSortingForward);

        List<CustomizedProduct> sortByPrice(List<CustomizedProduct> product, boolean isSortingForward);
    }
}
