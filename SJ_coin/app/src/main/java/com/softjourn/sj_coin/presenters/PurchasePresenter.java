package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.contratcts.PurchaseContract;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkManager;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by omartynets on 13.10.2016.
 */
public class PurchasePresenter extends BasePresenterImpl implements PurchaseContract.Presenter, Const {

    private final PurchaseContract.View mView;
    private final LoginPresenter mLoginPresenter;
    private final VendingModel mVendingModel;

    private static String productId;

    public PurchasePresenter(PurchaseContract.View purchaseView) {

        onCreate();

        mView = purchaseView;
        mVendingModel = new VendingModel();
        mLoginPresenter = new LoginPresenter();
    }


    @Override
    public void buyProduct(String id) {

        if (!NetworkManager.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            if (Utils.checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                mLoginPresenter.refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
                productId = id;
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mVendingModel.buyProductByID(Preferences.retrieveStringObject(SELECTED_MACHINE_ID), id);
                onDestroy();
            }
        }
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event) {
        if (event.isSuccess()) {
            buyProduct(productId);
        } else {
            mView.hideProgress();
        }
    }
}
