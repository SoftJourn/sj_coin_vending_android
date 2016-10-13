package com.softjourn.sj_coin.presenters;

import android.app.Activity;

import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.callbacks.OnAddedToFavorites;
import com.softjourn.sj_coin.callbacks.OnRemovedFromFavorites;
import com.softjourn.sj_coin.contratcts.VendingFragmentContract;
import com.softjourn.sj_coin.realm.RealmController;

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
        mView.loadData(mModel.loadLocalProductList(mActivity));
    }

    @Override
    public void getLocalLastAddedProducts() {
        mView.loadData(mModel.loadLastAdded(mActivity));
    }

    @Override
    public void getLocalBestSellers() {
        mView.loadData(mModel.loadBestSellers(mActivity));
    }

    @Override
    public void getLocalFavorites() {
        mView.loadData(mModel.loadFavorites(mActivity));
    }

    @Override
    public void getLocalCategoryProducts(String category) {
        mView.loadData(mModel.loadProductsFromDB(mActivity, category));
    }

    @Override
    public void sortByName(String productsCategory, boolean isSortingForward) {
        mView.setSortedData(mModel.sortByName(mActivity, productsCategory, isSortingForward));
    }

    @Override
    public void sortByPrice(String productsCategory, boolean isSortingForward) {
        mView.setSortedData(mModel.sortByPrice(mActivity, productsCategory, isSortingForward));
    }

    @Subscribe
    public void OnEvent(OnAddedToFavorites event) {
        RealmController.with(mActivity).addToFavoriteLocal(event.getId());
        mView.changeFavoriteIcon();
    }

    @Subscribe
    public void OnEvent(OnRemovedFromFavorites event) {
        RealmController.with(mActivity).removeFromFavoritesLocal(Integer.parseInt(event.getId()));
        mView.changeFavoriteIcon();
    }
}
