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
import com.softjourn.sj_coin.callbacks.OnFavoritesListReceived;
import com.softjourn.sj_coin.callbacks.OnFeaturedProductsListReceived;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.realm.RealmController;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VendingActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        VendingContract.View, Const {

    private VendingContract.Presenter mPresenter;

    private int viewCounter = 0;

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

    @OnClick({R.id.textViewLastAddedSeeAll, R.id.textViewBestSellersSeeAll, R.id.textViewFavoritesSeeAll})
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
        LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
        for (int i = 0; i < viewCounter; i++) {
            layout.removeView(findViewById(R.id.categoryLayout));
        }
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
        onCreateDialog(product, mPresenter);
    }

    @Override
    public void navigateToFragments() {

        getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_products_list_favorites, ProductsListFragment.newInstance(FAVORITES, 0, 0), TAG_FAVORITES_FRAGMENT)
                .replace(R.id.container_fragment_products_list_new_products, ProductsListFragment.newInstance(LAST_ADDED, 0, 0), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                .replace(R.id.container_fragment_products_list_best_sellers, ProductsListFragment.newInstance(BEST_SELLERS, 0, 0), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
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

    @Override
    public void createContainer(final String categoryName) {

        viewCounter++;

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);

        LinearLayout ll;

        ll = (LinearLayout) getLayoutInflater().inflate(R.layout.category_header_layout, null);
        mainLayout.addView(ll);

        LinearLayout llHeader = (LinearLayout) findViewById(R.id.dummyHeaderID);
        llHeader.setId(View.generateViewId());

        TextView tvCategoryName = (TextView) findViewById(R.id.categoryName);
        tvCategoryName.setId(View.generateViewId());
        tvCategoryName.setText(categoryName);

        TextView tvSeeAll = (TextView) findViewById(R.id.dummySeeAllID);
        tvSeeAll.setId(View.generateViewId());

        LinearLayout llContainer = (LinearLayout) findViewById(R.id.container_dummyID);
        llContainer.setId(View.generateViewId());

        tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.goToSeeAllActivity(VendingActivity.this, categoryName);
            }
        });

        attachFragment(categoryName, llHeader.getId(), llContainer.getId(), tvSeeAll.getId());
    }

    private void attachFragment(String categoryName, int headerID, int containerID, int seeAllID) {
        getFragmentManager().beginTransaction().replace(containerID, ProductsListFragment.newInstance(categoryName, headerID, containerID),
                Preferences.retrieveStringObject(categoryName.toUpperCase())).commit();
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Subscribe
    public void OnEvent(OnFeaturedProductsListReceived event) {
        mPresenter.getFavoritesList();
    }

    @Subscribe
    public void OnEvent(OnFavoritesListReceived event) {
        mPresenter.getCategoriesFromDB();
        navigateToFragments();
        hideProgress();
    }
}

