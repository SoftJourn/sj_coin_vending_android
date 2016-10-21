package com.softjourn.sj_coin.activities.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.SeeAllActivity;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseFragment;
import com.softjourn.sj_coin.contratcts.VendingFragmentContract;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.presenters.VendingFragmentPresenter;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Extras;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductsListFragment extends BaseFragment implements VendingFragmentContract.View, Const, Extras {

    private String mProductsCategory;

    private static final String TAG_PRODUCTS_CATEGORY = "PRODUCTS CATEGORY";

    private VendingFragmentContract.Presenter mPresenter;
    private FeaturedProductItemsAdapter mProductAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mHeaders;

    public static ProductsListFragment newInstance(String category, @Nullable int headers, @Nullable int container) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG_PRODUCTS_CATEGORY, category);
        bundle.putString("HEADER", String.valueOf(headers));
        ProductsListFragment fragment = new ProductsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private List<Product> mProductList;

    private Parcelable mListState;

    @Bind(R.id.list_items_recycler_view)
    RecyclerView mMachineItems;

    @Nullable
    @Bind(R.id.button_sort_name)
    Button mButtonSortByName;

    @Nullable
    @Bind(R.id.button_sort_price)
    Button mButtonSortByPrice;

    @Nullable
    @Bind(R.id.textViewNoProductsInCategory)
    TextView mNoProductsInCategory;

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
        mHeaders = Integer.parseInt(getArguments().getString("HEADER"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view;

        switch (getActivity().getLocalClassName()) {
            case "activities.VendingActivity":
                view = inflater.inflate(R.layout.fragment_products_list, container, false);
                ButterKnife.bind(this, view);
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mProductAdapter = new FeaturedProductItemsAdapter(mProductsCategory, null);
                view.startAnimation(AnimationUtils.loadAnimation(App.getContext(), R.anim.slide_left));
                break;

            case "activities.SeeAllActivity":
                view = inflater.inflate(R.layout.fragment_product_see_all_snacks_drinks, container, false);
                ButterKnife.bind(this, view);
                (getActivity()).setTitle(mProductsCategory);
                ((SeeAllActivity) getActivity()).setNavigationItemChecked(mProductsCategory);
                view.startAnimation(AnimationUtils.loadAnimation(App.getContext(), R.anim.slide_left));
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
        mPresenter = new VendingFragmentPresenter(this, this.getActivity());

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
                mPresenter.getLocalFavorites();
                break;
            case LAST_ADDED:
                mHeaders = R.id.newProductsHeader;
                mPresenter.getLocalLastAddedProducts();
                break;
            case BEST_SELLERS:
                mHeaders = R.id.bestSellersHeader;
                mPresenter.getLocalBestSellers();
                break;
            default:
                mPresenter.getLocalCategoryProducts(mProductsCategory);
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
    public void setSortedData(List<Product> product) {
        mProductAdapter.setData(product);
    }

    @Override
    public void changeFavoriteIcon(String action) {
        if (action.equals(ACTION_ADD_FAVORITE)) {
            if (getActivity().getLocalClassName().equals("activities.SeeAllActivity")) {
                ((SeeAllActivity) getActivity()).hideProgress();
            }
            mProductAdapter.notifyDataSetChanged();
        } else {
            if (getActivity().getLocalClassName().equals("activities.SeeAllActivity")) {
                ((SeeAllActivity) getActivity()).hideProgress();
                if (!mProductsCategory.equals(FAVORITES)) {
                    mProductAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void showDataAfterRemovingFavorites(List<Product> productsList) {
        if (mProductsCategory.equals(FAVORITES)) {

            if (productsList != null && !productsList.isEmpty()) {
                mProductList = productsList;
                mProductAdapter.setData(productsList);

                try {
                    ((VendingActivity) getActivity()).showContainer(mHeaders, ((ViewGroup) getView().getParent()).getId());
                } catch (ClassCastException e) {
                    return;
                }
            } else {
                try {
                    ((VendingActivity) getActivity()).hideContainer(mHeaders, ((ViewGroup) getView().getParent()).getId());
                } catch (ClassCastException e) {
                    if (mNoProductsInCategory != null) {
                        mNoProductsInCategory.setVisibility(View.VISIBLE);
                    }
                    if (mButtonSortByName != null && mButtonSortByPrice != null) {
                        mButtonSortByName.setBackgroundColor(getResources().getColor(R.color.transparent));
                        mButtonSortByPrice.setBackgroundColor(getResources().getColor(R.color.transparent));
                        mButtonSortByName.setEnabled(false);
                        mButtonSortByPrice.setEnabled(false);
                    }
                }
            }
        }
    }

    @Override
    public void loadData(List<Product> productsList) {
        if (productsList != null && !productsList.isEmpty()) {
            mProductList = productsList;
            mProductAdapter.setData(productsList);

            try {
                ((VendingActivity) getActivity()).showContainer(mHeaders, ((ViewGroup) getView().getParent()).getId());
            } catch (ClassCastException e) {
                return;
            }
        } else {
            try {
                ((VendingActivity) getActivity()).hideContainer(mHeaders, ((ViewGroup) getView().getParent()).getId());
            } catch (ClassCastException e) {
                if (mNoProductsInCategory != null) {
                    mNoProductsInCategory.setVisibility(View.VISIBLE);
                }
                if (mButtonSortByName != null && mButtonSortByPrice != null) {
                    mButtonSortByName.setBackgroundColor(getResources().getColor(R.color.transparent));
                    mButtonSortByPrice.setBackgroundColor(getResources().getColor(R.color.transparent));
                    mButtonSortByName.setEnabled(false);
                    mButtonSortByPrice.setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
