package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.models.products.Categories;
import com.softjourn.sj_coin.contratcts.SeeAllContract;
import com.softjourn.sj_coin.events.OnAddFavoriteEvent;
import com.softjourn.sj_coin.events.OnAmountReceivedEvent;
import com.softjourn.sj_coin.events.OnRemoveFavoriteEvent;
import com.softjourn.sj_coin.events.OnTokenRevoked;
import com.softjourn.sj_coin.mvpmodels.VendingModel;
import com.softjourn.sj_coin.utils.NetworkUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class SeeAllPresenter extends BasePresenterImpl implements SeeAllContract.Presenter {

    private SeeAllContract.View mView;
    private final VendingModel mModel;
    private final LoginPresenter mLoginPresenter;

    public SeeAllPresenter(SeeAllContract.View vendingView) {

        onCreate();

        this.mView = vendingView;
        this.mModel = new VendingModel();
        this.mLoginPresenter = new LoginPresenter();
    }

    @Override
    public void addToFavorite(int id) {
        if (!NetworkUtils.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            mModel.addProductToFavorite(id);
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
    public boolean isProductInMachine(int id) {
        return mModel.isSingleProductPresent(id);
    }

    @Override
    public List<Categories> getCategories() {
        return mModel.loadCategories();
    }

    @Override
    public void removeFromFavorite(String id) {
        if (!NetworkUtils.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            mModel.removeProductFromFavorites(id);
        }
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
    public void OnEvent(OnAmountReceivedEvent event) {
        mView.hideProgress();
    }

    @Subscribe
    public void OnEvent(OnTokenRevoked event) {
        mView.hideProgress();
        mView.logOut();
    }
}
