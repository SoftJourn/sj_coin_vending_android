package com.softjourn.sj_coin.presenters;

import android.content.Context;

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

    private static String sProductId;
    private static Context sContext;

    public PurchasePresenter(PurchaseContract.View purchaseView) {

        onCreate();

        mView = purchaseView;
        mVendingModel = new VendingModel();
        mLoginPresenter = new LoginPresenter();
    }


    @Override
    public void buyProduct(String id, Context context) {
        sContext = context;
        if (!NetworkUtils.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            if (Utils.checkExpirationDate()) {
                mLoginPresenter.refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
                sProductId = id;
            } else {
                mVendingModel.buyProductByID(Preferences.retrieveStringObject(SELECTED_MACHINE_ID), id, sContext);
                onDestroy();
            }
        }
    }

    @Override
    public void buyAfterRefresh() {
        if (sProductId != null) {
            buyProduct(sProductId, sContext);
            sProductId = null;
        }
    }

    @Subscribe
    public void OnEvent(NoSubscriberEvent event) {

    }
}
