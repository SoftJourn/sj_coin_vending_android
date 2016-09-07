package com.softjourn.sj_coin.activities.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.SeeAllActivity;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseFragment;
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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductsListLastAddedFragment extends BaseFragment implements VendingContract.View, Constants, Extras{

    public static ProductsListLastAddedFragment newInstance() {
        return new ProductsListLastAddedFragment();
    }

    private VendingContract.Presenter mPresenter;
    private FeaturedProductItemsAdapter mProductAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<LastAdded> mProductList;

    List<CustomizedProduct> mCustomizedList;

    Parcelable mListState;

    @Bind(R.id.list_items_recycler_view)
    RecyclerView mMachineItems;

    @Nullable
    @Bind(R.id.button_sort_name)
    Button mButtonSortByName;

    @Nullable
    @Bind(R.id.button_sort_price)
    Button mButtonSortByPrice;

    @Nullable
    @OnClick(R.id.button_sort_name)
    public void onClickSortByName() {
        sortByName(mSortingByNameForward, mCustomizedList, mPresenter, mButtonSortByName, mButtonSortByPrice);
    }

    @Nullable
    @OnClick(R.id.button_sort_price)
    public void onClickSortByPrice() {
        sortByPrice(mSortingByPriceForward, mCustomizedList, mPresenter, mButtonSortByName, mButtonSortByPrice);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        switch (getActivity().getLocalClassName()) {
            case "activities.VendingActivity":
                view = inflater.inflate(R.layout.fragment_products_list, container, false);
                ButterKnife.bind(this, view);
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mProductAdapter = new FeaturedProductItemsAdapter(LAST_ADDED, null);
                break;

            case "activities.SeeAllActivity":
                view = inflater.inflate(R.layout.fragment_product_see_all_snacks_drinks, container, false);

                mButtonSortByPrice = (Button)view.findViewById(R.id.button_sort_price);
                mButtonSortByName = (Button)view.findViewById(R.id.button_sort_name);

                ButterKnife.bind(this, view);
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mProductAdapter = new FeaturedProductItemsAdapter(LAST_ADDED, SEE_ALL_SNACKS_DRINKS_RECYCLER_VIEW);
                break;

            default:
                view = inflater.inflate(R.layout.fragment_products_list, container, false);
                ButterKnife.bind(this, view);
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mProductAdapter = new FeaturedProductItemsAdapter(LAST_ADDED, null);
                break;
        }

        mMachineItems.setLayoutManager(mLayoutManager);

        mMachineItems.setAdapter(mProductAdapter);

        if (getActivity().getLocalClassName().equals("activities.SeeAllActivity"))
        {
            ((SeeAllActivity) getActivity()).productsList(mProductAdapter);
            ((SeeAllActivity)getActivity()).setButtons(mButtonSortByName,mButtonSortByPrice);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new VendingPresenter(this);

        if (savedInstanceState == null) {
            mPresenter.getLocalLastAddedProducts();
        } else {
            mMachineItems.getLayoutManager().onRestoreInstanceState(mListState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            mListState = mLayoutManager.onSaveInstanceState();
            outState.putParcelable(EXTRAS_PRODUCTS_NEW_PRODUCTS_LIST, mListState);
        } else {
            mPresenter.getLocalLastAddedProducts();
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
    public void loadData(List<Drink> drinks, List<Snack> snacks) {

    }

    @Override
    public void loadLastAddedData(List<LastAdded> data) {
        if (data.size() > 0) {
            mProductList = data;
            mProductAdapter.setLastAddedData(data);

            mCustomizedList = mProductAdapter.getCustomizedProductList();
        } else {
            try {
                ((VendingActivity) getActivity()).hideContainer(R.id.newProductsHeader, R.id.container_fragment_products_list_new_products);
            } catch (ClassCastException e) {
                ((SeeAllActivity) getActivity()).showToast("There is currently no Products in chosen category");

                assert mButtonSortByName != null && mButtonSortByPrice!=null;
                mButtonSortByName.setEnabled(false);
                mButtonSortByPrice.setEnabled(false);
            }
        }
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

    }

    @Override
    public void setSortedData(List<CustomizedProduct> product) {
        mProductAdapter.setSortedData(mCustomizedList);
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
    public void showToastMessage(String message) {

    }
}
