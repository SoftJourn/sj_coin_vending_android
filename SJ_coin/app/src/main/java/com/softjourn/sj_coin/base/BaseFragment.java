package com.softjourn.sj_coin.base;


import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;
import com.softjourn.sj_coin.utils.Utils;

public abstract class BaseFragment extends Fragment {

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


    public void hideProgress(){ProgressDialogUtils.dismiss();}

}
