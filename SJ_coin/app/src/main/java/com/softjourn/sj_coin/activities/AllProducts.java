package com.softjourn.sj_coin.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.softjourn.sj_coin.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnAddFavoriteEvent;
import com.softjourn.sj_coin.callbacks.OnProductBuyClickEvent;
import com.softjourn.sj_coin.callbacks.OnRemoveFavoriteEvent;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.LastAdded;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;
import com.softjourn.sj_coin.utils.Navigation;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllProducts extends BaseActivity implements VendingContract.View, Constants, Extras {

    private boolean mSortingByNameForward = true;
    private boolean mSortingByPriceForward = true;

    List<Drink> mProductDrinkList;
    List<Snack> mProductSnackList;

    private Menu mMenu;

    SearchView mSearch;

    List<CustomizedProduct> mProductList;

    @Bind(R.id.list_items_recycler_view)
    RecyclerView mMachineItems;

    @Bind(R.id.button_sort_name)
    Button buttonSortByName;

    @Bind(R.id.button_sort_price)
    Button buttonSortByPrice;

    @OnClick(R.id.button_sort_name)
    public void onClickSortByName() {
        sortByName(mSortingByNameForward);
    }

    @OnClick(R.id.button_sort_price)
    public void onClickSortByPrice() {
        sortByPrice(mSortingByPriceForward);
    }

    private VendingContract.Presenter mPresenter;
    private FeaturedProductItemsAdapter mProductAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Parcelable mListState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);
        mMachineItems.setLayoutManager(mLayoutManager);
        mProductAdapter = new FeaturedProductItemsAdapter(null, SEE_ALL_SNACKS_DRINKS_RECYCLER_VIEW);
        mMachineItems.setAdapter(mProductAdapter);

        mPresenter = new VendingPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.activityCategory, R.layout.spinner_dropdown_item);
        Spinner navigationSpinner = new Spinner(getApplicationContext());
        navigationSpinner.setAdapter(spinnerAdapter);
        toolbar.addView(navigationSpinner, 0);
        toolbar.canShowOverflowMenu();
        toolbar.showOverflowMenu();

        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Navigation.navigationOnCategoriesAllProducts(position, AllProducts.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (mListState != null) {
            mMachineItems.getLayoutManager().onRestoreInstanceState(mListState);
        } else {
            mPresenter.getLocalProductList();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            mListState = mLayoutManager.onSaveInstanceState();
            outState.putParcelable(EXTRAS_PRODUCTS_ALL_LIST, mListState);
        } else {
            mPresenter.getLocalProductList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_search).setVisible(true);

        super.mAllItemsVisible = true;
        this.mMenu = menu;
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        mSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();

        mSearch.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        mSearch.setQueryHint("Product Name");

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(AllProducts.this.getCurrentFocus().getWindowToken(), 0);
                mSearch.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                if (TextUtils.isEmpty(query)) {
                    mProductAdapter.getFilter().filter("");
                } else {
                    mProductAdapter.getFilter().filter(query.toString());
                }
                buttonSortByName.setEnabled(false);
                buttonSortByPrice.setEnabled(false);
                return true;
            }

        });

        mSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                buttonSortByName.setEnabled(true);
                buttonSortByPrice.setEnabled(true);
                return false;
            }
        });

        return true;

    }

    @Override
    public void loadData(List<Drink> drinks, List<Snack> snacks) {
        mProductDrinkList = drinks;
        mProductSnackList = snacks;
        mProductAdapter.setAllProducts(drinks, snacks);

        mProductList = mProductAdapter.getCustomizedProductList();
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
        onCreateDialog(product, mPresenter);
    }

    @Override
    public void navigateToFragments() {

    }

    @Override
    public void setSortedData(List<CustomizedProduct> product) {
        mProductAdapter.setSortedData(mProductList);
    }

    @Override
    public void loadUserBalance() {

    }

    @Override
    public void updateBalanceAmount(String amount) {

    }

    @Override
    public void changeFavoriteIcon() {
        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFavorites(List<CustomizedProduct> data) {

    }

    @Override
    public void showToastMessage(String message) {
        showToast(message);
    }

    @Override
    public void showNoInternetError() {

    }

    private void sortByName(boolean isSortingForward) {
        buttonSortByName.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
        buttonSortByPrice.setBackgroundColor(getResources().getColor(R.color.transparent));
        mPresenter.sortByName(mProductList, isSortingForward);
        mSortingByNameForward = !mSortingByNameForward;
        mSearch.clearFocus();
    }

    private void sortByPrice(boolean isSortingForward) {
        buttonSortByPrice.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
        buttonSortByName.setBackgroundColor(getResources().getColor(R.color.transparent));
        mPresenter.sortByPrice(mProductList, isSortingForward);
        mSortingByPriceForward = !mSortingByPriceForward;;
    }

    @Subscribe
    public void OnEvent(OnProductBuyClickEvent event) {
        navigateToBuyProduct(event.buyProduct());
    }

    @Subscribe
    public void OnEvent(OnAddFavoriteEvent event){
        mPresenter.addToFavorite(String.valueOf(event.addFavorite().getId()));
    }

    @Subscribe
    public void OnEvent(OnRemoveFavoriteEvent event){
        mPresenter.removeFromFavorite(String.valueOf(event.removeFavorite().getId()));
    }

}
