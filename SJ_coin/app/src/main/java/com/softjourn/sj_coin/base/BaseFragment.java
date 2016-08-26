package com.softjourn.sj_coin.base;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Utils;

public abstract class BaseFragment extends Fragment {

    ProgressDialog mProgressDialog;

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
        showToast(getString(R.string.internet_turned_off));
        hideProgress();
    }

    public void showToast(String text) {
        Utils.showErrorToast(getActivity(), text, Gravity.CENTER);
    }

    public void showProgress(String message) {
        mProgressDialog = new ProgressDialog(getActivity(), R.style.Base_V7_Theme_AppCompat_Dialog);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
