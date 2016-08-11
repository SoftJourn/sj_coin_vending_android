package com.softjourn.sj_coin.activities.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.ProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseFragment;
import com.softjourn.sj_coin.callbacks.OnLogin;
import com.softjourn.sj_coin.callbacks.OnProductsListReceived;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.presenters.IVendingMachinePresenter;
import com.softjourn.sj_coin.presenters.VendingMachinePresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ad1 on 04.08.2016.
 */
public class ProductsListOtherFragment extends BaseFragment implements IProductsListFragment,Constants{

    private static final String EXTRAS_PRODUCTS_MACHINE_LIST = "PRODUCTS_MACHINE_LIST";
    List<Product> mProductList;
    @Bind(R.id.list_items_recycler_view)
    RecyclerView machineItems;

    private int mColumns;
    private String mSelectedMachine;
    private IVendingMachinePresenter mPresenter;
    private ProductItemsAdapter mProductAdapter;

    public static ProductsListOtherFragment newInstance() {
        return new ProductsListOtherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products_machine_view, container, false);

        ButterKnife.bind(this, view);

        mSelectedMachine = String.valueOf(Preferences.retrieveIntObject(SELECTED_MACHINE_ID));
        mColumns = Preferences.retrieveIntObject(SELECTED_MACHINE_COLUMNS);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),mColumns);
        machineItems.setLayoutManager(mLayoutManager);
        mProductAdapter = new ProductItemsAdapter(MACHINE_VIEW);
        machineItems.setAdapter(mProductAdapter);

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
            mProductList = savedInstanceState.getParcelableArrayList(EXTRAS_PRODUCTS_MACHINE_LIST);
            loadData(mProductList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRAS_PRODUCTS_MACHINE_LIST, new ArrayList<Parcelable>(mProductList));
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
