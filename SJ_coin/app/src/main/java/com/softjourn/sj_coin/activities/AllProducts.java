package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.view.Menu;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;

public class AllProducts extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_search).setVisible(true);
        return true;
    }
}
