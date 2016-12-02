package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.contratcts.PurchaseContract;
import com.softjourn.sj_coin.mvpmodels.VendingModel;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkUtils;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.NoSubscriberEvent;
import org.greenrobot.eventbus.Subscribe;

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

        if (!NetworkUtils.isNetworkEnabled()) {
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

    @Override
    public void buyAfterRefresh() {
        if (productId != null) {
            buyProduct(productId);
            productId = null;
        }
    }

    @Subscribe
    public void OnEvent(NoSubscriberEvent event) {

    }
}
