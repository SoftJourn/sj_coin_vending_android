package com.softjourn.sj_coin.activities.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.ProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseFragment;
import com.softjourn.sj_coin.callbacks.OnProductsListReceived;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.presenters.IVendingMachinePresenter;
import com.softjourn.sj_coin.presenters.VendingMachinePresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SeeAllProductsFragment extends BaseFragment implements IProductsListFragment,Constants,Extras {
    List<Product> mProductList;

    @Bind(R.id.list_items_recycler_view)
    RecyclerView machineItems;

    private String mSelectedMachine;
    private IVendingMachinePresenter mPresenter;
    private ProductItemsAdapter mProductAdapter;

    public static SeeAllProductsFragment newInstance() {
        return new SeeAllProductsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products_see_all, container, false);

        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        machineItems.setLayoutManager(mLayoutManager);
        mProductAdapter = new ProductItemsAdapter(SEE_ALL_RECYCLER_VIEW);
        machineItems.setAdapter(mProductAdapter);

        mSelectedMachine = String.valueOf(Preferences.retrieveIntObject(SELECTED_MACHINE_ID));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new VendingMachinePresenter();
        if (savedInstanceState == null) {
            mPresenter.callProductsList(mSelectedMachine);
            ProgressDialogUtils.showDialog(getActivity(),getString(R.string.progress_loading));
        } else {
            mProductList = savedInstanceState.getParcelableArrayList(EXTRAS_PRODUCTS_SEE_ALL_LAST_PURCHASES_FRAGMENT);
            loadData(mProductList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRAS_PRODUCTS_SEE_ALL_LAST_PURCHASES_FRAGMENT, new ArrayList<Parcelable>(mProductList));
    }

    @Override
    public void loadData(List<Product> data) {
        mProductList = data;
        mProductAdapter.setData(data);
        onCallSuccess();
    }


    @Subscribe
    public void OnEvent(OnProductsListReceived event) {
        mProductList = event.getProductsList();
        loadData(mProductList);
    }
}
