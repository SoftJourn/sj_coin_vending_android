package com.softjourn.sj_coin.activities;

import android.content.Context;
import android.os.Bundle;

import com.roughike.bottombar.OnTabClickListener;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.utils.Navigation;

public class AllProducts extends BaseActivity {

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        mContext = this;

        mBottomBar.setOnTabClickListener(new OnTabClickListener() {
            @Override
            public void onTabSelected(int position) {
                if (position == R.id.bottomBarFeatured) {
                    Navigation.goToVendingActivity(mContext);
                }
            }

            @Override
            public void onTabReSelected(int position) {

            }
        });
    }
}
