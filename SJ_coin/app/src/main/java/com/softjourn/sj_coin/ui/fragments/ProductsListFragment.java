package com.softjourn.sj_coin.ui.fragments;

import android.os.Bundle;
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
import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.base.BaseFragment;
import com.softjourn.sj_coin.contratcts.VendingFragmentContract;
import com.softjourn.sj_coin.events.OnRemoveItemFromCategoryFavorite;
import com.softjourn.sj_coin.presenters.VendingFragmentPresenter;
import com.softjourn.sj_coin.ui.activities.SeeAllActivity;
import com.softjourn.sj_coin.ui.activities.VendingActivity;
import com.softjourn.sj_coin.ui.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Extras;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class ProductsListFragment extends BaseFragment implements VendingFragmentContract.View, Const, Extras {

    private interface ProductsListFragmentStrategy{
        View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
        void onChangeFavoriteIcon(String action);
    }

    private String mProductsCategory;

    private static final String TAG_PRODUCTS_CATEGORY = "PRODUCTS CATEGORY";

    private VendingFragmentContract.Presenter mPresenter;
    private FeaturedProductItemsAdapter mProductAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mHeaders;
    private ProductsListFragmentStrategy mStrategy;
    private List<Product> mProductList;


    public static ProductsListFragment newInstance(String category, @Nullable int headers, @Nullable int container) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG_PRODUCTS_CATEGORY, category);
        bundle.putString("HEADER", String.valueOf(headers));
        ProductsListFragment fragment = new ProductsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.list_items_recycler_view)
    RecyclerView mMachineItems;

    @Nullable
    @BindView(R.id.button_sort_name)
    Button mButtonSortByName;

    @Nullable
    @BindView(R.id.button_sort_price)
    Button mButtonSortByPrice;

    @Nullable
    @BindView(R.id.textViewNoProductsInCategory)
    TextView mNoProductsInCategory;

    @Optional
    @OnClick(R.id.button_sort_name)
    public void onClickSortByName() {
        if (mProductList != null && !mProductList.isEmpty()) {
            sortByName(mSortingByNameForward, mProductsCategory, mPresenter, mButtonSortByName, mButtonSortByPrice);
        }
    }

    @Optional
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

        if (getActivity() instanceof SeeAllActivity){
            mStrategy = new ParentSeeAllActivityStrategy();
        } else if (getActivity() instanceof VendingActivity){
            mStrategy = new ParentVendingActivityStrategy();
        }

        View view = mStrategy.onCreateView(inflater, container, savedInstanceState);

        mMachineItems.setLayoutManager(mLayoutManager);

        mMachineItems.setAdapter(mProductAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new VendingFragmentPresenter(this);

        if (savedInstanceState == null) {
            getLocalProductsList();
        }
    }

    /**
     * Retrieves chosen list of products from local storage
     * depends on mProductsCategory;
     */
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
    public void setSortedData(List<Product> product) {
        mProductAdapter.setData(product);
    }

    @Override
    public void changeFavoriteIcon(String action) {
        mStrategy.onChangeFavoriteIcon(action);
    }

    /**
     * Is used to hide category (Vending Activity case) when there is no products
     * and to show special message (See All Activity) when there is no products
     */
    @Override
    public void showDataAfterRemovingFavorites(List<Product> productsList) {
        if (mProductsCategory.equals(FAVORITES)) {

            if (productsList != null && !productsList.isEmpty()) {
                try {
                    ((VendingActivity) getActivity()).showContainer(mHeaders, ((ViewGroup) getView().getParent()).getId());
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            } else {
                hideContainer();
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
                e.printStackTrace();
            }
        } else {
            hideContainer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mPresenter = null;
    }

    /**
     * Hides Container and headers or shows special message
     * when there are no products to show in chosen category
     */
    private void hideContainer() {
        try {
            ((VendingActivity) getActivity()).hideContainer(mHeaders, ((ViewGroup) getView().getParent()).getId());
        } catch (ClassCastException e) {
            if (mNoProductsInCategory != null) {
                mNoProductsInCategory.setVisibility(View.VISIBLE);
            }
            if (mButtonSortByName != null && mButtonSortByPrice != null) {
                mButtonSortByName.setVisibility(View.GONE);
                mButtonSortByPrice.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Strategy implementation for case of SeeAll activity should be as container for fragment
     */
    private class ParentSeeAllActivityStrategy implements ProductsListFragmentStrategy{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_product_see_all_snacks_drinks, container, false);
            ButterKnife.bind(ProductsListFragment.this, view);
            (getActivity()).setTitle(mProductsCategory);

            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mProductAdapter = new FeaturedProductItemsAdapter(mProductsCategory, SEE_ALL_SNACKS_DRINKS_RECYCLER_VIEW, getActivity());

            if (mProductsCategory.equals(FAVORITES)) {
                if (mButtonSortByName != null) {
                    mButtonSortByName.setVisibility(View.GONE);
                }
                if (mButtonSortByPrice != null) {
                    mButtonSortByPrice.setVisibility(View.GONE);
                }
            }
            ((SeeAllActivity) getActivity()).productsList(mProductAdapter, mProductsCategory);
            ((SeeAllActivity) getActivity()).setButtons(mButtonSortByName, mButtonSortByPrice);
            return view;
        }

        @Override
        public void onChangeFavoriteIcon(String action) {
            ((SeeAllActivity) getActivity()).hideProgress();
            if (action.equals(ACTION_ADD_FAVORITE) || (!action.equals(ACTION_ADD_FAVORITE) && !mProductsCategory.equals(FAVORITES))) {
                mProductAdapter.notifyDataChanges();
            }
        }
    }

    /**
     * Strategy implementation for case of Vending activity should be as container for fragment
     */
    private class ParentVendingActivityStrategy implements ProductsListFragmentStrategy{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_products_list, container, false);
            ButterKnife.bind(ProductsListFragment.this, view);
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            mProductAdapter = new FeaturedProductItemsAdapter(mProductsCategory, null, getActivity());
            view.startAnimation(AnimationUtils.loadAnimation(App.getContext(), R.anim.slide_left));
            return view;
        }

        @Override
        public void onChangeFavoriteIcon(String action) {

        }
    }

    @Subscribe
    public void OnEvent(OnRemoveItemFromCategoryFavorite event) {
        mProductAdapter.removeItem(event.getId());
    }

}
