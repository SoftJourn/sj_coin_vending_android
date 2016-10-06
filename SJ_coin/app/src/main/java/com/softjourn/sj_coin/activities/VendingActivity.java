package com.softjourn.sj_coin.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductsListFragment;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.realm.RealmController;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VendingActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        VendingContract.View, Const {

    private VendingContract.Presenter mPresenter;

    @Bind(R.id.balance)
    TextView mBalance;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

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

    @OnClick({R.id.textViewLastAddedSeeAll, R.id.textViewBestSellersSeeAll, R.id.textViewFavoritesSeeAll,
            R.id.textViewSnacksSeeAll, R.id.textViewDrinksSeeAll})
    public void seeAll(View v) {
        switch (v.getId()) {
            case R.id.textViewFavoritesSeeAll:
                Navigation.goToSeeAllActivity(this, FAVORITES);
                break;
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
    public void navigateToBuyProduct(Product product) {
        onCreateDialog(product,mPresenter);
    }

    @Override
    public void navigateToFragments() {

        getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_products_list_favorites, ProductsListFragment.newInstance(FAVORITES),TAG_FAVORITES_FRAGMENT)
                .replace(R.id.container_fragment_products_list_new_products, ProductsListFragment.newInstance(LAST_ADDED), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                .replace(R.id.container_fragment_products_list_best_sellers, ProductsListFragment.newInstance(BEST_SELLERS), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                .replace(R.id.container_fragment_products_list_snacks, ProductsListFragment.newInstance(SNACKS),TAG_PRODUCTS_SNACKS_FRAGMENT)
                .replace(R.id.container_fragment_products_list_drinks, ProductsListFragment.newInstance(DRINKS),TAG_PRODUCTS_DRINKS_FRAGMENT)
                .commit();
    }

    @Override
    public void setSortedData(List<Product> product) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void loadUserBalance() {
        mPresenter.getBalance();
    }


    @Override
    public void showToastMessage(String message) {
        super.showToast(message);
    }

    @Override
    public void updateBalanceAmount(String balance) {
        mBalance.setVisibility(View.VISIBLE);
        mBalance.setText(getString(R.string.your_balance_is) + balance + getString(R.string.coins));
    }

    @Override
    public void changeFavoriteIcon() {

    }

    @Override
    public void loadData(List<Product> data) {

    }

    private void loadProductList() {
        RealmController.with(this).clearAll();
        mPresenter.getFeaturedProductsList(MACHINE_ID);

        loadUserBalance();
    }

    public void hideContainer(int headers, int fragmentContainerId) {
        View view = (View) findViewById(headers);
        View fragmentContainer = (View) findViewById(fragmentContainerId);
        fragmentContainer.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
    }

    public void showContainer(int headers, int fragmentContainerId) {
        View view = (View) findViewById(headers);
        View fragmentContainer = (View) findViewById(fragmentContainerId);

        view.setVisibility(View.VISIBLE);
        fragmentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void createContainer(String categoryName){

        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.mainLayout);

        LinearLayout ll = new LinearLayout(this);
        ll.setId(View.generateViewId());

        ll = (LinearLayout)getLayoutInflater().inflate(R.layout.category_header_layout,null);
        mainLayout.addView(ll);

        TextView tvCategoryName = (TextView)findViewById(R.id.categoryName);
        tvCategoryName.setId(View.generateViewId());
        tvCategoryName.setText(categoryName);

        TextView tvSeeAll = (TextView)findViewById(R.id.dummyID);
        tvSeeAll.setId(View.generateViewId());

        LinearLayout llContainer = (LinearLayout)findViewById(R.id.container_dummyID);
        llContainer.setId(View.generateViewId());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}

