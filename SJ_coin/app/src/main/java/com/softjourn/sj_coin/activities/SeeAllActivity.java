package com.softjourn.sj_coin.activities;

import android.os.Bundle;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.SeeAllProductsFragment;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;

public class SeeAllActivity extends BaseActivity implements Constants, Extras {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        setTitle(getIntent().getStringExtra(EXTRAS_CATEGORY));
        this.getFragmentManager().beginTransaction()
                .replace(R.id.container_for_see_all_products, SeeAllProductsFragment.newInstance(), TAG_PRODUCTS_SEE_ALL_FRAGMENT)
                .commit();
    }
}
