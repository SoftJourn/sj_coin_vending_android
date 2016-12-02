package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.base.BasePresenter;

import java.util.List;

public interface VendingFragmentContract {

    interface View {
        void setSortedData(List<Product> product);

        void loadData(List<Product> data);

        void changeFavoriteIcon(String action);

        void showDataAfterRemovingFavorites(List<Product> productsList);
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
