package com.softjourn.sj_coin.activities.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.adapters.ProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseFragment;
import com.softjourn.sj_coin.callbacks.OnLogin;
import com.softjourn.sj_coin.callbacks.OnProductsListReceived;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.presenters.IVendingMachinePresenter;
import com.softjourn.sj_coin.presenters.VendingMachinePresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ad1 on 04.08.2016.
 */
public class ProductsListSnacksFragment extends BaseFragment implements IProductsListFragment,Constants {

    private static final String EXTRAS_PRODUCTS_LIST = "PRODUCTS_LIST";

    List<Product> mProductList;
    @Bind(R.id.list_items_recycler_view)
    RecyclerView machineItems;
    private String mSelectedMachine;
    private IVendingMachinePresenter mPresenter;
    private ProductItemsAdapter mProductAdapter;

    public static ProductsListSnacksFragment newInstance() {
        return new ProductsListSnacksFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products_list, container, false);

        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        machineItems.setLayoutManager(mLayoutManager);
        mProductAdapter = new ProductItemsAdapter(LIST_VIEW);
        machineItems.setAdapter(mProductAdapter);

        VendingActivity activity = (VendingActivity)getActivity();
        mSelectedMachine = activity.mSelectedMachine;

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new VendingMachinePresenter();
        if (savedInstanceState == null) {
            mPresenter.callProductsList(mSelectedMachine);
            ProgressDialogUtils.showDialog(getActivity(),"Loading");
        } else {
            mProductList = savedInstanceState.getParcelableArrayList(EXTRAS_PRODUCTS_LIST);
            loadData(mProductList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRAS_PRODUCTS_LIST, new ArrayList<Parcelable>(mProductList));
    }

    @Override
    public void loadData(List<Product> data) {
        mProductList = data;
        mProductAdapter.setData(data);
    }

    @Subscribe
    public void OnEvent(OnProductsListReceived event) {
        mProductList = event.getProductsList();
        onCallSuccess();
        loadData(mProductList);
    }

    @Subscribe
    public void OnEvent(OnLogin event) {
        if (event.isSuccess()) {
            onCallSuccess();
        } else {
            onCallFailed();
        }
    }
}
