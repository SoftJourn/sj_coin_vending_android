package com.softjourn.sj_coin.presenters;

import android.util.Log;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnAddFavoriteEvent;
import com.softjourn.sj_coin.callbacks.OnBoughtEvent;
import com.softjourn.sj_coin.callbacks.OnRemoveFavoriteEvent;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.contratcts.SeeAllContract;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkManager;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.Subscribe;

public class SeeAllPresenter extends BasePresenterImpl implements SeeAllContract.Presenter {

    SeeAllContract.View mView;
    private final VendingModel mModel;
    private final LoginPresenter mLoginPresenter;

    private static String actionAfterRefresh;
    private static int itemId;

    public SeeAllPresenter(SeeAllContract.View vendingView) {

        onCreate();

        this.mView = vendingView;
        this.mModel = new VendingModel();
        this.mLoginPresenter = new LoginPresenter();
    }

    @Override
    public void addToFavorite(int id) {
        if (!NetworkManager.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            if (Utils.checkExpirationDate()) {
                actionAfterRefresh = "ADD";
                itemId = id;

                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(Const.REFRESH_TOKEN));
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.addProductToFavorite(id);
            }
        }
    }

    @Override
    public void removeFromFavorite(String id) {
        if (!NetworkManager.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            if (Utils.checkExpirationDate()) {
                actionAfterRefresh = "REMOVE";
                itemId = Integer.parseInt(id);

                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(Const.REFRESH_TOKEN));
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.removeProductFromFavorites(id);
            }
        }
    }

    @Override
    public void refreshToken(String refreshToken) {
        mLoginPresenter.refreshToken(refreshToken);
    }

    @Subscribe
    public void OnEvent(OnBoughtEvent event) {
        mView.hideProgress();
        mView.showSnackBar();
    }

    @Subscribe
    public void OnEvent(OnAddFavoriteEvent event) {
        addToFavorite(event.addFavorite().getId());
    }

    @Subscribe
    public void OnEvent(OnRemoveFavoriteEvent event) {
        removeFromFavorite(String.valueOf(event.removeFavorite().getId()));
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event) {
        if (event.isSuccess()) {
            if (actionAfterRefresh != null) {
                Log.d("ActAfterRefreshSeeAll", actionAfterRefresh);
                switch (actionAfterRefresh) {
                    case "ADD":
                        addToFavorite(itemId);
                        break;
                    case "REMOVE":
                        removeFromFavorite(String.valueOf(itemId));
                        break;
                    default:
                        break;
                }
            }
        }else {
            mView.hideProgress();
        }
    }
}
