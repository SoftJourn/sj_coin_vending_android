package com.softjourn.sj_coin.presenters;

import android.app.Activity;

import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.callbacks.OnAddedToFavorites;
import com.softjourn.sj_coin.callbacks.OnRemovedFromFavorites;
import com.softjourn.sj_coin.callbacks.OnRemovedLastFavoriteEvent;
import com.softjourn.sj_coin.contratcts.VendingFragmentContract;
import com.softjourn.sj_coin.utils.Const;

import org.greenrobot.eventbus.Subscribe;

public class VendingFragmentPresenter extends BasePresenterImpl implements VendingFragmentContract.Presenter {

    private final VendingFragmentContract.View mView;
    private final VendingModel mModel;
    private Activity mActivity;

    public VendingFragmentPresenter(VendingFragmentContract.View vendingView, Activity activity) {

        onCreate();

        this.mActivity = activity;
        this.mView = vendingView;
        this.mModel = new VendingModel();
    }


    @Override
    public void getLocalProductList() {
        mView.loadData(mModel.loadLocalProductList());
    }

    @Override
    public void getLocalLastAddedProducts() {
        mView.loadData(mModel.loadLastAdded());
    }

    @Override
    public void getLocalBestSellers() {
        mView.loadData(mModel.loadBestSellers());
    }

    @Override
    public void getLocalFavorites() {
        mView.loadData(mModel.loadFavorites());
    }

    @Override
    public void getLocalCategoryProducts(String category) {
        mView.loadData(mModel.loadProductsFromDB(category));
    }

    @Override
    public void sortByName(String productsCategory, boolean isSortingForward) {
        mView.setSortedData(mModel.sortByName(productsCategory, isSortingForward));
    }

    @Override
    public void sortByPrice(String productsCategory, boolean isSortingForward) {
        mView.setSortedData(mModel.sortByPrice(productsCategory, isSortingForward));
    }

    @Subscribe
    public void OnEvent(OnAddedToFavorites event) {
        mModel.addToFavoriteLocal(event.getId());
        mView.changeFavoriteIcon(Const.ACTION_ADD_FAVORITE);
    }

    @Subscribe
    public void OnEvent(OnRemovedFromFavorites event) {
        mModel.removeFromFavoriteLocal(event.getId());
        mView.changeFavoriteIcon(Const.ACTION_REMOVE_FAVORITE);
    }

    @Subscribe
    public void OnEvent(OnRemovedLastFavoriteEvent event) {
        mView.showDataAfterRemovingFavorites(event.getList());
    }
}
