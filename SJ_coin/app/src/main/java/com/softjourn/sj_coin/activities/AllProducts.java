package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Button;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnProductBuyClickEvent;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.NewProduct;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;

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

    List<CustomizedProduct> mProductList;

    @Bind(R.id.list_items_recycler_view)
    RecyclerView mMachineItems;

    @Bind(R.id.button_sort_name)
    Button buttonSortByName;

    @Bind(R.id.button_sort_price)
    Button buttonSortByPrice;

    @OnClick(R.id.button_sort_name)
    public void onClickSortByName(){
        sortByName(mSortingByNameForward);
    }

    @OnClick(R.id.button_sort_price)
    public void onClickSortByPrice(){
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

        if (mListState != null){
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
        return true;
    }

    @Override
    public void loadData(List<Drink> drinks,List<Snack> snacks) {
        mProductDrinkList = drinks;
        mProductSnackList = snacks;
        mProductAdapter.setAllProducts(drinks,snacks);

        mProductList = mProductAdapter.getCustomizedProductList();
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
        onCreateDialog(product,mPresenter);
    }

    @Override
    public void navigateToFragments() {

    }

    @Override
    public void setSortedData(List<CustomizedProduct> product) {
        mProductAdapter.setSortedData(mProductList);
    }

    @Override
    public void showToastMessage(String message) {

    }

    @Override
    public void showNoInternetError() {

    }

    private void sortByName(boolean isSortingForward) {
        buttonSortByName.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
        buttonSortByPrice.setBackgroundColor(getResources().getColor(R.color.transparent));
        mPresenter.sortByName(mProductList,isSortingForward);
        mSortingByNameForward = !mSortingByNameForward;
    }

    private void sortByPrice(boolean isSortingForward) {
        buttonSortByPrice.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
        buttonSortByName.setBackgroundColor(getResources().getColor(R.color.transparent));
        mPresenter.sortByPrice(mProductList,isSortingForward);
        mSortingByPriceForward = !mSortingByPriceForward;
    }

    @Subscribe
    public void OnEvent(OnProductBuyClickEvent event){
        navigateToBuyProduct(event.buyProduct());
    }

}
