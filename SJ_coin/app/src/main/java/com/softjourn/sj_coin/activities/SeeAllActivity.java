package com.softjourn.sj_coin.activities;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductsListFragment;
import com.softjourn.sj_coin.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnAddFavoriteEvent;
import com.softjourn.sj_coin.callbacks.OnProductBuyClickEvent;
import com.softjourn.sj_coin.callbacks.OnRemoveFavoriteEvent;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Extras;
import com.softjourn.sj_coin.utils.Navigation;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class SeeAllActivity extends BaseActivity implements VendingContract.View, Const, Extras, NavigationView.OnNavigationItemSelectedListener {

    private VendingContract.Presenter mPresenter;

    FeaturedProductItemsAdapter mAdapter;

    SearchView mSearch;

    Button mFragmentsSortNameButton;
    Button mFragmentsSortPriceButton;

    NavigationView mNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        mPresenter = new VendingPresenter(this);

        String category = getIntent().getStringExtra(EXTRAS_CATEGORY);
        setTitle(category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);

        attachFragment(getIntent().getStringExtra(EXTRAS_CATEGORY));

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.allProducts:
                Navigation.navigationOnCategoriesSeeAll(0, SeeAllActivity.this);
                setTitle(R.string.allItems);
                break;
            case R.id.favorites:
                Navigation.navigationOnCategoriesSeeAll(1, SeeAllActivity.this);
                setTitle(R.string.favorites);
                break;
            case R.id.lastAdded:
                Navigation.navigationOnCategoriesSeeAll(2, SeeAllActivity.this);
                setTitle(R.string.lastAdded);
                break;
            case R.id.bestSellers:
                Navigation.navigationOnCategoriesSeeAll(3, SeeAllActivity.this);
                setTitle(R.string.bestSellers);
                break;
            case R.id.snacks:
                Navigation.navigationOnCategoriesSeeAll(4, SeeAllActivity.this);
                setTitle(R.string.snacks);
                break;
            case R.id.drinks:
                Navigation.navigationOnCategoriesSeeAll(5, SeeAllActivity.this);
                setTitle(R.string.drinks);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_search).setVisible(true);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        mSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();

        mSearch.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        mSearch.setQueryHint(getString(R.string.search_hint));

        ((EditText)mSearch.findViewById(R.id.search_src_text)).setTextColor(Color.WHITE);
        ((EditText)mSearch.findViewById(R.id.search_src_text)).setHintTextColor(Color.WHITE);

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(SeeAllActivity.this.getCurrentFocus().getWindowToken(), 0);
                mSearch.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (TextUtils.isEmpty(query)) {
                    mAdapter.getFilter().filter("");
                } else {
                    mAdapter.getFilter().filter(query.toString());
                }
                mFragmentsSortNameButton.setEnabled(false);
                mFragmentsSortPriceButton.setEnabled(false);

                return true;
            }

        });

        mSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mFragmentsSortNameButton.setEnabled(true);
                mFragmentsSortPriceButton.setEnabled(true);
                return false;
            }
        });

        return true;
    }

    private void attachFragment(String stringExtra) {
        switch (stringExtra) {

            case ALL_ITEMS:
                mNavigationView.getMenu().getItem(0).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(ALL_ITEMS), TAG_ALL_PRODUCTS_FRAGMENT)
                        .commit();
                break;
            case FAVORITES:
                mNavigationView.getMenu().getItem(1).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(FAVORITES), TAG_FAVORITES_FRAGMENT)
                        .commit();
                break;
            case SNACKS:
                mNavigationView.getMenu().getItem(4).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(SNACKS), TAG_PRODUCTS_SNACKS_FRAGMENT)
                        .commit();
                break;
            case DRINKS:
                mNavigationView.getMenu().getItem(5).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(DRINKS), TAG_PRODUCTS_DRINKS_FRAGMENT)
                        .commit();
                break;
            case BEST_SELLERS:
                mNavigationView.getMenu().getItem(3).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(BEST_SELLERS), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                        .commit();
                break;
            case LAST_ADDED:
                mNavigationView.getMenu().getItem(2).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(LAST_ADDED), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                        .commit();
                break;
        }
    }

    public void setButtons(Button button, Button button2) {
        this.mFragmentsSortNameButton = button;
        this.mFragmentsSortPriceButton = button2;
    }

    public void productsList(FeaturedProductItemsAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void loadData(List<? extends CustomizedProduct> drinks, List<? extends CustomizedProduct> snacks) {

    }

    @Override
    public void navigateToBuyProduct(CustomizedProduct product) {
        onCreateDialog(product, mPresenter);
    }

    @Override
    public void navigateToFragments() {

    }

    @Override
    public void setSortedData(List<? extends CustomizedProduct> product) {

    }

    @Override
    public void loadUserBalance() {

    }

    @Override
    public void updateBalanceAmount(String amount) {

    }

    @Override
    public void changeFavoriteIcon() {

    }

    @Override
    public void loadData(List<? extends CustomizedProduct> data) {

    }

    @Override
    public void showToastMessage(String message) {
        super.showToast(message);
    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }

    @Subscribe
    public void OnEvent(OnProductBuyClickEvent event) {
        navigateToBuyProduct(event.buyProduct());
    }

    @Subscribe
    public void OnEvent(OnAddFavoriteEvent event) {
        mPresenter.addToFavorite(event.addFavorite().getId());
    }

    @Subscribe
    public void OnEvent(OnRemoveFavoriteEvent event) {
        mPresenter.removeFromFavorite(String.valueOf(event.removeFavorite().getId()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
