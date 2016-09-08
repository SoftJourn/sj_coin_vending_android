package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.ProfileModel;
import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnAddedToFavorites;
import com.softjourn.sj_coin.callbacks.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnBoughtEvent;
import com.softjourn.sj_coin.callbacks.OnFavoritesListReceived;
import com.softjourn.sj_coin.callbacks.OnFeaturedProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnProductItemClickEvent;
import com.softjourn.sj_coin.callbacks.OnProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnRemovedFromFavorites;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.utils.Connections;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.localData.FavoritesListSingleton;
import com.softjourn.sj_coin.utils.localData.FeaturedProductsSingleton;
import com.softjourn.sj_coin.utils.localData.ProductsListSingleton;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.List;

public class VendingPresenter extends BasePresenterImpl implements VendingContract.Presenter, Constants {

    private VendingContract.View mView;
    private VendingModel mModel;
    private LoginPresenter mLoginPresenter;
    private ProfileModel mProfileModel;

    private String mMachineID;

    public VendingPresenter(VendingContract.View vendingView) {

        onCreate();

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
                mModel.callProductsList(mMachineID);
            }
        }
    }

    @Override
    public void getLocalProductList() {
        mView.loadData(mModel.loadDrink(), mModel.loadSnack());
    }

    @Override
    public void getFeaturedProductsList(String machineID) {

        mMachineID = machineID;

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
                mModel.getListFavorites();
            }
        }
    }

    @Override
    public void getLocalFeaturedProductsList() {
        getLocalLastAddedProducts();
        getLocalBestSellers();
        getLocalMyLastPurchase();
        getLocalSnacks();
        getLocalDrinks();
    }

    @Override
    public void getLocalLastAddedProducts() {
        mView.loadLastAddedData(mModel.loadLastAdded());
    }

    @Override
    public void getLocalBestSellers() {
        mView.loadBestSellerData(mModel.loadBestSellers());
    }

    @Override
    public void getLocalMyLastPurchase() {
        mView.loadMyLastPurchaseData(mModel.loadMyLastPurchase());
    }

    @Override
    public void getLocalSnacks() {
        mView.loadSnackData(mModel.loadSnack());
    }

    @Override
    public void getLocalDrinks() {
        mView.loadDrinkData(mModel.loadDrink());
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
    public void addToFavorite(String id) {
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
    public void sortByName(List<CustomizedProduct> product, boolean isSortingForward) {
        mView.setSortedData(mModel.sortByName(product, isSortingForward));
    }

    @Override
    public void sortByPrice(List<CustomizedProduct> product, boolean isSortingForward) {
        mView.setSortedData(mModel.sortByPrice(product, isSortingForward));
    }

    @Override
    public void getBalance() {
        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
        } else {
            if (checkExpirationDate()) {
                refreshToken(Constants.REFRESH_TOKEN);
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
        return Connections.isNetworkEnabled();
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
    public void OnEvent(OnProductsListReceived event) {
        mView.hideProgress();
        ProductsListSingleton.getInstance().setData(event.getProductsList());
        getLocalProductList();
    }

    @Subscribe
    public void OnEvent(OnFeaturedProductsListReceived event) {
        mView.hideProgress();
        FeaturedProductsSingleton.getInstance().setData(event.getProductsList());
        mView.navigateToFragments();
    }

    @Subscribe
    public void OnEvent(OnFavoritesListReceived event){
        FavoritesListSingleton.getInstance().setData(event.getFavorites());
    }

    @Subscribe
    public void OnEvent(OnBoughtEvent event) {
        if (event.isSuccess()) {
            mView.showToastMessage(App.getContext().getString(R.string.activity_product_take_your_order_message));
        } else {
            mView.showToastMessage(App.getContext().getString(R.string.toast_we_can_not_proceed_your_request));
        }
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
    public void OnEvent(OnAddedToFavorites event){
        mView.hideProgress();
        FavoritesListSingleton.getInstance().LocalAddToFavorites(event.getId());
        mView.changeFavoriteIcon();
    }

    @Subscribe
    public void OnEvent(OnRemovedFromFavorites event){
        mView.hideProgress();
        FavoritesListSingleton.getInstance().LocalRemoveFromFavorites(event.getId());
        mView.changeFavoriteIcon();
    }


}
