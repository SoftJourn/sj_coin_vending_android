package com.softjourn.sj_coin.base;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.utils.Utils;

import java.util.List;

public abstract class BaseFragment extends Fragment {

    ProgressDialog mProgressDialog;

    public boolean mSortingByNameForward = true;
    public boolean mSortingByPriceForward = true;


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

    protected void onNoInternetAvailable() {
//        showToast(getString(R.string.internet_turned_off));
//        hideProgress();
    }

    public void showToast(String text) {
        Utils.showErrorToast(getActivity(), text, Gravity.CENTER);
    }

    public void showProgress(String message) {
//        mProgressDialog = new ProgressDialog(getActivity(), R.style.Base_V7_Theme_AppCompat_Dialog);
//        mProgressDialog.setMessage(message);
//        mProgressDialog.show();
    }

    public void hideProgress() {
//        if (mProgressDialog != null) {
//            mProgressDialog.dismiss();
//        }
    }

    public void sortByName(boolean isSortingForward, List<? extends CustomizedProduct> product, VendingContract.Presenter presenter, Button buttonName, Button buttonPrice) {
        buttonName.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
        buttonPrice.setBackgroundColor(getResources().getColor(R.color.transparent));
        presenter.sortByName(product, isSortingForward);
        mSortingByNameForward = !mSortingByNameForward;
    }

    public void sortByPrice(boolean isSortingForward, List<? extends CustomizedProduct> product, VendingContract.Presenter presenter, Button buttonName, Button buttonPrice) {
        buttonPrice.setBackgroundColor(getResources().getColor(R.color.colorScreenBackground));
        buttonName.setBackgroundColor(getResources().getColor(R.color.transparent));
        presenter.sortByPrice(product, isSortingForward);
        mSortingByPriceForward = !mSortingByPriceForward;
    }
}
