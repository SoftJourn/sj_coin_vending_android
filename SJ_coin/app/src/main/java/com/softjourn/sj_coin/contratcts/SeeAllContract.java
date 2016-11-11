package com.softjourn.sj_coin.contratcts;


import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;
import com.softjourn.sj_coin.model.products.Categories;
import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

public interface SeeAllContract {

    interface View extends BaseView{

        void navigateToBuyProduct(Product product);

        void showSnackBar();

        void logOut();
    }

    interface Presenter extends BasePresenter{

        void removeFromFavorite(String id);

        void addToFavorite(int id);

        boolean isProductInMachine(int id);

        List<Categories> getCategories();
    }
}
