package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

/**
 * Created by omartynets on 13.10.2016.
 */
public interface VendingFragmentContract {

    interface View {
        void setSortedData(List<Product> product);

        void loadData(List<Product> data);

        void changeFavoriteIcon();
    }

    interface Presenter extends BasePresenter{

        void getLocalProductList();

        void getLocalLastAddedProducts();

        void getLocalBestSellers();

        void getLocalFavorites();

        void getLocalCategoryProducts(String category);

        void sortByName(String productsCategory, boolean isSortingForward);

        void sortByPrice(String productsCategory, boolean isSortingForward);
    }
}
