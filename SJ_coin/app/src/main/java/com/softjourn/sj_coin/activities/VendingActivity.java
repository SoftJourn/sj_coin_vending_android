package com.softjourn.sj_coin.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.NewProduct;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VendingActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        VendingContract.View, Constants {

    private VendingContract.Presenter mPresenter;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @OnClick({R.id.textViewNewProductsSeeAll, R.id.textViewLastPurchaseSeeAll,
            R.id.textViewBestSellersSeeAll, R.id.textViewSnacksSeeAll, R.id.textViewDrinksSeeAll})
    public void seeAll(View v) {
        switch (v.getId()) {
            case R.id.textViewNewProductsSeeAll:
                Navigation.goToSeeAllActivity(this, NEW_PRODUCTS);
                break;
            case R.id.textViewLastPurchaseSeeAll:
                Navigation.goToSeeAllActivity(this, LAST_PURCHASES);
                break;
            case R.id.textViewBestSellersSeeAll:
                Navigation.goToSeeAllActivity(this, BEST_SELLERS);
                break;
            case R.id.textViewSnacksSeeAll:
                Navigation.goToSeeAllActivity(this, SNACKS);
                break;
            case R.id.textViewDrinksSeeAll:
                Navigation.goToSeeAllActivity(this, DRINKS);
                break;
        }
    }

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new VendingPresenter(this);

        mContext = this;

        ButterKnife.bind(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        loadProductList();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        loadProductList();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgress(String message) {
        super.showProgress(message);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }

    @Override
    public void loadData(List<Product> data) {

    }

    @Override
    public void loadNewProductsData(List<NewProduct> data) {

    }

    @Override
    public void loadBestSellerData(List<BestSeller> data) {

    }

    @Override
    public void loadMyLastPurchaseData(List<MyLastPurchase> data) {

    }

    @Override
    public void loadSnackData(List<Snack> data) {

    }

    @Override
    public void loadDrinkData(List<Drink> data) {

    }

    @Override
    public void navigateToBuyProduct(CustomizedProduct product) {

    }

    @Override
    public void navigateToFragments() {
        Navigation.goToProductListFragments(this);
    }

    @Override
    public void showToastMessage(String message) {

    }

    private void loadProductList() {
        mPresenter.getFeaturedProductsList(MACHINE_ID);
    }

}

