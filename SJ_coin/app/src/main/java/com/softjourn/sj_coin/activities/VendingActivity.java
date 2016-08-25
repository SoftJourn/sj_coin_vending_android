package com.softjourn.sj_coin.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VendingActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        VendingContract.View,Constants {

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
                Navigation.goToSeeAllActivity(this,SNACKS);
                break;
            case R.id.textViewDrinksSeeAll:
                Navigation.goToSeeAllActivity(this,DRINKS);
                break;
        }
    }

    private BottomBar mBottomBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new VendingPresenter(this);

        ButterKnife.bind(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setTextAppearance(R.style.BottomBarItem);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {

                }
            }
        });
        loadProductList();
        Navigation.goToProductListFragments(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        loadProductList();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgress(String message) {
        ProgressDialogUtils.showDialog(this,message);
    }

    @Override
    public void showToastMessage() {

    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }

    @Override
    public void loadData(List<Product> data) {

    }

    @Override
    public void hideProgress() {
        ProgressDialogUtils.dismiss();
    }

    private void loadProductList(){
        mPresenter.getProductList(MACHINE_ID);
    }

}

