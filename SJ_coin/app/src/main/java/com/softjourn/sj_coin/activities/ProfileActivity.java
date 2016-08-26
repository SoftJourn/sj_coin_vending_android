package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.PicassoTrustAdapter;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.model.Account;
import com.softjourn.sj_coin.presenters.ProfilePresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileContract.View, Constants {

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

        super.mProfileIsVisible = true;

        ButterKnife.bind(this);

        mPresenter = new ProfilePresenter(this);

        mPresenter.getAccount();
    }

    @Override
    protected void onStop() {
        super.onStop();
        super.mProfileIsVisible = false;
    }

    @Override
    protected void onDestroy() {
        super.mProfileIsVisible = false;
        super.onDestroy();
    }

    @Override
    public void showBalance(Account account) {
        mUserBalance.setText(account.getAmount());
    }

    @Override
    public void setUserName(String message) {
        mUserName.setText(message);
    }

    @Override
    public void setPhoto(Account account) {
        if (TextUtils.isEmpty(account.getImage())) {
            Picasso.with(App.getContext()).load(R.drawable.softjourn_logo).into(mUserPhoto);
        } else {
            PicassoTrustAdapter.getInstance(App.getContext()).load(URL_COIN_SERVICE + account.getImage()).into(mUserPhoto);
        }
    }

    @Override
    public void showProgress(String message) {
        super.showProgress(message);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void showToastMessage(String message) {

    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }
}
