package com.softjourn.sj_coin.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.LastAdded;
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

    @Bind(R.id.balance)
    TextView mBalance;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @OnClick({R.id.textViewLastAddedSeeAll, R.id.textViewBestSellersSeeAll,
            R.id.textViewSnacksSeeAll, R.id.textViewDrinksSeeAll})
    public void seeAll(View v) {
        switch (v.getId()) {
            case R.id.textViewLastAddedSeeAll:
                Navigation.goToSeeAllActivity(this, LAST_ADDED);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new VendingPresenter(this);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
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
    public void loadData(List<Drink> drinks,List<Snack> snacks) {

    }

    @Override
    public void loadLastAddedData(List<LastAdded> data) {

    }

    @Override
    public void loadBestSellerData(List<BestSeller> data) {

    }

    @Override
    public void loadSnackData(List<Snack> data) {

    }

    @Override
    public void loadDrinkData(List<Drink> data) {

    }

    @Override
    public void navigateToBuyProduct(CustomizedProduct product) {
        onCreateDialog(product,mPresenter);
    }

    @Override
    public void navigateToFragments() {
        Navigation.goToProductListFragments(this);
    }

    @Override
    public void setSortedData(List<CustomizedProduct> product) {
        
    }

    @Override
    public void loadUserBalance() {
        mPresenter.getBalance();
    }


    @Override
    public void showToastMessage(String message) {
        super.showToast(message);
    }

    private void loadProductList() {
        mPresenter.getFeaturedProductsList(MACHINE_ID);

        loadUserBalance();
    }

    public void hideContainer(int headers, int fragmentContainerId) {
        View view = (View) findViewById(headers);
        View fragmentContainer = (View) findViewById(fragmentContainerId);

        view.setVisibility(View.INVISIBLE);
        fragmentContainer.setVisibility(View.INVISIBLE);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.height = 0;
        params.setMargins(0, 0, 0, 0);

        LinearLayout.LayoutParams frParams = (LinearLayout.LayoutParams) fragmentContainer.getLayoutParams();
        frParams.height = 0;
        frParams.setMargins(0, 0, 0, 0);

        view.setLayoutParams(params);
        fragmentContainer.setLayoutParams(frParams);
    }

    @Override
    public void updateBalanceAmount(String balance) {
        mBalance.setVisibility(View.VISIBLE);
        mBalance.setText(getString(R.string.your_balance_is) + balance + getString(R.string.coins));
    }

    @Override
    public void changeFavoriteIcon() {

    }
}

