package com.softjourn.sj_coin.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductsListFragment;
import com.softjourn.sj_coin.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnAddFavoriteEvent;
import com.softjourn.sj_coin.callbacks.OnProductBuyClickEvent;
import com.softjourn.sj_coin.callbacks.OnRemoveFavoriteEvent;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Extras;
import com.softjourn.sj_coin.utils.Navigation;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class SeeAllActivity extends BaseActivity implements VendingContract.View, Const, Extras {

    private VendingContract.Presenter mPresenter;

    FeaturedProductItemsAdapter mAdapter;

    SearchView mSearch;

    Button mFragmentsSortNameButton;
    Button mFragmentsSortPriceButton;

    public Spinner mNavigationSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        mPresenter = new VendingPresenter(this);

        String category = getIntent().getStringExtra(EXTRAS_CATEGORY);
        setTitle(category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.activityCategory, R.layout.spinner_dropdown_item);
        mNavigationSpinner = new Spinner(getApplicationContext());
        mNavigationSpinner.setAdapter(spinnerAdapter);
        toolbar.addView(mNavigationSpinner, 0);
        toolbar.canShowOverflowMenu();
        toolbar.showOverflowMenu();

        mNavigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Navigation.navigationOnCategoriesSeeAll(position, SeeAllActivity.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        attachFragment(getIntent().getStringExtra(EXTRAS_CATEGORY));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_search).setVisible(true);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        mSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();

        mSearch.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        mSearch.setQueryHint(getString(R.string.search_hint));

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

            case ALL_PRODUCTS:
                mNavigationSpinner.setSelection(0);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(ALL_PRODUCTS), TAG_ALL_PRODUCTS_FRAGMENT)
                        .commit();
                break;
            case FAVORITES:
                mNavigationSpinner.setSelection(1);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(FAVORITES), TAG_FAVORITES_FRAGMENT)
                        .commit();
                break;
            case SNACKS:
                mNavigationSpinner.setSelection(4);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(SNACKS), TAG_PRODUCTS_SNACKS_FRAGMENT)
                        .commit();
                break;
            case DRINKS:
                mNavigationSpinner.setSelection(5);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(DRINKS), TAG_PRODUCTS_DRINKS_FRAGMENT)
                        .commit();
                break;
            case BEST_SELLERS:
                mNavigationSpinner.setSelection(3);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(BEST_SELLERS), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                        .commit();
                break;
            case LAST_ADDED:
                mNavigationSpinner.setSelection(2);
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
}
