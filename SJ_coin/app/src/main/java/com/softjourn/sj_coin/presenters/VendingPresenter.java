package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.FeaturedProductsSingleton;
import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.ProductsListSingleton;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnBoughtEvent;
import com.softjourn.sj_coin.callbacks.OnFeaturedProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnProductItemClickEvent;
import com.softjourn.sj_coin.callbacks.OnProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.utils.Connections;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

public class VendingPresenter extends BasePresenterImpl implements VendingContract.Presenter, Constants {

    private VendingContract.View mView;
    private VendingContract.Model mModel;
    private LoginPresenter mLoginPresenter;

    private String mMachineID;
    private String mId;

    public VendingPresenter(VendingContract.View vendingView) {

        onCreate();

        this.mView = vendingView;
        this.mModel = new VendingModel();
        this.mLoginPresenter = new LoginPresenter();
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
        mView.loadData(mModel.loadLocalProductList());
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
            }
        }
    }

    @Override
    public void getLocalFeaturedProductsList() {
        getLocalNewProducts();
        getLocalBestSellers();
        getLocalMyLastPurchase();
        getLocalSnacks();
        getLocalDrinks();
    }

    @Override
    public void getLocalNewProducts() {
        mView.loadNewProductsData(mModel.loadNewProduct());
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

        this.mId = id;

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
            if (mView.getClass().getName().equals("ProductActivity.java"))
            {
                mModel.buyProductByID(mId);
            } else {
                mModel.callProductsList(mMachineID);
            }
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
        getLocalFeaturedProductsList();
    }

    @Subscribe
    public void OnEvent(OnBoughtEvent event){
        if (event.isSuccess()){
            mView.showToastMessage(App.getContext().getString(R.string.activity_product_take_your_order_message));
        } else {
            mView.showToastMessage(App.getContext().getString(R.string.toast_we_can_not_proceed_your_request));
        }
    }

    @Subscribe
    public void OnEvent(OnProductItemClickEvent event){
        mView.navigateToBuyProduct(event.getProduct());
    }
}
