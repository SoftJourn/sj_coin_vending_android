package com.softjourn.sj_coin.base;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.contratcts.VendingFragmentContract;

public abstract class BaseFragment extends Fragment {

    protected boolean mSortingByNameForward = false;
    protected boolean mSortingByPriceForward = true;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    protected void sortByName(boolean isSortingForward, String productsCategory, VendingFragmentContract.Presenter presenter, Button buttonName, Button buttonPrice) {
        mSortingByPriceForward = true;
        buttonName.setBackgroundColor(ContextCompat.getColor(App.getContext(),R.color.colorScreenBackground));
        buttonPrice.setBackgroundColor(ContextCompat.getColor(App.getContext(),R.color.transparent));
        presenter.sortByName(productsCategory, isSortingForward);
        mSortingByNameForward = !mSortingByNameForward;
    }

    protected void sortByPrice(boolean isSortingForward, String productsCategory, VendingFragmentContract.Presenter presenter, Button buttonName, Button buttonPrice) {
        mSortingByNameForward = true;
        buttonPrice.setBackgroundColor(ContextCompat.getColor(App.getContext(),R.color.colorScreenBackground));
        buttonName.setBackgroundColor(ContextCompat.getColor(App.getContext(),R.color.transparent));
        presenter.sortByPrice(productsCategory, isSortingForward);
        mSortingByPriceForward = !mSortingByPriceForward;
    }
}
