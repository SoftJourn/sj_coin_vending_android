package com.softjourn.sj_coin.presenters;

import android.text.TextUtils;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.ProfileModel;
import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api_models.products.Categories;
import com.softjourn.sj_coin.callbacks.OnAmountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnBoughtEvent;
import com.softjourn.sj_coin.callbacks.OnProductItemClickEvent;
import com.softjourn.sj_coin.callbacks.OnTokenRevoked;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkManager;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class VendingPresenter extends BasePresenterImpl implements VendingContract.Presenter, Const {

    private final VendingContract.View mView;
    private final VendingModel mModel;
    private final LoginPresenter mLoginPresenter;
    private final ProfileModel mProfileModel;

    private static String actionAfterRefresh;

    public VendingPresenter(VendingContract.View vendingView) {

        onCreate();

        this.mView = vendingView;
        this.mModel = new VendingModel();
        this.mLoginPresenter = new LoginPresenter();

        this.mProfileModel = new ProfileModel();
    }

    @Override
    public void getMachinesList() {

        if (!NetworkManager.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            if (Utils.checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
                actionAfterRefresh = MACHINES_LIST;
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.callMachinesList();
            }
        }
    }

    @Override
    public void isMachineSet() {
        if (TextUtils.isEmpty(Preferences.retrieveStringObject(SELECTED_MACHINE_ID))) {
            mModel.callMachinesList();
        } else {
            mView.loadProductList();
        }
    }

    @Override
    public void getFeaturedProductsList() {

        if (!NetworkManager.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            if (Utils.checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
                actionAfterRefresh = PRODUCTS_LIST;
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.callFeaturedProductsList(Preferences.retrieveStringObject(SELECTED_MACHINE_ID));
            }
        }
    }

    @Override
    public void getFavoritesList() {
        mModel.callListFavorites();
    }

    @Override
    public void getBalance() {
        if (NetworkManager.isNetworkEnabled()) {
            if (Utils.checkExpirationDate()) {
                refreshToken(REFRESH_TOKEN);
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
    public void logOut(String refreshToken) {
        if (!NetworkManager.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            mView.showProgress(App.getContext().getString(R.string.progress_loading));
            mLoginPresenter.logOut(refreshToken);
        }
    }

    @Override
    public void getCategoriesFromDB() {
        List<String> categoriesNames = new ArrayList<>();
        List<Categories> categories = mModel.loadCategories();
        for (Categories category : categories) {
            if (!categoriesNames.contains(category.getName())) {
                categoriesNames.add(category.getName());
                Preferences.storeObject(category.getName().toUpperCase(), category.getName());
            }
        }
        for (String name : categoriesNames) {
            mView.createContainer(name);
        }
        mView.hideProgress();
    }

    public void getActionAfterRefresh() {
        if (actionAfterRefresh != null) {
            switch (actionAfterRefresh) {
                case MACHINES_LIST:
                    mView.getMachinesList();
                    actionAfterRefresh = null;
                    break;
                case PRODUCTS_LIST:
                    mView.loadProductList();
                    actionAfterRefresh = null;
                    break;
                default:
                    break;
            }
        }
    }

    @Subscribe
    public void OnEvent(OnAmountReceivedEvent event) {
        mView.hideProgress();
        mView.updateBalanceAmount(String.valueOf(event.getAmount().getAmount()));
        mView.showToastMessage(App.getContext().getString(R.string.activity_product_take_your_order_message));
    }

    @Subscribe
    public void OnEvent(OnBoughtEvent event) {
        //mView.hideProgress();
        mView.showSnackBar(App.getContext().getResources().getString(R.string.activity_order_processing));
    }

    @Subscribe
    public void OnEvent(OnProductItemClickEvent event) {
        if (mModel.isSingleProductPresent(event.getProduct().getId())) {
            mView.navigateToBuyProduct(event.getProduct());
        } else {
            mView.onCreateErrorDialog(App.getContext().getResources().getString(R.string.product_is_not_available_in_machine));
        }
    }

    @Subscribe
    public void OnEvent(OnBalanceReceivedEvent event) {
        mView.updateBalanceAmount(event.getBalance());
    }

    @Subscribe
    public void OnEvent(OnTokenRevoked event) {
        if (event.isSuccess()) {
            mView.hideProgress();
            mView.logOut();
        }
    }
}
