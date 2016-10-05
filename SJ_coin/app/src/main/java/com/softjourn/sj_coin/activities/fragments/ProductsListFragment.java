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
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Extras;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andriy Ksenych on 15.09.2016.
 */
public class ProductsListFragment extends BaseFragment implements VendingContract.View, Const, Extras {

    private String mProductsCategory;

    private static final String TAG_PRODUCTS_CATEGORY = "PRODUCTS CATEGORY";

    public static ProductsListFragment newInstance(String category) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG_PRODUCTS_CATEGORY, category);
        ProductsListFragment fragment = new ProductsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private VendingContract.Presenter mPresenter;
    private FeaturedProductItemsAdapter mProductAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mHeaders;
    private int mContainer;

    List<Product> mProductList;

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
        if (mProductList != null && !mProductList.isEmpty()) {
            sortByName(mSortingByNameForward, mProductsCategory, mPresenter, mButtonSortByName, mButtonSortByPrice);
        }
    }

    @Nullable
    @OnClick(R.id.button_sort_price)
    public void onClickSortByPrice() {
        if (mProductList != null && !mProductList.isEmpty()) {
            sortByPrice(mSortingByPriceForward, mProductsCategory, mPresenter, mButtonSortByName, mButtonSortByPrice);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductsCategory = getArguments().getString(TAG_PRODUCTS_CATEGORY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        switch (getActivity().getLocalClassName()) {
            case "activities.VendingActivity":
                view = inflater.inflate(R.layout.fragment_products_list, container, false);
                ButterKnife.bind(this, view);
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mProductAdapter = new FeaturedProductItemsAdapter(mProductsCategory, null);
                break;

            case "activities.SeeAllActivity":
                view = inflater.inflate(R.layout.fragment_product_see_all_snacks_drinks, container, false);
                ButterKnife.bind(this, view);
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mProductAdapter = new FeaturedProductItemsAdapter(mProductsCategory, SEE_ALL_SNACKS_DRINKS_RECYCLER_VIEW);
                break;

            default:
                view = inflater.inflate(R.layout.fragment_products_list, container, false);
                ButterKnife.bind(this, view);
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mProductAdapter = new FeaturedProductItemsAdapter(mProductsCategory, null);
                break;
        }

        mMachineItems.setLayoutManager(mLayoutManager);

        mMachineItems.setAdapter(mProductAdapter);

        if (getActivity().getLocalClassName().equals("activities.SeeAllActivity")) {
            ((SeeAllActivity) getActivity()).productsList(mProductAdapter);
            ((SeeAllActivity) getActivity()).setButtons(mButtonSortByName, mButtonSortByPrice);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new VendingPresenter(this,this.getActivity());

        if (savedInstanceState == null) {
            getLocalProductsList();
        } else {
            mMachineItems.getLayoutManager().onRestoreInstanceState(mListState);
        }
    }

    private void getLocalProductsList() {
        switch (mProductsCategory) {
            case ALL_ITEMS:
                mPresenter.getLocalProductList();
                break;
            case FAVORITES:
                mHeaders = R.id.favoritesHeader;
                mContainer = R.id.container_fragment_products_list_favorites;
                mPresenter.getLocalFavorites();
                break;
            case LAST_ADDED:
                mHeaders = R.id.newProductsHeader;
                mContainer = R.id.container_fragment_products_list_new_products;
                mPresenter.getLocalLastAddedProducts();
                break;
            case BEST_SELLERS:
                mHeaders = R.id.bestSellersHeader;
                mContainer = R.id.container_fragment_products_list_best_sellers;
                mPresenter.getLocalBestSellers();
                break;
            case SNACKS:
                mHeaders = R.id.snacksHeader;
                mContainer = R.id.container_fragment_products_list_snacks;
                mPresenter.getLocalSnacks();
                break;
            case DRINKS:
                mHeaders = R.id.drinksHeader;
                mContainer = R.id.container_fragment_products_list_drinks;
                mPresenter.getLocalDrinks();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            mListState = mLayoutManager.onSaveInstanceState();
            outState.putParcelable(EXTRAS_PRODUCTS_LIST, mListState);
        } else {
            getLocalProductsList();
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
    public void navigateToBuyProduct(Product product) {

    }

    @Override
    public void navigateToFragments() {

    }

    @Override
    public void setSortedData(List<Product> product) {
        mProductAdapter.setData(product);
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
    public void loadData(List<Product> productsList) {
        if (!productsList.isEmpty()) {
            mProductList = productsList;
            mProductAdapter.setData(productsList);

            try {
                ((VendingActivity) getActivity()).showContainer(mHeaders, ((ViewGroup)getView().getParent()).getId());
            } catch (ClassCastException e) {}

        } else {
            try {
                ((VendingActivity) getActivity()).hideContainer(mHeaders, ((ViewGroup)getView().getParent()).getId());
            } catch (ClassCastException e) {
                ((SeeAllActivity) getActivity()).showToast("There is currently no Products in chosen category");
                if (mButtonSortByName != null && mButtonSortByPrice != null) {
                    mButtonSortByName.setEnabled(false);
                    mButtonSortByPrice.setEnabled(false);
                }
            }
        }
    }


    @Override
    public void showToastMessage(String message) {

    }

    @Override
    public void showNoInternetError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
