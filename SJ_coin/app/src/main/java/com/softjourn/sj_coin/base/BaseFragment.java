package com.softjourn.sj_coin.base;


import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.contratcts.VendingContract;

public abstract class BaseFragment extends Fragment {

    protected boolean mSortingByNameForward = true;
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

    protected void sortByName(boolean isSortingForward, String productsCategory, VendingContract.Presenter presenter, Button buttonName, Button buttonPrice) {
        buttonName.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
        buttonPrice.setBackgroundColor(getResources().getColor(R.color.transparent));
        presenter.sortByName(productsCategory, isSortingForward);
        mSortingByNameForward = !mSortingByNameForward;
    }

    protected void sortByPrice(boolean isSortingForward, String productsCategory, VendingContract.Presenter presenter, Button buttonName, Button buttonPrice) {
        buttonPrice.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
        buttonName.setBackgroundColor(getResources().getColor(R.color.transparent));
        presenter.sortByPrice(productsCategory, isSortingForward);
        mSortingByPriceForward = !mSortingByPriceForward;
    }
}
