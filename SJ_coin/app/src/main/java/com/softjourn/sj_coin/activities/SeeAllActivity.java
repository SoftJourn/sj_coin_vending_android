package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductListDrinksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductListSnacksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListBestSellersFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListLastAddedFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListLastPurchasesFragment;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnProductBuyClickEvent;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.LastAdded;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;
import com.softjourn.sj_coin.utils.Navigation;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class SeeAllActivity extends BaseActivity implements VendingContract.View,Constants, Extras {

    private VendingContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        mPresenter = new VendingPresenter(this);

        setTitle(getIntent().getStringExtra(EXTRAS_CATEGORY));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.activityCategory, R.layout.spinner_dropdown_item);
        Spinner navigationSpinner = new Spinner(getApplicationContext());
        navigationSpinner.setAdapter(spinnerAdapter);
        toolbar.addView(navigationSpinner,0);
        toolbar.canShowOverflowMenu();
        toolbar.showOverflowMenu();

        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                navigationOnCategories(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switch (getIntent().getStringExtra(EXTRAS_CATEGORY)){
            case SNACKS:
                navigationSpinner.setSelection(4);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductListSnacksFragment.newInstance(), TAG_PRODUCTS_SNACKS_FRAGMENT)
                        .commit();
                break;
            case DRINKS:
                navigationSpinner.setSelection(5);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductListDrinksFragment.newInstance(), TAG_PRODUCTS_DRINKS_FRAGMENT)
                        .commit();
                break;
            case BEST_SELLERS:
                navigationSpinner.setSelection(3);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListBestSellersFragment.newInstance(), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                        .commit();
                break;
            case LAST_ADDED:
                navigationSpinner.setSelection(1);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListLastAddedFragment.newInstance(), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                        .commit();
                break;
            case LAST_PURCHASES:
                navigationSpinner.setSelection(2);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListLastPurchasesFragment.newInstance(), TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT)
                        .commit();
                break;
        }
    }

    public void navigationOnCategories(int position){
        switch (position){
            case 0:
                Navigation.goToAllProductsActivity(SeeAllActivity.this);
                finish();
                break;
            case 1:
                SeeAllActivity.this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListLastAddedFragment.newInstance(), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                        .commit();
                break;
            case 2:
                SeeAllActivity.this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListLastPurchasesFragment.newInstance(), TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT)
                        .commit();
                break;
            case 3:
                SeeAllActivity.this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListBestSellersFragment.newInstance(), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                        .commit();
                break;
            case 4:
                SeeAllActivity.this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductListSnacksFragment.newInstance(), TAG_PRODUCTS_SNACKS_FRAGMENT)
                        .commit();
                break;
            case 5:
                SeeAllActivity.this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductListDrinksFragment.newInstance(), TAG_PRODUCTS_DRINKS_FRAGMENT)
                        .commit();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_search).setVisible(true);
        return true;
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
        onCreateDialog(product,mPresenter);
    }

    @Override
    public void navigateToFragments() {

    }

    @Override
    public void setSortedData(List<CustomizedProduct> product) {

    }

    @Override
    public void loadUserBalance() {

    }

    @Override
    public void updateBalanceAmount(String amount) {

    }

    @Override
    public void showToastMessage(String message) {

    }

    @Override
    public void showNoInternetError() {

    }

    @Subscribe
    public void OnEvent(OnProductBuyClickEvent event){
        navigateToBuyProduct(event.buyProduct());
    }
}
