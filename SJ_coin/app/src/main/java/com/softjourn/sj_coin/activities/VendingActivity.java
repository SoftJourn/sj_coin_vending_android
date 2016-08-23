package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

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

    private BottomBar mBottomBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {

                }
            }
        });
        ProgressDialogUtils.showDialog(this,getString(R.string.progress_loading));
        Navigation.goToProductListFragments(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }
}
