package com.softjourn.sj_coin.activities.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseFragment;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.NewProduct;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductsListLastPurchasesFragment extends BaseFragment implements VendingContract.View,Constants,Extras {

    public static ProductsListLastPurchasesFragment newInstance() {
        return new ProductsListLastPurchasesFragment();
    }

    List<MyLastPurchase> mProductList;

    @Bind(R.id.list_items_recycler_view)
    RecyclerView mMachineItems;

    @Bind(R.id.textNoItems)
    TextView mNoProducts;

    private VendingContract.Presenter mPresenter;
    private FeaturedProductItemsAdapter mProductAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        switch (getActivity().getLocalClassName()) {
            case "activities.VendingActivity":
                view = inflater.inflate(R.layout.fragment_products_list, container, false);
                ButterKnife.bind(this, view);
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mProductAdapter = new FeaturedProductItemsAdapter(LAST_PURCHASES, null);
                break;

            case "activities.SeeAllActivity":
                view = inflater.inflate(R.layout.fragment_product_see_all_snacks_drinks, container, false);
                ButterKnife.bind(this, view);
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mProductAdapter = new FeaturedProductItemsAdapter(LAST_PURCHASES, SEE_ALL_SNACKS_DRINKS_RECYCLER_VIEW);
                break;

            default:
                view = inflater.inflate(R.layout.fragment_products_list, container, false);
                ButterKnife.bind(this, view);
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mProductAdapter = new FeaturedProductItemsAdapter(LAST_PURCHASES, null);
                break;
        }

        mMachineItems.setLayoutManager(mLayoutManager);

        mMachineItems.setAdapter(mProductAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new VendingPresenter(this);

        if (savedInstanceState == null) {
            mPresenter.getLocalMyLastPurchase();
        } else {
            mProductList = savedInstanceState.getParcelable(EXTRAS_PRODUCTS_LAST_PURCHASES_LIST);
            loadMyLastPurchaseData(mProductList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putParcelable(EXTRAS_PRODUCTS_LAST_PURCHASES_LIST, mLayoutManager.onSaveInstanceState());
        } else {
            mPresenter.getLocalMyLastPurchase();
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
    public void loadData(List<Product> data) {

    }

    @Override
    public void loadNewProductsData(List<NewProduct> data) {

    }

    @Override
    public void loadBestSellerData(List<BestSeller> data) {

    }

    @Override
    public void loadMyLastPurchaseData(List<MyLastPurchase> data) {
        if (data.size()>0) {
            mNoProducts.setVisibility(View.INVISIBLE);
            mProductList = data;
            mProductAdapter.setMyLastPurchaseData(data);
        }
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
    public void showToastMessage(String message) {

    }
}
