package com.softjourn.sj_coin.contratcts;


import com.softjourn.sj_coin.api.models.products.Categories;
import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;

import java.util.List;

public interface SeeAllContract {

    interface View extends BaseView{

        void navigateToBuyProduct(Product product);

        void showSnackBar();

        void logOut();
    }

    interface Presenter extends BasePresenter{

        /**
         * Removes product from favorites list
         *
         * @param id = id of product.
         */
        void removeFromFavorite(String id);

        /**
         * Adds product to favorites list
         * @param id = id of product.
         */
        void addToFavorite(int id);

        /**
         * Interface for checking is current product present in chosen Machine.
         * Using for correct appearance of favorites product.
         * @param id = id of product;
         */
        boolean isProductInMachine(int id);

        /**
         * Returns all categories from the server even if there are no products in current machine
         * matching current category.
         */
        List<Categories> getCategories();
    }
}
