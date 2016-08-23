package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.model.Balance;
import com.softjourn.sj_coin.presenters.ProfilePresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileContract.View,Constants {

    @Bind(R.id.profile_user_photo)
    ImageView mUserPhoto;

    @Bind(R.id.profile_user_name)
    TextView mUserName;

    @Bind(R.id.profile_amount_available)
    TextView mUserBalance;

    private ProfileContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (this.getWindow().getDecorView().isShown())
        {finish();}

            ButterKnife.bind(this);
            mPresenter = new ProfilePresenter(this);
            mPresenter.getUserName();

            mPresenter.getBalance();
    }

    @Override
    public void showBalance(Balance balance) {
        mUserBalance.setText(balance.getAmount());
    }

    @Override
    public void setUserName(String message) {
        mUserName.setText(message);
    }

    @Override
    public void showProgress(String message) {
        ProgressDialogUtils.showDialog(this, message);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void showToastMessage() {

    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }
}
