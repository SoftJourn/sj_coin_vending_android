package com.softjourn.sj_coin.activities.fragments.seeAll;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseFragment;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.NewProduct;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;
import com.softjourn.sj_coin.utils.Navigation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SeeAllProductsFragment extends BaseFragment implements VendingContract.View,Constants,Extras {

    public static SeeAllProductsFragment newInstance() {
        return new SeeAllProductsFragment();
    }

    List<Product> mProductList;

    @Bind(R.id.list_items_recycler_view)
    RecyclerView machineItems;

    private VendingContract.Presenter mPresenter;
    private FeaturedProductItemsAdapter mProductAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_products_see_all, container, false);

        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        machineItems.setLayoutManager(mLayoutManager);
        mProductAdapter = new FeaturedProductItemsAdapter(getActivity().getIntent().getStringExtra(EXTRAS_CATEGORY),SEE_ALL_RECYCLER_VIEW);
        machineItems.setAdapter(mProductAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new VendingPresenter(this);

        if (savedInstanceState == null) {
            mPresenter.getLocalProductList();
        } else {

            mProductList = savedInstanceState.getParcelableArrayList(EXTRAS_PRODUCTS_SEE_ALL_FRAGMENT);
            loadData(mProductList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putParcelableArrayList(EXTRAS_PRODUCTS_SEE_ALL_FRAGMENT, new ArrayList<Parcelable>(mProductList));
        } else {
            mPresenter.getLocalProductList();
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
        mProductList = data;
        mProductAdapter.setData(data);
        hideProgress();
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
    public void navigateToBuyProduct(Product product) {
        Navigation.goToProductActivity(getActivity(),product);
    }

    @Override
    public void showToastMessage(String message) {

    }
}
