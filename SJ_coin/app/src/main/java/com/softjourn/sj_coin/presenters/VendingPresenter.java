package com.softjourn.sj_coin.presenters;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.ProfileModel;
import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnAmountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnBoughtEvent;
import com.softjourn.sj_coin.callbacks.OnProductItemClickEvent;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.products.Categories;
import com.softjourn.sj_coin.realm.RealmController;
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
    private Activity mActivity;

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
    public void getFeaturedProductsList(String machineID) {

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
        mModel.getListFavorites();
    }

    @Override
    public void getBalance() {
        if (NetworkManager.isNetworkEnabled()) {
            if (Utils.checkExpirationDate()) {
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
        mView.hideProgress();
    }

    private void getActionAfterRefresh() {
        if (actionAfterRefresh!=null) {
            Log.d("ActAfterRefreshVending", actionAfterRefresh);
            switch (actionAfterRefresh) {
                case MACHINES_LIST:
                    mView.getMachinesList();
                    break;
                case PRODUCTS_LIST:
                    mView.loadProductList();
                    break;
                default:
                    break;
            }
            mView.hideProgress();
        }
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event) {
        if (event.isSuccess()) {
            getActionAfterRefresh();
            mView.hideProgress();
        } else {
            mView.hideProgress();
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
        mView.showSnackBar(App.getContext().getResources().getString(R.string.activity_order_processing));
    }

    @Subscribe
    public void OnEvent(OnProductItemClickEvent event) {
        if (RealmController.with(mActivity).isSingleProductPresent(String.valueOf(event.getProduct().getId()))) {
            mView.navigateToBuyProduct(event.getProduct());
        } else {
            mView.onCreateErrorDialog(App.getContext().getResources().getString(R.string.product_is_not_available_in_machine));
        }
    }

    @Subscribe
    public void OnEvent(OnBalanceReceivedEvent event) {
        mView.updateBalanceAmount(event.getBalance());
    }
}
