package com.softjourn.sj_coin.presenters;

import android.app.Activity;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.ProfileModel;
import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnAddedToFavorites;
import com.softjourn.sj_coin.callbacks.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnBoughtEvent;
import com.softjourn.sj_coin.callbacks.OnProductItemClickEvent;
import com.softjourn.sj_coin.callbacks.OnRemovedFromFavorites;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.products.Categories;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkManager;
import com.softjourn.sj_coin.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendingPresenter extends BasePresenterImpl implements VendingContract.Presenter, Const {

    private VendingContract.View mView;
    private VendingModel mModel;
    private LoginPresenter mLoginPresenter;
    private ProfileModel mProfileModel;
    private Activity mActivity;

    private String mMachineID;

    public VendingPresenter(VendingContract.View vendingView) {

        onCreate();

        this.mView = vendingView;
        this.mModel = new VendingModel();
        this.mLoginPresenter = new LoginPresenter();
        this.mProfileModel = new ProfileModel();
    }

    public VendingPresenter(VendingContract.View vendingView, Activity activity) {

        onCreate();

        this.mActivity = activity;
        this.mView = vendingView;
        this.mModel = new VendingModel();
        this.mLoginPresenter = new LoginPresenter();
        this.mProfileModel = new ProfileModel();
    }


    @Override
    public void getProductList(String machineID) {

        mMachineID = machineID;

        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
            getLocalProductList();
        } else {
            if (checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.callFeaturedProductsList(mMachineID);
            }
        }
    }

    @Override
    public void getLocalProductList() {
        mView.loadData(mModel.loadLocalProductList(mActivity));
    }

    @Override
    public void getFeaturedProductsList(String machineID) {

        mMachineID = "13";

        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
            getLocalFeaturedProductsList();
        } else {
            if (checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.callFeaturedProductsList(mMachineID);
            }
        }
    }

    @Override
    public void getFavoritesList() {
        mModel.getListFavorites();
    }

    @Override
    public void getLocalFeaturedProductsList() {
        getLocalLastAddedProducts();
        getLocalBestSellers();
    }

    @Override
    public void getLocalLastAddedProducts() {
        mView.loadData(mModel.loadLastAdded(mActivity));
    }

    @Override
    public void getLocalBestSellers() {
        mView.loadData(mModel.loadBestSellers(mActivity));
    }

    @Override
    public void getLocalCategoryProducts(String category){
        mView.loadData(mModel.loadProductsFromDB(mActivity,category));
    }

    @Override
    public boolean checkExpirationDate() {
        return (new Date().getTime() / 1000 >= Long.parseLong(Preferences.retrieveStringObject(EXPIRATION_DATE)));
    }

    @Override
    public void buyProduct(String id) {

        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
        } else {
            if (checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.buyProductByID(id);
            }
        }
    }

    @Override
    public void addToFavorite(int id) {
        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
        } else {
            if (checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.addProductToFavorite(id);
            }
        }
    }

    @Override
    public void removeFromFavorite(String id) {
        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
        } else {
            if (checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.removeProductFromFavorites(id);
            }
        }
    }

    @Override
    public void getLocalFavorites() {
        mView.loadData(mModel.loadFavorites(mActivity));
    }

    @Override
    public void sortByName(String productsCategory, boolean isSortingForward) {
        mView.setSortedData(mModel.sortByName(mActivity,productsCategory, isSortingForward));
    }

    @Override
    public void sortByPrice(String productsCategory, boolean isSortingForward) {
        mView.setSortedData(mModel.sortByPrice(mActivity,productsCategory, isSortingForward));
    }

    @Override
    public void getBalance() {
        if (!makeNetworkChecking()) {

        } else {
            if (checkExpirationDate()) {
                refreshToken(Const.REFRESH_TOKEN);
            } else {
                mProfileModel.makeBalanceCall();
            }
        }
    }

    @Override
    public void refreshToken(String refreshToken) {
        mLoginPresenter.refreshToken(refreshToken);
    }

    @Override
    public boolean makeNetworkChecking() {
        return NetworkManager.isNetworkEnabled();
    }

    @Override
    public void getCategoriesFromDB() {
        List<String> categoriesNames = new ArrayList<>();
        List<Categories> categories = mModel.loadCategories(mActivity);
        for (Categories category : categories) {
            if (!categoriesNames.contains(category.getName())) {
                categoriesNames.add(category.getName());
                Preferences.storeObject(category.getName().toUpperCase(), category.getName());
            }
        }
        for (String name : categoriesNames) {
            mView.createContainer(name);
        }
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event) {
        if (event.isSuccess()) {
            mView.hideProgress();
            mModel.callFeaturedProductsList(mMachineID);
            mView.loadUserBalance();
        } else {
            mView.hideProgress();
        }
    }

    @Subscribe
    public void OnEvent(OnBoughtEvent event) {
        mView.hideProgress();
        mView.showToastMessage(App.getContext().getString(R.string.activity_product_take_your_order_message));
        mView.updateBalanceAmount(String.valueOf(event.getAmount().getAmount()));
    }

    @Subscribe
    public void OnEvent(OnProductItemClickEvent event) {
        mView.navigateToBuyProduct(event.getProduct());
    }

    @Subscribe
    public void OnEvent(OnBalanceReceivedEvent event) {
        mView.updateBalanceAmount(event.getBalance());
    }

    @Subscribe
    public void OnEvent(OnAddedToFavorites event) {
        mView.hideProgress();
        mView.changeFavoriteIcon();
    }

    @Subscribe
    public void OnEvent(OnRemovedFromFavorites event) {
        mView.hideProgress();
        mView.changeFavoriteIcon();
    }
}
