package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnCallEvent;
import com.softjourn.sj_coin.callbacks.OnConcreteMachineReceived;
import com.softjourn.sj_coin.callbacks.OnMachinesListReceived;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.presenters.IVendingMachinePresenter;
import com.softjourn.sj_coin.presenters.VendingMachinePresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VendingActivity extends BaseActivity implements Constants {

    @Bind(R.id.textViewLastPurchasesSeeAll)
    TextView seeAllLastPurchasesBtn;

    @Bind(R.id.textViewFeaturedSeeAll)
    TextView seeAllFeaturedBtn;

    @Bind(R.id.textViewBestSellersSeeAll)
    TextView seeAllBestSellersBtn;

    @OnClick({R.id.textViewLastPurchasesSeeAll, R.id.textViewFeaturedSeeAll, R.id.textViewBestSellersSeeAll})
    public void seeAll(View v) {
        switch (v.getId()) {
            case R.id.textViewLastPurchasesSeeAll:
                Navigation.goToSeeAllActivity(this, LAST_PURCHASES);
                break;
            case R.id.textViewFeaturedSeeAll:
                Navigation.goToSeeAllActivity(this, FEATURED);
                break;
            case R.id.textViewBestSellersSeeAll:
                Navigation.goToSeeAllActivity(this, BEST_SELLERS);
                break;
        }
    }

    //Hardcoded to have only one machine
    public String mSelectedMachine = "1";

    private IVendingMachinePresenter mPresenter;

    private BottomBar mBottomBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseActivity.mActivity = this;

        ButterKnife.bind(this);

        mBottomBar = BottomBar.attach(this, savedInstanceState);

        mBottomBar.setItems(R.menu.bottombar_menu);

        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    Log.d("Tag", "first");
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    Log.d("Tag", "firstReselected");
                }
            }
        });

        if (new Date().getTime() / 1000 >= Long.parseLong(Preferences.retrieveStringObject(EXPIRATION_DATE))) {
            callRefreshToken();
        } else {
            callProductsList();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }

    private void callProductsList() {
        if (!isInternetAvailable()) {
            onNoInternetAvailable();
        } else {
            mPresenter = new VendingMachinePresenter();
            mPresenter.callConcreteMachine(mSelectedMachine);
            Navigation.goToProductListFragments(this);
            ProgressDialogUtils.showDialog(this, getString(R.string.progress_loading));
        }
    }

    @Subscribe
    public void OnEvent(OnCallEvent event) {
        if (event.isSuccess()) {
            onCallSuccess();
        } else {
            onCallFailed();
        }
    }

    @Subscribe
    public void OnEvent(OnMachinesListReceived event) {
        onCallSuccess();
    }

    @Subscribe
    public void OnEvent(OnConcreteMachineReceived event) {
        Preferences.storeObject(SELECTED_MACHINE_ROWS, event.getСoncreteMachines().getSize().getRows());
        Preferences.storeObject(SELECTED_MACHINE_COLUMNS, event.getСoncreteMachines().getSize().getColumns());
        Preferences.storeObject(SELECTED_MACHINE_ID, event.getСoncreteMachines().getId());
        Preferences.storeObject(SELECTED_MACHINE_NAME, event.getСoncreteMachines().getName());
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event) {
        if (event.isSuccess()) {
            onCallSuccess();
            callProductsList();
        } else {
            onCallFailed();
        }
    }
}
