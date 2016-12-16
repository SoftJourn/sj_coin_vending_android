package com.softjourn.sj_coin.presenters;

import android.content.Context;

import com.softjourn.sj_coin.contratcts.PurchaseContract;
import com.softjourn.sj_coin.mvpmodels.VendingModel;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkUtils;
import com.softjourn.sj_coin.utils.Preferences;

import org.greenrobot.eventbus.NoSubscriberEvent;
import org.greenrobot.eventbus.Subscribe;

public class PurchasePresenter extends BasePresenterImpl implements PurchaseContract.Presenter, Const {

    private final PurchaseContract.View mView;
    private final VendingModel mVendingModel;

    public PurchasePresenter(PurchaseContract.View purchaseView) {

        onCreate();

        mView = purchaseView;
        mVendingModel = new VendingModel();
    }


    @Override
    public void buyProduct(String id, Context context) {
        if (!NetworkUtils.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            mVendingModel.buyProductByID(Preferences.retrieveStringObject(SELECTED_MACHINE_ID), id, context);
            onDestroy();
        }
    }

    @Subscribe
    public void OnEvent(NoSubscriberEvent event) {

    }
}
