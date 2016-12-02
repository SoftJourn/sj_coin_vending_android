package com.softjourn.sj_coin.contratcts;

import com.softjourn.sj_coin.api.models.machines.Machines;
import com.softjourn.sj_coin.api.models.products.Categories;
import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.base.BaseView;

import java.util.List;

public interface VendingContract {

    interface View extends BaseView{

        void showProgress(String message);

        void hideProgress();

        /**
         * Creates Dialog to confirm or decline purchase
         *
         * @param product = Chosen product
         */
        void navigateToBuyProduct(Product product);

        /**
         * Load all fragments according to their quantity
         *
         */
        void navigateToFragments();

        void loadUserBalance();

        /**
         * Resetting amount after purchase
         * @param amount = response after success purchase
         */
        void updateBalanceAmount(String amount);

        /**
         * Dynamically creates category header according to the categoryName
         * and creates container for fragment.
         * @param categoryName = name of category loaded from the server.
         */
        void createContainer(String categoryName);

        /**
         * Show dialog for choosing machine.
         * By default is choosing after login.
         * If ther4e is only one machine available dialog is not shown and available
         * machine sets as chosen. If there are multiple machines dialog appears and chosen machine
         * sets as chosen
         * @param machines = response from server on call getMachinesList()
         */
        void showMachinesSelector(List<Machines> machines);

        void loadProductList();

        void getMachinesList();

        void showSnackBar(String message);

        void onCreateErrorDialog(String message);

        /**
         * Revokes tokens on the server and Navigates to Login Screen
         */
        void logOut();
    }

    interface Presenter extends BasePresenter {

        /**
         * Checks if Machine was chosen.
         * If machine was not chosen no products will appear
         * and calls related to products will be blocked till machine will not be chosen.
         */
        void isMachineSet();

        void getFeaturedProductsList();

        void getFavoritesList();

        void getMachinesList();

        void getBalance();

        List<Categories> getCategories();

        /**
         * Get all categories loaded from server for dynamic creating views
         */
        void getCategoriesFromDB();

        /**
         * Decides which call needs to be proceed after refreshing token
         */
        void getActionAfterRefresh();
    }
}
