package com.softjourn.sj_coin.presenters;

import android.text.TextUtils;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.models.products.Categories;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.events.OnAccountReceivedEvent;
import com.softjourn.sj_coin.events.OnAmountReceivedEvent;
import com.softjourn.sj_coin.events.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.events.OnProductItemClickEvent;
import com.softjourn.sj_coin.events.OnTokenRevoked;
import com.softjourn.sj_coin.mvpmodels.ProfileModel;
import com.softjourn.sj_coin.mvpmodels.VendingModel;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkUtils;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.UIUtils;

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

        if (!NetworkUtils.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            mView.showProgress(App.getContext().getString(R.string.progress_loading));
            mModel.callMachinesList();
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

        if (!NetworkUtils.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            mModel.callFeaturedProductsList(Preferences.retrieveStringObject(SELECTED_MACHINE_ID));
        }
    }

    @Override
    public void getFavoritesList() {
        mModel.callListFavorites();
    }

    @Override
    public void getBalance() {
        if (NetworkUtils.isNetworkEnabled()) {
            mProfileModel.makeBalanceCall();
            mProfileModel.makeAccountCall();
        }
    }

    @Override
    public void logOut(String refreshToken) {
        if (!NetworkUtils.isNetworkEnabled()) {
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

    @Override
    public List<Categories> getCategories() {
        return mModel.loadCategories();
    }

    @Subscribe
    public void OnEvent(OnAmountReceivedEvent event) {
        Preferences.storeObject(USER_BALANCE_PREFERENCES_KEY, String.valueOf(event.getAmount().getAmount()));
        mView.updateBalanceAmount(String.valueOf(event.getAmount().getAmount()));
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
        Preferences.storeObject(USER_BALANCE_PREFERENCES_KEY, event.getBalance());
        mView.updateBalanceAmount(event.getBalance());
    }

    @Subscribe
    public void OnEvent(OnAccountReceivedEvent event) {
        Preferences.storeObject(USER_NAME_PREFERENCES_KEY, UIUtils.getUserFullName(event.getAccount().getName(), event.getAccount().getSurname()));
    }

    @Subscribe
    public void OnEvent(OnTokenRevoked event) {
        mView.hideProgress();
        mView.logOut();
    }
}
