package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.PurchaseHistoryItemsAdapter;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.model.History;
import com.softjourn.sj_coin.model.accountInfo.Account;
import com.softjourn.sj_coin.presenters.ProfilePresenter;
import com.softjourn.sj_coin.utils.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileContract.View, Constants {

    @Bind(R.id.profile_coins_label)
    TextView mCoinsLabel;

    @Bind(R.id.profile_user_name)
    TextView mUserName;

    @Bind(R.id.profile_amount_available)
    TextView mUserBalance;

    @Bind(R.id.list_items_recycler_view)
    RecyclerView mHistoryList;

    private ProfileContract.Presenter mPresenter;
    private PurchaseHistoryItemsAdapter mHistoryAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        super.mProfileIsVisible = true;

        ButterKnife.bind(this);

        mPresenter = new ProfilePresenter(this);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mHistoryAdapter = new PurchaseHistoryItemsAdapter();
        mHistoryList.setLayoutManager(mLayoutManager);
        mHistoryList.setAdapter(mHistoryAdapter);

        mPresenter.getHistory();
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
        mCoinsLabel.setVisibility(View.VISIBLE);
        mUserBalance.setText(account.getAmount());
    }

    @Override
    public void setUserName(String message) {
        mUserName.setText(message);
    }

    @Override
    public void setData(List<History> data) {
        mHistoryAdapter.setData(data);
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
