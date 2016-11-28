package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.PurchaseHistoryItemsAdapter;
import com.softjourn.sj_coin.api_models.History;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.presenters.ProfilePresenter;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileContract.View, Const {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        super.mProfileIsVisible = true;

        ButterKnife.bind(this);

        mPresenter = new ProfilePresenter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mHistoryAdapter = new PurchaseHistoryItemsAdapter();
        mHistoryList.setLayoutManager(layoutManager);
        mHistoryList.setAdapter(mHistoryAdapter);

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
        mPresenter.onDestroy();
        mPresenter = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.select_machine).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                mPresenter.logOut(Preferences.retrieveStringObject(REFRESH_TOKEN));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showBalance(String amount) {
        mCoinsLabel.setVisibility(View.VISIBLE);
        mUserBalance.setText(amount);
        mUserBalance.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom));
    }

    @Override
    public void setUserName(String message) {
        mUserName.setText(message);
        mUserName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom));
    }

    @Override
    public void setData(List<History> history) {
        mHistoryAdapter.setData(history);
    }

    @Override
    public void logOut() {
        Utils.clearUsersData();
        Navigation.goToLoginActivity(this);
        finish();
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
